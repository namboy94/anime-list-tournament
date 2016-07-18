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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.namibsun.maltourn.android.R;
import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;
import net.namibsun.maltourn.lib.posts.ScoreSetter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class SimpleVsActivity extends AnalyticsActivity {

    /**
     * The list of completed anime series of the user
     */
    private ArrayList<AnimeSeries> animeList = new ArrayList<>();

    private AnimeSeries topCompetitor = null;
    private AnimeSeries bottomCompetitor = null;

    /**
     * The MAL score setter
     */
    private ScoreSetter scoreSetter;

    protected void onCreate(Bundle savedInstanceState) {

        // this.analyticsActive = false;
        this.layoutFile = R.layout.activity_simplevs;
        this.screenName = "Simple VS Rater";
        this.analyticsName = "Simple Vs Rater";
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        this.scoreSetter = new ScoreSetter(bundle.getString("username"), bundle.getString("password"));

        new MalListGetter().execute();
    }

    private void initializeListeners() {
        this.findViewById(R.id.drawResultCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleVsActivity.this.nextRound();
            }
        });
        this.findViewById(R.id.topCompetitorCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleVsActivity.this.nextRound();
            }
        });
        this.findViewById(R.id.bottomCompetitorCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleVsActivity.this.nextRound();
            }
        });
    }

    private void nextRound() {

        if (this.topCompetitor != null && this.bottomCompetitor != null) {
            this.animeList.add(this.topCompetitor);
            this.animeList.add(this.topCompetitor);
            Collections.shuffle(this.animeList);
        }

        this.topCompetitor = this.animeList.remove(0);
        this.bottomCompetitor = this.animeList.remove(0);

        TextView topCompetitorText = (TextView) this.findViewById(R.id.topCompetitorTitle);
        TextView bottomCompetitorText = (TextView) this.findViewById(R.id.bottomCompetitorTitle);
        topCompetitorText.setText(this.topCompetitor.seriesTitle);
        bottomCompetitorText.setText(this.bottomCompetitor.seriesTitle);

        new ImageLoader().execute();

    }

    private class ImageLoader extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            try {
                URL topUrl = new URL(SimpleVsActivity.this.topCompetitor.seriesImage);
                final Bitmap topBitmap = BitmapFactory.decodeStream(topUrl.openConnection().getInputStream());
                URL bottomUrl = new URL(SimpleVsActivity.this.bottomCompetitor.seriesImage);
                final Bitmap bottomBitmap = BitmapFactory.decodeStream(bottomUrl.openConnection().getInputStream());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView topCompetitorImage =
                                (ImageView) SimpleVsActivity.this.findViewById(R.id.topCompetitorImage);
                        ImageView bottomCompetitorImage =
                                (ImageView) SimpleVsActivity.this.findViewById(R.id.bottomCompetitorImage);
                        topCompetitorImage.setImageBitmap(topBitmap);
                        bottomCompetitorImage.setImageBitmap(bottomBitmap);
                    }
                });
            } catch (IOException e) {
                // Do nothing
            }
            return null;
        }
    }

    private class MalListGetter extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            String username = SimpleVsActivity.this.getIntent().getExtras().getString("username");
            Set<AnimeSeries> animeSeries = ListGetter.getList(username);
            for (AnimeSeries anime: animeSeries) {
                SimpleVsActivity.this.animeList.add(anime);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ProgressBar progressBar = (ProgressBar) SimpleVsActivity.this.findViewById(R.id.loadingSimpleVs);
                    progressBar.setVisibility(View.GONE);
                    SimpleVsActivity.this.nextRound();
                    SimpleVsActivity.this.initializeListeners();
                }
            });
            return null;
        }
    }

}
