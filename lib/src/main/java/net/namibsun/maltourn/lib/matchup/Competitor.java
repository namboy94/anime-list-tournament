package net.namibsun.maltourn.lib.matchup;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 */
public class Competitor {

    private Object competitorObject;
    private ArrayList<Competitor> losers = new ArrayList<>();
    private int losses = 0;

    public Competitor(Object competitorObject) {
        this.competitorObject = competitorObject;
    }

    public Object getObject() {
        return this.competitorObject;
    }

    public int getLosses() {
        return this.losses;
    }

    public void addLoss() {
        this.losses++;
        this.losers.forEach(Competitor::addLoss);
    }
}
