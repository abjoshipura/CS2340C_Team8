// Package declaration
package com.example.cs2340c_team8.viewModels;

// Import statements for required Android classes
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.example.cs2340c_team8.R;
import com.example.cs2340c_team8.models.GameConfig;

// Declaration of the 'CreditsViewModel' class, extending BaseObservable
public class CreditsViewModel extends BaseObservable {
    // Instance variable to store the associated activity
    private final Activity activity;

    // Constructor for the CreditsViewModel class, initializing the activity
    public CreditsViewModel(Activity activity) {
        this.activity = activity;

        GameConfig.getMainThemePlayer().pause();
        GameConfig.setMainThemePlayer(MediaPlayer.create(activity, R.raw.credits));
        GameConfig.getMainThemePlayer().setVolume((float) 0.7, (float) 0.7);
        GameConfig.getMainThemePlayer().setLooping(true);
        GameConfig.getMainThemePlayer().start();
    }

    // Method to handle the "Exit Game" button click,
    // starting the home screen activity and closing the current activity
    public void exitGame(View view) {
        GameConfig.getMainThemePlayer().stop();
        // Create an intent to navigate to the home screen
        Intent exit = new Intent(Intent.ACTION_MAIN);
        exit.addCategory(Intent.CATEGORY_HOME);
        exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start the home screen activity
        activity.startActivity(exit);
    }
}
