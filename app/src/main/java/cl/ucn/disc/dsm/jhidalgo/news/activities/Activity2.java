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
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import cl.ucn.disc.dsm.jhidalgo.news.R;
import cl.ucn.disc.dsm.jhidalgo.news.services.AppDatabase;
import cl.ucn.disc.dsm.jhidalgo.news.services.CheckNetwork;
import cl.ucn.disc.dsm.jhidalgo.news.services.Contracts;
import cl.ucn.disc.dsm.jhidalgo.news.services.ContractsImplNewsApi;

public class Activity2 extends AppCompatActivity {

    /**
     * The listView.
     */
    protected ListView listView;

    /**
     * onCreate.
     *
     * @param savedInstanceState used to reload the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        // The switch
        Switch switchButton = findViewById(R.id.switch_2);
        switchButton.setChecked(true);

        if (switchButton != null) {
            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    openActivity();
                }
            });
        }

        // Database instance
        AppDatabase db2 = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "newsDB").enableMultiInstanceInvalidation().build();

        // Using the contracts to get the news ..
        Contracts contracts = new ContractsImplNewsApi("6bccb50265334579b044cc5077e600ed");

        // Returns true if internet available
        if(CheckNetwork.isInternetAvailable(Activity2.this)) {

            // The toolbar
            this.setSupportActionBar(findViewById(R.id.am_t_toolbar));

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

        }

    }

    /**
     * Open the MainActivity when clicking the switch
     */
    public void openActivity (){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}