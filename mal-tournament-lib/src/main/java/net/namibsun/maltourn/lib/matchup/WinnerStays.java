package net.namibsun.maltourn.lib.matchup;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.util.Set;

/**
 * Class tht models a 'Winner Stays' rating model
 * It uses the SimpleVS model as a base
 */
public class WinnerStays extends SimpleVs {

    /**
     * A reference to the previous winner
     */
    private AnimeSeries winner = null;

    /**
     * Constructor that acts the same as the constructor of simple VS
     * @param series the completed series of the user
     * @param username the user's username
     * @param password the user's password
     */
    public WinnerStays(Set<AnimeSeries> series, String username, String password) {
        super(series, username, password);
    }

    /**
     * Starts the next matchup
     */
    public void nextRound() {
        super.nextRound();
        if (this.winner != null) {
            this.animeSeries.add(this.competitorOne);
            this.competitorOne = this.winner;
        }
    }

    /**
     * Sets the decision of the user (in case a winner was selected)
     * @param winnerTitle the title of the winning competitor
     * @param loserTitle the title of the losing competitor
     */
    public void setWinningDecision(String winnerTitle, String loserTitle) {
        super.setWinningDecision(winnerTitle, loserTitle);
        if (winnerTitle.equals(this.competitorOne.getTitle())) {
            this.winner = this.competitorOne;
        }
        else {
            this.winner = this.competitorTwo;
        }
    }
}
