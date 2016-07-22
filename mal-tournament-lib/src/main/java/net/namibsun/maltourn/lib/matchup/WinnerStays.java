package net.namibsun.maltourn.lib.matchup;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.util.Set;

public class WinnerStays extends SimpleVs {

    private AnimeSeries winner = null;

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
