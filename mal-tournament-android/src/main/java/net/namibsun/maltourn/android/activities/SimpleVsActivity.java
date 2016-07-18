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

import android.os.AsyncTask;
import android.os.Bundle;
import net.namibsun.maltourn.android.R;
import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;
import net.namibsun.maltourn.lib.posts.ScoreSetter;

import java.util.ArrayList;
import java.util.Set;

public class SimpleVsActivity extends AnalyticsActivity {

    /**
     * The list of completed anime series of the user
     */
    private ArrayList<AnimeSeries> animeList = new ArrayList<>();

    /**
     * The MAL score setter
     */
    private ScoreSetter scoreSetter;

    private String username;
    private String password;

    protected void onCreate(Bundle savedInstanceState) {

        // this.analyticsActive = false;
        this.layoutFile = R.layout.activity_overview;
        this.screenName = "Simple VS Rater";
        this.analyticsName = "Simple Vs Rater";
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        this.username = bundle.getString("username");
        this.password = bundle.getString("password");
        this.scoreSetter = new ScoreSetter(this.username, this.password);
    }

    private void nextRound() {

    }

    private class MalListGetter extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            Set<AnimeSeries> animeSeries = ListGetter.getList(SimpleVsActivity.this.username);
            for (AnimeSeries anime: animeSeries) {
                SimpleVsActivity.this.animeList.add(anime);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SimpleVsActivity.this.nextRound();
                }
            });
            return null;
        }
    }

}
