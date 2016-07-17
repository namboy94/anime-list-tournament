/*
Copyright 2016 Hermann Krumrey

This file is part of mal-tournament.

    mal-tournament is a program that lets a user pit his watched anime series
    from myanimelist.net against each other in an attempt to determine relative scores
    between the shows.

    mal-tournament is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    mal-tournament is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with mal-tournament. If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.maltourn.java.matchup;

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
