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
    boolean prelims = false;

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

    public ArrayList<Competitor> getNextMatchups() {
        int powerOfTwo = 0;
        while (Math.pow(2, powerOfTwo) < this.competitorsLeft.size()) {
            powerOfTwo++;
        }
        int sizeDifference = (int) Math.pow(2, powerOfTwo) - this.competitorsLeft.size();
        if (sizeDifference == 0) {
            return this.competitorsLeft;
        } else {
            this.prelims = true;
            ArrayList<Competitor> preliminaries = new ArrayList<>();
            for (int i = 0; i < this.competitorsLeft.size() - sizeDifference; i++) {
                preliminaries.add(this.competitorsLeft.get(i));
            }
            return preliminaries;
        }
    }

    public void setMatchupResults


}
