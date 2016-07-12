package net.namibsun.maltourn.lib.matchup;

import java.util.ArrayList;

/**
 * Class that wraps around an Object to enable it being used in a Tournament class
 */
public class Competitor {

    /**
     * The original object
     */
    private Object competitorObject;

    /**
     * The competitors that lost against this competitor previously
     */
    private ArrayList<Competitor> losers = new ArrayList<>();

    /**
     * The amount of (relative) losses this competitor has.
     */
    private int losses = 0;

    /**
     * Constructor that stores the competing object
     * @param competitorObject the object that will compete in the Tournament
     */
    public Competitor(Object competitorObject) {
        this.competitorObject = competitorObject;
    }

    /**
     * Adds a loss to this competitor and all the competitors that lost against it previously
     */
    public void addLoss() {
        this.losses++;
        this.losers.forEach(Competitor::addLoss);
    }

    /**
     * @return the original object
     */
    public Object getObject() {
        return this.competitorObject;
    }

    /**
     * @return the amount of (relative) losses this competitor had
     */
    public int getLosses() {
        return this.losses;
    }

}
