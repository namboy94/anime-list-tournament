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
