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

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import net.namibsun.maltourn.android.R;
import net.namibsun.maltourn.lib.lists.HummingBirdListGetter;
import net.namibsun.maltourn.lib.lists.MalListGetter;
import net.namibsun.maltourn.lib.matchup.SimpleVs;
import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * Activity that offers the user the possibility to change his rating according to a tertiary choice
 * between two anime series and a draw
 */
public class SimpleVsActivity extends AnalyticsActivity {

    /**
     * The Simple VS structure
     */
    protected SimpleVs simpleVs;

    /**
     * Flag to see if the user has already made his decision
     */
    private boolean decided = false;

    /**
     * The activity's screen name
     */
    String activityName = "Simple VS Rater";

    /**
     * Creates the activity, downloads the MAL list and creates a score setter object
     * @param savedInstanceState the saved instance sent by the Android OS
     */
    protected void onCreate(Bundle savedInstanceState) {

        this.layoutFile = R.layout.activity_simplevs;
        this.initializeName(this.activityName, this.activityName);
        super.onCreate(savedInstanceState);
        new AsyncListGetter().execute();
    }

    /**
     * Initializes all the listeners. Should be done AFTER the anime list was successfully fetched
     */
    private void initializeListeners() {
        this.findViewById(R.id.drawResultCard).setOnClickListener(new View.OnClickListener() {

            /**
             * Evaluates the matchup as a draw
             * @param v the draw card
             */
            @Override
            public void onClick(View v) {
                SimpleVsActivity.this.evaluateDraw();
            }
        });
        this.findViewById(R.id.topCompetitorCard).setOnClickListener(new View.OnClickListener() {

            /**
             * Evaluates the matchup with the top competitor as the winner
             * @param v the top competitor card
             */
            @Override
            public void onClick(View v) {
                SimpleVsActivity.this.setWinner(
                        (TextView) SimpleVsActivity.this.findViewById(R.id.topCompetitorTitle),
                        (TextView) SimpleVsActivity.this.findViewById(R.id.bottomCompetitorTitle));
            }
        });
        this.findViewById(R.id.bottomCompetitorCard).setOnClickListener(new View.OnClickListener() {

            /**
             * Evaluates the matchup with the bottom competitor as the winner
             * @param v the bottom competitor card
             */
            @Override
            public void onClick(View v) {
                SimpleVsActivity.this.setWinner(
                        (TextView) SimpleVsActivity.this.findViewById(R.id.bottomCompetitorTitle),
                        (TextView) SimpleVsActivity.this.findViewById(R.id.topCompetitorTitle));
            }
        });
        this.findViewById(R.id.confirmResultCard).setOnClickListener(new View.OnClickListener() {

            /**
             * Confirms the currently entered scores
             * @param v the confirm card
             */
            @Override
            public void onClick(View v) {
                SimpleVsActivity.this.confirmScores();
            }
        });
        this.findViewById(R.id.cancelResultCard).setOnClickListener(new View.OnClickListener() {

            /**
             * Cancels the current matchup
             * @param v the cancel card
             */
            @Override
            public void onClick(View v) {
                String matchup = SimpleVsActivity.this.simpleVs.getTitles()[0] + " - "
                        + SimpleVsActivity.this.simpleVs.getTitles()[1];
                SimpleVsActivity.this.sendAnalyticsEvent(SimpleVsActivity.this.activityName, "Canceled", matchup);
                SimpleVsActivity.this.nextRound();
            }
        });
    }

    /**
     * Starts the next round of matchups. Resets all scores, and gets two new random
     * series from the anime list
     */
    private void nextRound() {

        this.decided = false;
        ((EditText)this.findViewById(R.id.topScore)).setText("");
        ((EditText)this.findViewById(R.id.bottomScore)).setText("");

        this.simpleVs.nextRound();

        TextView topCompetitorText = (TextView) this.findViewById(R.id.topCompetitorTitle);
        TextView bottomCompetitorText = (TextView) this.findViewById(R.id.bottomCompetitorTitle);
        topCompetitorText.setText(this.simpleVs.getTitles()[0]);
        bottomCompetitorText.setText(this.simpleVs.getTitles()[1]);

        new ImageLoader().execute();

        this.sendAnalyticsEvent(this.activityName, "New Matchup",
                this.simpleVs.getTitles()[0] + " - " + this.simpleVs.getTitles()[1]);
        this.sendAnalyticsEvent(this.activityName, "Displayed Series", this.simpleVs.getTitles()[0]);
        this.sendAnalyticsEvent(this.activityName, "Displayed Series", this.simpleVs.getTitles()[1]);

    }

    /**
     * Sets the currently entered scores for their respective anime series if theses
     * scores are valid
     */
    private void confirmScores() {
        try {
            int topScore = Integer.parseInt(((EditText) this.findViewById(R.id.topScore)).getText().toString());
            int bottomScore = Integer.parseInt(((EditText) this.findViewById(R.id.bottomScore)).getText().toString());
            if (this.decided) {

                this.sendAnalyticsEvent(this.activityName, "Score Set", this.simpleVs.getTitles()[0] + ": " + topScore);
                this.sendAnalyticsEvent(this.activityName, "Score Set",
                        this.simpleVs.getTitles()[1] + ": " + bottomScore);

                new AsyncScoreSetter().execute(topScore, bottomScore);
                this.nextRound();
            }
        } catch (NumberFormatException e) {
            this.showErrorDialog("Invalid number", "Please enter a score from 1-10");
        }
    }

