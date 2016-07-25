package net.namibsun.maltourn.lib.matchup;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Class that models a UI-independendt implementation of a Simple VS Matchup Mode
 * for Anime Series.
 */
public class SimpleVs {

    /**
     * The user's completed anime list
     */
    protected ArrayList<AnimeSeries> animeSeries = new ArrayList<>();

    /**
     * The user's username
     */
    protected String username;

    /**
     * The user's password
     */
    protected String password;

    /**
     * One of two competitors in the current round
     */
    protected AnimeSeries competitorOne = null;

    /**
     * One of two competitors in the current round
     */
    protected AnimeSeries competitorTwo = null;

    /**
     * Flag that gets set whenever the user's decision is not in line with the current scores
     */
    protected boolean decisionDiscepancy = false;

    /**
     * Constructor that stores the username and password of the user as well as the previously
     * fetched anime list.
     * @param series the completed anime list of the user
     * @param username the user's username
     * @param password the user's password
     */
    public SimpleVs(Set<AnimeSeries> series, String username, String password) {

        for (AnimeSeries anime: series) {
            this.animeSeries.add(anime);
        }
        Collections.shuffle(this.animeSeries);

        this.username = username;
        this.password = password;

        this.nextRound();

    }

    /**
     * Starts the next matchup
     */
    public void nextRound() {

        this.decisionDiscepancy = false;

        if (this.competitorOne != null) {
            this.animeSeries.add(this.competitorOne);
        }
        if (this.competitorTwo != null) {
            this.animeSeries.add(this.competitorTwo);
        }

        this.competitorOne = this.animeSeries.remove(0);
        this.competitorTwo = this.animeSeries.remove(0);
    }

    /**
     * @return the titles of the two competitors
     */
    public String[] getTitles() {
        return new String[] {
                this.competitorOne.getTitle(),
                this.competitorTwo.getTitle()
        };
    }

    /**
     * @return the URLs to the cover images of the competitors
     */
    public String[] getCoverUrls() {
        return new String[] {
                this.competitorOne.getImageUrl(),
                this.competitorTwo.getImageUrl()
        };
    }

    /** 
     * Sets the decision of the user (in case a winner was selected)
     * @param winnerTitle the title of the winning competitor
     * @param loserTitle the title of the losing competitor
     */
    public void setWinningDecision(String winnerTitle, String loserTitle) {
        if (winnerTitle.equals(this.competitorOne.getTitle())) {
            this.decisionDiscepancy = !(this.competitorOne.getScore() > this.competitorTwo.getScore());
        } else {
            this.decisionDiscepancy = !(this.competitorTwo.getScore() > this.competitorOne.getScore());
        }
    }

    /**
     * Sets the decision of the user in case of a draw
     */
    public void setDrawDecision() {
        this.decisionDiscepancy = !(this.competitorOne.getScore() == this.competitorTwo.getScore());
    }

    /**
     * Returns if the previous decision was OK
     * @return true if the decision was accepted as is, false otherwise
     */
    public boolean isDecisionAcceptable() {
        return !this.decisionDiscepancy;
    }

    /**
     * @return the current scores of the series
     */
    public int[] getCurrentScores() {
        return new int[]{
                this.competitorOne.getScore(),
                this.competitorTwo.getScore()
        };
    }

    /**
     * Sets the scores of the competitors
     * @param scoreOne the score of the first competitor
     * @param scoreTwo the score of the second competitor
     * @throws IOException in case a connection error occurs
     */
    public void setScores(int scoreOne, int scoreTwo) throws IOException {
        if (scoreOne > 0 && scoreOne <= 10 && scoreOne != this.competitorOne.getScore()) {
            this.competitorOne.setScore(scoreOne, this.username, this.password);
        }
        if (scoreTwo > 0 && scoreTwo <= 10 && scoreTwo != this.competitorTwo.getScore()) {
            this.competitorTwo.setScore(scoreTwo, this.username, this.password);
        }
    }
}
