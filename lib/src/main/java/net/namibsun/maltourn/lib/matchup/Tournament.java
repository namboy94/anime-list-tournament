package net.namibsun.maltourn.lib.matchup;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.util.ArrayList;

/**
 * Created by hermann on 7/10/16.
 */
public class Tournament {

    ArrayList<Competitor> competitors = new ArrayList<>();
    private ArrayList<Competitor[]> matchups = new ArrayList<>();

    public Tournament(ArrayList<AnimeSeries> series) {
        for (AnimeSeries serie : series) {
            this.competitors.add(new Competitor(serie));
        }

    }

    private void setupMatchups() {
        int powerOfTwo = 0;
        while (Math.pow(2, powerOfTwo) < this.competitors.size()) {
            powerOfTwo++;
        }
        int sizeDifference = (int) Math.pow(2, powerOfTwo) - this.competitors.size();

    }

}