    /**
     * Sets the current scores of the series into their respective edittexts
     */
    @SuppressLint("SetTextI18n")
    private void evaluate() {
        if (this.simpleVs.isDecisionAcceptable()) {
            this.sendAnalyticsEvent(this.activityName, "Correct Decision", "correct");
            this.nextRound();
        }
        else {
            this.decided = true;
            this.sendAnalyticsEvent(this.activityName, "Inorrect Decision", "incorrect");
            ((EditText) this.findViewById(R.id.topScore)).setText("" + this.simpleVs.getCurrentScores()[0]);
            ((EditText) this.findViewById(R.id.bottomScore)).setText("" + this.simpleVs.getCurrentScores()[1]);
        }
    }

    /**
     * Evaluates a matchup with a winner and loser
     * @param winner the winner
     * @param loser the loser
     */
    private void setWinner(TextView winner, TextView loser) {
        this.sendAnalyticsEvent(this.activityName, "Winner Selected", winner.getText().toString());
        this.sendAnalyticsEvent(this.activityName, "Loser Selected", loser.getText().toString());
        if (!this.decided) {
            this.simpleVs.setWinningDecision(winner.getText().toString(), loser.getText().toString());
            this.evaluate();
        }
    }

    /**
     * Evaluates a draw
     */
    private void evaluateDraw() {
        this.sendAnalyticsEvent(this.activityName, "Draw Selected",
                this.simpleVs.getTitles()[1] + " - " + this.simpleVs.getTitles()[1]);
        if (!this.decided) {
            this.simpleVs.setDrawDecision();
            this.evaluate();
        }
    }

    /**
     * Initializes the simple VS object
     * @param series the series fetched from the user's anime list
     * @param username the user's username
     * @param password the user's password
     */
    protected void initializeSimpleVs(Set<AnimeSeries> series, String username, String password) {
        this.simpleVs = new SimpleVs(series, username, password);
    }

    /**
     * Async Task that sets the scores of the anime series
     */
    private class AsyncScoreSetter extends AsyncTask<Integer, Void, Void> {

        /**
         * This sets the anime scores, only if the scores differ from the current ones
         * @param params expects two integer values, one for the top competitor, one for the bottom competitor,
         *                  in exactly that sequence
         * @return nothing
         */
        protected Void doInBackground(Integer... params) {
            try {
                SimpleVsActivity.this.simpleVs.setScores(params[0], params[1]);
            } catch (IOException e) {
                SimpleVsActivity.this.showErrorDialog("Connection Error", "Failed to set score");
            }
            return null;
        }
    }

    /**
     * Async Task that loads the cover images from myanimelist.net
     */
    private class ImageLoader extends AsyncTask<Void, Void, Void> {

        /**
         * Loads the cover images
         * @param params nothing
         * @return nothing
         */
        protected Void doInBackground(Void... params) {
            final ImageView topCompetitorImage =
                    (ImageView) SimpleVsActivity.this.findViewById(R.id.topCompetitorImage);
            final ImageView bottomCompetitorImage =
                    (ImageView) SimpleVsActivity.this.findViewById(R.id.bottomCompetitorImage);

            try {
                URL topUrl = new URL(SimpleVsActivity.this.simpleVs.getCoverUrls()[0]);
                final Bitmap topBitmap = BitmapFactory.decodeStream(topUrl.openConnection().getInputStream());
                URL bottomUrl = new URL(SimpleVsActivity.this.simpleVs.getCoverUrls()[1]);
                final Bitmap bottomBitmap = BitmapFactory.decodeStream(bottomUrl.openConnection().getInputStream());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        topCompetitorImage.setImageBitmap(topBitmap);
                        bottomCompetitorImage.setImageBitmap(bottomBitmap);
                    }
                });
            } catch (IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int fallbackColor = ContextCompat.getColor(SimpleVsActivity.this, R.color.colorDefaultBG);
                        topCompetitorImage.setImageDrawable(new ColorDrawable(fallbackColor));
                        bottomCompetitorImage.setImageDrawable(new ColorDrawable(fallbackColor));
                    }
                });
            }
            return null;
        }
    }

    /**
     * Async Task that fetches the anime list of the user
     */
    private class AsyncListGetter extends AsyncTask<Void, Void, Void> {

        /**
         * Fetches the anime list
         * @param params nothing
         * @return nothing
         */
        protected Void doInBackground(Void... params) {

            String username = SimpleVsActivity.this.getIntent().getExtras().getString("username");
            String password = SimpleVsActivity.this.getIntent().getExtras().getString("password");
            String service = SimpleVsActivity.this.getIntent().getExtras().getString("service");
            assert service != null;

            try {
                Set<AnimeSeries> animeSeries = null;
                if (service.equals("MyAnimeList")) {
                    animeSeries = new MalListGetter().getCompletedList(username);
                }
                else if (service.equals("Hummingbird")) {
                    animeSeries = new HummingBirdListGetter().getCompletedList(username);
                }
                SimpleVsActivity.this.initializeSimpleVs(animeSeries, username, password);

                runOnUiThread(new Runnable() {
                    /**
                     * Remove the loading bar, populate the first matchup and initialize the listeners.
                     */
                    @Override
                    public void run() {
                        SimpleVsActivity.this.findViewById(R.id.loadingSimpleVs).setVisibility(View.GONE);
                        SimpleVsActivity.this.nextRound();
                        SimpleVsActivity.this.initializeListeners();
                    }
                });

            } catch (IOException | IndexOutOfBoundsException e) {
                Log.e("Exep", "exp");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SimpleVsActivity.this.showFatalErrorDialog("Connection Error", "Failed to fetch list");
                    }
                });
            }
            return null;
        }
    }
}