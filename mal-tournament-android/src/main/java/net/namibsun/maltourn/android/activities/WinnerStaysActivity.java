package net.namibsun.maltourn.android.activities;

import android.os.Bundle;
import net.namibsun.maltourn.lib.matchup.WinnerStays;
import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.util.Set;


public class WinnerStaysActivity extends SimpleVsActivity {

    /**
     * Creates the activity, downloads the MAL list and initializes a Winner Stays rating game
     * @param savedInstanceState the saved instance sent by the Android OS
     */
    protected void onCreate(Bundle savedInstanceState) {

        this.activityName = "Winner Stays Rater";
        super.onCreate(savedInstanceState);
    }

    /**
     * Initializes the simple VS object
     * @param series the series fetched from the user's anime list
     * @param username the user's username
     * @param password the user's password
     */
    protected void initializeSimpleVs(Set<AnimeSeries> series, String username, String password) {
        this.simpleVs = new WinnerStays(series, username, password);
    }

}
