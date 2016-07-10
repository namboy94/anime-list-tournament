package net.namibsun.maltourn.lib.matchup;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.util.ArrayList;

/**
 *
 */
public class Competitor {

    private AnimeSeries series;
    private ArrayList<Competitor> losers = new ArrayList<>();
    private int losses = 0;

    public Competitor(AnimeSeries series) {
        this.series = series;
    }

    public String getName() {
        return this.series.seriesTitle;
    }

    public String getImageUrl() {
        return this.series.seriesImage;
    }

    public int getLosses() {
        return this.losses;
    }

    public void addLoss() {
        this.losses++;
        this.losers.forEach(Competitor::addLoss);
    }



}
