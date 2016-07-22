package net.namibsun.maltourn.android.activities;

import net.namibsun.maltourn.lib.matchup.WinnerStays;
import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.util.Set;


public class WinnerStaysActivity extends SimpleVsActivity {

    /**
    * The activity's screen name
            */
    String screenName = "Winner Stays Rater";

    /**
     * The activity' analytics name
     */
    String analyticsName = "Winner Stays Rater";

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
