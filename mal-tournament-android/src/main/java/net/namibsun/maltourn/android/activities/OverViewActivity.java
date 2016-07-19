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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import net.namibsun.maltourn.android.R;

/**
 * Activity that shows the available rating/game modes
 */
public class OverViewActivity extends AnalyticsActivity {

    /**
     * Creates the login activity and sets the different game modes available
     * @param savedInstanceState the saved instance sent by the Android OS
     */
    protected void onCreate(Bundle savedInstanceState) {

        this.layoutFile = R.layout.activity_overview;
        this.screenName = "Overview";
        this.analyticsName = "Overview";
        super.onCreate(savedInstanceState);

        TextView loginButton = (TextView) this.findViewById(R.id.simpleVsSelection);

        loginButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Starts the simple VS activity
             * @param v the simple vs button
             */
            @Override
            public void onClick(View v) {
                OverViewActivity.this.startSimpleVsActivity();
            }
        });
    }

    /**
     * Starts the Simple VS Activity and gives it the authentication bundle
     */
    private void startSimpleVsActivity() {
        Intent simpleVsActivity = new Intent(this, SimpleVsActivity.class);
        simpleVsActivity.putExtras(this.getIntent().getExtras());
        this.startActivity(simpleVsActivity);
    }

}
