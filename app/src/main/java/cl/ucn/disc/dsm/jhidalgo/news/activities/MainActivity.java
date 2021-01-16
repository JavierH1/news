/*
 * Copyright 2020 Javier Hidalgo Ochoa, javier.hidalgo@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.jhidalgo.news.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ModelAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import cl.ucn.disc.dsm.jhidalgo.news.R;
import cl.ucn.disc.dsm.jhidalgo.news.model.News;
import cl.ucn.disc.dsm.jhidalgo.news.services.AppDatabase;
import cl.ucn.disc.dsm.jhidalgo.news.services.CheckNetwork;
import cl.ucn.disc.dsm.jhidalgo.news.services.Contracts;
import cl.ucn.disc.dsm.jhidalgo.news.services.ContractsImpl;
import cl.ucn.disc.dsm.jhidalgo.news.services.ContractsImplNewsApi;

/**
 * The Main Class
 *
 * @autor Javier Hidalgo Ochoa
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(MainActivity.class);

    /**
     * The recycleView.
     */
    protected RecyclerView recyclerView;

    /**
     * The swipeRefreshLayout.
     */
    protected SwipeRefreshLayout swipeRefreshLayout;

    /**
     * onCreate.
     *
     * @param savedInstanceState used to reload the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The switch
        Switch switchButton = findViewById(R.id.switch_1);

        if (switchButton != null) {
            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    openActivity2();

                }
            });
        }

        // Database instance
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "newsDB").enableMultiInstanceInvalidation().build();

        // Using the contracts to get the news ..
        Contracts contracts = new ContractsImplNewsApi("6bccb50265334579b044cc5077e600ed");

        // Returns true if internet available
        if(CheckNetwork.isInternetAvailable(MainActivity.this)) {

            // The toolbar
            this.setSupportActionBar(findViewById(R.id.am_t_toolbar));

            // The FastAdapter
            ModelAdapter<News, NewsItem> newsAdapter = new ModelAdapter<>(NewsItem::new);
            FastAdapter<NewsItem> fastAdapter = FastAdapter.with(newsAdapter);
            fastAdapter.withSelectable(false);

            // The Recycler view
            RecyclerView recyclerView = findViewById(R.id.am_rv_news);
            recyclerView.setAdapter(fastAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            // Get the news in the background thread
            AsyncTask.execute(() -> {

                // Get the News from NewsApi (internet!)
                List<News> listNews = contracts.retrieveNews(30);

                // Delete the stored news when accessing the internet
                Thread t = new Thread(() -> AppDatabase.getInstance(getApplicationContext()).newsDao().nukeTable());
                t.start();

                // Save the news in the local database
                contracts.saveNews(db,listNews);

                // Set the adapter!
                runOnUiThread(() -> {
                    newsAdapter.add(listNews);
                });

                // The Swipe refresh layout
                swipeRefreshLayout = findViewById(R.id.am_swl_refresh);

                // Setup refresh listener
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
                    @Override
                    public void onRefresh(){

                        // Get the news from the background thread
                        AsyncTask.execute(()->{

                            // Using contracts to get the news
                            Contracts contracts = new ContractsImplNewsApi("6bccb50265334579b044cc5077e600ed");

                            // Get the news from internet
                            List<News> listNews = contracts.retrieveNews(30);

                            // Set the adapter
                            runOnUiThread(() -> {

                                // Clear the items
                                newsAdapter.clear();

                                // Add the news items
                                newsAdapter.add(listNews);
                            });
                        });

                        fastAdapter.notifyAdapterDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            });

        }
        // If no internet connection is available
        else {

            Toolbar toolbar;

            // The toolbar
            this.setSupportActionBar(findViewById(R.id.am_t_toolbar));

            // Change the title of the toolbar
            toolbar = (Toolbar) findViewById(R.id.am_t_toolbar);
            toolbar.setTitle("News (No Internet)");
            this.setSupportActionBar(toolbar);

            // The FastAdapter
            ModelAdapter<News, NewsItem> newsAdapter = new ModelAdapter<>(NewsItem::new);
            FastAdapter<NewsItem> fastAdapter = FastAdapter.with(newsAdapter);
            fastAdapter.withSelectable(false);

            // The Recycler view
            RecyclerView recyclerView = findViewById(R.id.am_rv_news);
            recyclerView.setAdapter(fastAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            // Display a message of "no internet connection available"
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();

            AsyncTask.execute(() -> {

                // Get the News from the Room Database (with NO internet!)
                List<News> listNews = db.newsDao().getAll();

                // Set the adapter!
                runOnUiThread(() -> {
                    newsAdapter.add(listNews);
                });
            });
        }

    }

    /**
     * Open the activity2 when clicking the switch
     */
    public void openActivity2 (){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Change the label of the menu based on the state of the app.
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else{
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Check if the correct item was clicked
        if (item.getItemId() == R.id.night_mode){}
        // TODO: Get the night mode state of the app.
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        //Set the theme mode for the restarted activity
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        recreate();
        return true;
    }
}
