package com.flaviu.flickrbrowser;

import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    public static final String FLICKR_QUERRY = "FLICKR_QUERRY";
    public static final String PHOTO_TRANSFER ="PHOTO_TRANSFER";

    void activateToolbar (boolean enableHome){
        Log.d(TAG, "activateToolbar: ");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null){
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            if(toolbar != null){
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }
}
