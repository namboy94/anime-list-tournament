package net.namibsun.maltourn.lib.matchup;

public class Matchup {

    private Competitor competitorOne;
    private Competitor competitorTwo;
    private Competitor winner = null;

    public Matchup(Competitor competitorOne, Competitor competitorTwo) {
        this.competitorOne = competitorOne;
        this.competitorTwo = competitorTwo;
    }

    public Competitor[] getCompetitors() {
        return new Competitor[] {
            this.competitorOne, this.competitorTwo
        };
    }

    public void setResult(Competitor winner, Competitor loser) {
        this.winner = winner;
        loser.addLoss();
    }

    public Competitor getWinner() {
        return this.winner;
    }

}
