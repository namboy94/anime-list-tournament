/*
Copyright 2016 Hermann Krumrey

This file is part of mal-tournament.

    mal-tournament is a program that lets a user pit his watched anime series
    from myanimelist.net against each other in an attempt to determine relative scores
    between the shows.

    mal-tournament is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    mal-tournament is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with mal-tournament. If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.maltourn.android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.samples.quickstart.analytics.AnalyticsApplication;

/**
 * An abstract class featuring some commonly used functionality of android and
 * especially google analytics
 */
public abstract class AnalyticsActivity extends AppCompatActivity {

    /**
     * Flag that can disable all analytics-related features
     */
    protected boolean analyticsActive = true;

    /**
     * The Google Analytics tracker
     */
    protected Tracker analyticsTracker;

    /**
     * The ID of the XML layout file
     */
    protected int layoutFile = -1;

    /**
     * The name to be displayed by the action bar
     */
    protected String screenName = "";

    /**
     * The name of the activity that will be logged by Google Analytics
     */
    protected String analyticsName = "Generic Activity";

    /**
     * The constructor (essentially) of the activity. It takes care of initializing the XML file,
     * analytics tracker and action bar title
     * Class that
     * @param savedInstanceState the saved instance sent by the Android OS
     */
    @SuppressWarnings("ConstantConditions") //To appease IntelliJ regarding the setting of the action bar title
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Calls the parent classes' onCreate method
        super.onCreate(savedInstanceState);

        //Loads the layout file, won't load if the layout's ID is -1, i.e. not set.
        if (this.layoutFile != -1) {
            this.setContentView(this.layoutFile);
        }

        //For compatibility reasons, we try to set the support action bar and the action bar as well
        //One or the other always exists, depending on the version of android
        try {
            this.getSupportActionBar().setTitle(this.screenName);
        } catch (NullPointerException e) {
            this.getActionBar().setTitle(this.screenName);
        }

        if (this.analyticsActive) {
            //Initializes the analytics tracker
            AnalyticsApplication application = (AnalyticsApplication) this.getApplication();
            this.analyticsTracker = application.getDefaultTracker();
            this.analyticsTracker.enableAdvertisingIdCollection(true); //Enable demographics tracking
        }
    }

    /**
     * Method that gets called whenever the activity comes into focus
     * Google Analytics tracks whenever this is called and sends the name of the activity's
     * analyticsName
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (this.analyticsActive) {
            analyticsTracker.setScreenName(this.analyticsName);  //Set the name to be sent to the analytics service
            analyticsTracker.send(new HitBuilders.ScreenViewBuilder().build()); //And send it
        }
    }

    /**
     * Shows an error dialog with a custom message
     * @param errorMessage the primary error message to display
     * @param secondaryText the secondary error message to display
     */
    protected void showErrorDialog(String errorMessage, String secondaryText){

        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(this);
        errorDialogBuilder.setTitle(errorMessage);
        errorDialogBuilder.setMessage(secondaryText);
        errorDialogBuilder.setCancelable(true);

        errorDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            /**
             * Sets the dialog's OK button behaviour
             * @param dialog the dialog
             * @param id the ID of something, don't know what
             */
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        errorDialogBuilder.create();
        errorDialogBuilder.show();
    }

    /**
     * Shows an error dialog with a custom message that immediately closes the
     * current activity once the OK button is pressed
     * @param errorMessage the primary error message to display
     * @param secondaryText the secondary error message to display
     */
    protected void showFatalErrorDialog(String errorMessage, String secondaryText){

        AlertDialog.Builder errorDialogBuilder = new AlertDialog.Builder(this);
        errorDialogBuilder.setTitle(errorMessage);
        errorDialogBuilder.setMessage(secondaryText);
        errorDialogBuilder.setCancelable(true);

        final AppCompatActivity activity = this;

        errorDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            /**
             * Sets the dialog's OK button behaviour
             * @param dialog the dialog
             * @param id the ID of something, don't know what
             */
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                activity.finish();
            }
        });

        errorDialogBuilder.create();
        errorDialogBuilder.show();
    }
}
