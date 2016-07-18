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

import android.os.Bundle;
import net.namibsun.maltourn.android.R;

/**
 * Activity that handles the login of the user to myanimelist.net
 */
public class LoginActivity extends AnalyticsActivity {

    /**
     * Creates the login activity
     * @param savedInstanceState the saved instance sent by the Android OS
     */
    protected void onCreate(Bundle savedInstanceState) {

        // this.analyticsActive = false;
        this.layoutFile = R.layout.activity_login;
        this.screenName = "Login";
        this.analyticsName = "MAL-Login";
        super.onCreate(savedInstanceState);
    }

}
