package net.namibsun.maltourn.lib.matchup;

/**
 * A class that handles a matchup of two competitors
 */
public class Matchup {

    /**
     * The first competitor
     */
    private Competitor competitorOne;

    /**
     * The second competitor
     */
    private Competitor competitorTwo;

    /**
     * The winner of this matchup
     */
    private Competitor winner = null;

    /**
     * The constructor stores the competitors n local variables
     * @param competitorOne the first competitor
     * @param competitorTwo the second competitor
     */
    public Matchup(Competitor competitorOne, Competitor competitorTwo) {
        this.competitorOne = competitorOne;
        this.competitorTwo = competitorTwo;
    }

    /**
     * Sets the result of a matchup.
     * @param winner the winning competitor
     * @param loser the losing competitor
     */
    public void setResult(Competitor winner, Competitor loser) {
        this.winner = winner;
        loser.addLoss();
    }

    /**
     * @return the winning competitor
     */
    public Competitor getWinner() {
        return this.winner;
    }

    /**
     * @return an array containing both competitors
     */
    public Competitor[] getCompetitors() {
        return new Competitor[] {
                this.competitorOne, this.competitorTwo
        };
    }

}
