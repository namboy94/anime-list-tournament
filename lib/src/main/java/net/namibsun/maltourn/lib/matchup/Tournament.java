package net.namibsun.maltourn.lib.matchup;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by hermann on 7/10/16.
 */
public class Tournament {

    ArrayList<Competitor> competitors = new ArrayList<>();
    ArrayList<Competitor> competitorsLeft = new ArrayList<>();

    public Tournament(ArrayList<AnimeSeries> series) {
        for (AnimeSeries serie : series) {
            Competitor competitor = new Competitor(serie);
            this.competitors.add(competitor);
            this.competitorsLeft.add(competitor)
        }

    }

    private void setupMatchups() {
        int powerOfTwo = 0;
        while (Math.pow(2, powerOfTwo) < this.competitors.size()) {
            powerOfTwo++;
        }
        int sizeDifference = (int) Math.pow(2, powerOfTwo) - this.competitors.size();

    }

    public ArrayList<Matchup> getNextMatchups() {

        ArrayList<Competitor> matchupPool = new ArrayList<>();

        int powerOfTwo = 0;
        while (Math.pow(2, powerOfTwo) < this.competitorsLeft.size()) {
            powerOfTwo++;
        }
        int sizeDifference = (int) Math.pow(2, powerOfTwo) - this.competitorsLeft.size();

        if (sizeDifference == 0) {
            matchupPool = this.competitorsLeft;
        } else {
            for (int i = 0; i < this.competitorsLeft.size() - sizeDifference; i++) {
                matchupPool.add(this.competitorsLeft.get(i));
            }
        }

        assert(matchupPool.size() % 2 == 0);

        ArrayList<Matchup> matchups = new ArrayList<>();
        for (int i = 0; i < matchupPool.size() - 1; i += 2) {
            matchups.add(new Matchup(matchupPool.get(i), matchupPool.get(i + 1)));
        }
        return matchups;
    }

    public void setMatchResults(ArrayList<Matchup> matchups) {
        this.competitorsLeft = new ArrayList<>();
        for (Matchup matchup: matchups) {
            this.competitorsLeft.add(matchup.getWinner());
        }
    }

}
