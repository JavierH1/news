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
import android.view.View;
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

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ModelAdapter;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.jhidalgo.news.R;
import cl.ucn.disc.dsm.jhidalgo.news.model.ArticleNews;
import cl.ucn.disc.dsm.jhidalgo.news.model.News;
import cl.ucn.disc.dsm.jhidalgo.news.services.ApiAdapter;
import cl.ucn.disc.dsm.jhidalgo.news.services.AppDatabase;
import cl.ucn.disc.dsm.jhidalgo.news.services.CheckNetwork;
import cl.ucn.disc.dsm.jhidalgo.news.services.Contracts;
import cl.ucn.disc.dsm.jhidalgo.news.services.ContractsImplNewsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The Secondary Class, used to display the news from laravel
 *
 * @autor Javier Hidalgo Ochoa
 */
public class ActivityLaravel extends AppCompatActivity {

    /**
     * The list where the news will be save
     */
    ArrayList<News> newsList = new ArrayList<>();

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
        setContentView(R.layout.activity2);

        // Check if Fresco has Been Initialized
        if(!Fresco.hasBeenInitialized()) {
            Fresco.initialize(this);
        }

        Toolbar toolbar;
        // The toolbar
        this.setSupportActionBar(findViewById(R.id.am_t_toolbar));
        toolbar = (Toolbar) findViewById(R.id.am_t_toolbar);
        toolbar.setSubtitle("LaravelNews");

        // The FastAdapter
        ModelAdapter<News, NewsItem> newsAdapter = new ModelAdapter<>(NewsItem::new);
        FastAdapter<NewsItem> fastAdapter = FastAdapter.with(newsAdapter);
        fastAdapter.withSelectable(false);

        // The Recycler view
        RecyclerView recyclerView = findViewById(R.id.am_rv_news);
        recyclerView.setAdapter(fastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // The switch button
        Switch switchButton = findViewById(R.id.switch_2);
        switchButton.setChecked(true);

        // The Swipe refresh layout
        swipeRefreshLayout = findViewById(R.id.am_swl_refresh);

        // Database instance
        AppDatabase db2 = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "newsDB2").enableMultiInstanceInvalidation().build();

        AsyncTask.execute(() -> {

            // Switch listener on click
            switchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db2.close();
                    openActivity();
                }
            });

            // Swipe refresh listener
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
                @Override
                public void onRefresh(){
                    recreate();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

        });

        // Using the contracts to save the news
        Contracts contracts = new ContractsImplNewsApi("6bccb50265334579b044cc5077e600ed");

        // Returns true if internet available
        if(CheckNetwork.isInternetAvailable(ActivityLaravel.this)) {

             // The call to the laravel api
            Call<ArrayList<ArticleNews>> call = ApiAdapter.getApiService().getNews();
            call.enqueue(new Callback<ArrayList<ArticleNews>>() {
                @Override
                public void onResponse(Call<ArrayList<ArticleNews>> call, Response<ArrayList<ArticleNews>> response) {

                    if(response.isSuccessful()){
                        int x= response.body().size();

                        for(int i=0;i<x;i++){
                            News news = new News(response.body().get(i).getTitle()
                                    ,response.body().get(i).getSource()
                                    ,response.body().get(i).getAuthor()
                                    ,response.body().get(i).getUrl()
                                    ,response.body().get(i).getUrl_image()
                                    ,response.body().get(i).getDescription()
                                    ,response.body().get(i).getContent()
                                    ,org.threeten.bp.ZonedDateTime.parse(response.body().get(i).getPublished_at()));

                            // Add the news to the list
                            newsList.add(news);

                        }

                        AsyncTask.execute(() -> {
                            // Save the news into the database
                            contracts.saveNews(db2, newsList);

                            // Set the adapter!
                            runOnUiThread(() -> {
                                // Display the news
                                newsAdapter.add(newsList);

                            });

                        });

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ArticleNews>> call, Throwable t) {

                    // Display message onFailure
                    Toast.makeText(ActivityLaravel.this,"Can't connect to the server",Toast.LENGTH_LONG).show();

                }
            });

        }
        // If no internet connection is available
        else {

            // Change the title of the toolbar
            toolbar = (Toolbar) findViewById(R.id.am_t_toolbar);
            toolbar.setTitle("News (No Internet)");
            toolbar.setSubtitle("LaravelNews");
            this.setSupportActionBar(toolbar);

            // Display a message of "no internet connection available"
            Toast.makeText(ActivityLaravel.this,"No Internet Connection",Toast.LENGTH_LONG).show();

            AsyncTask.execute(() -> {

                // Get the News from the Room Database (with NO internet!)
                List<News> listNews = db2.newsDao().getAll();

                // Set the adapter!
                runOnUiThread(() -> {
                    newsAdapter.add(listNews);
                });

            });

        }

    }

    /**
     * Open the MainActivity when clicking the switch
     */
    public void openActivity (){

        Intent intent = new Intent(this, MainActivity.class);
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