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

package net.namibsun.maltourn.lib.matchup;

import java.util.*;

/**
 * Class that handles the tournament structure
 * The tournament is modelled as a simple binary with 2^n competitors per level
 * A preliminary round may be held to ensure that the rest of the tournament's levels are 2^n
 */
public class Tournament {

    /**
     * A set of all competitors left in the tournament
     */
    Set<Competitor> competitorsLeft = new HashSet<>();

    /**
     * A set of competitors that are exempt from the first round of matches to ensure a
     * binary tree following the first round.
     */
    Set<Competitor> seeded = new HashSet<>();

    /**
     * The constructor wraps each entrant inside a Competitor object and adds them to the
     * competitors and competitorsLeft sets
     * @param entrants a set of tournament participants
     */
    public Tournament(Set entrants) {
        for (Object entrant : entrants) {
            Competitor competitor = new Competitor(entrant);
            this.competitorsLeft.add(competitor);
        }
    }

    /**
     * Alternative constructor that allows limiting the entrant pool to a specific size
     * @param entrants the entire pool of entrants
     * @param poolSize the amount of entrants to use
     */
    public Tournament(Set entrants, int poolSize) {
        ArrayList<Competitor> helperList = new ArrayList<>();
        for (Object entrant : entrants) {
            Competitor competitor = new Competitor(entrant);
            helperList.add(competitor);
        }
        Collections.shuffle(helperList);
        for (int i = 0; i < poolSize; i++) {
            this.competitorsLeft.add(helperList.get(i));
        }
    }

    /**
     * Calculates a set of Matchup objects of the next matchups
     * This is trivial if there are 2^n competitors left, then it will just return
     * the entire competitorsLeft set
     * Otherwise, a subset of competitorsLeft is returned that ensures that the next matchup will be 2^n
     * @return The set of matchups
     */
    public Set<Matchup> getNextMatchups() {

        Set<Competitor> matchupPool = new HashSet<>();

        //Calculate the n in 2^n
        int powerOfTwo = 0;
        while (Math.pow(2, powerOfTwo) < this.competitorsLeft.size()) {
            powerOfTwo++;
        }
        int sizeDifference = (int) Math.pow(2, powerOfTwo) - this.competitorsLeft.size();

        if (sizeDifference == 0) {
            matchupPool = this.competitorsLeft;
        } else {
            Iterator<Competitor> iterator = this.competitorsLeft.iterator();
            for (int i = 0; i < this.competitorsLeft.size(); i++) {
                if (i < this.competitorsLeft.size() - sizeDifference) {
                    matchupPool.add(iterator.next());
                }
                else {
                    this.seeded.add(iterator.next());
                }
            }
        }

        assert(matchupPool.size() % 2 == 0);

        Set<Matchup> matchups = new HashSet<>();
        Iterator<Competitor> iterator = matchupPool.iterator();
        for (int i = 0; i < matchupPool.size() - 1; i += 2) {
            matchups.add(new Matchup(iterator.next(), iterator.next()));
        }
        return matchups;
    }

    /**
     * Sets the match results, replaces competitorsLeft with a set of winners from the
     * previous matchups
     * @param matchups the resolved matchups
     */
    public void setMatchResults(Set<Matchup> matchups) {
        this.competitorsLeft = new HashSet<>();
        for (Matchup matchup: matchups) {
            this.competitorsLeft.add(matchup.getWinner());
        }
        for (Competitor seededCompetitor: this.seeded) {
            this.competitorsLeft.add(seededCompetitor);
        }
        this.seeded = new HashSet<>();
    }

    /**
     * Checks if the tournament has completed
     * @return true if the tournament is completed, false otherwise
     */
    public boolean isDone() {
        return (this.competitorsLeft.size() == 1);
    }

}
