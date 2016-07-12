package net.namibsun.maltourn.lib.matchup;

import java.util.*;

/**
 * Class that handles the tournament structure
 * The tournament is modelled as a simple binary with 2^n competitors per level
 * A preliminary round may be held to ensure that the rest of the tournament's levels are 2^n
 */
public class Tournament {

    /**
     * A set of all competitors in the tournament
     */
    Set<Competitor> competitors = new HashSet<>();

    /**
     * A set of all competitors left in the tournament
     */
    Set<Competitor> competitorsLeft = new HashSet<>();

    /**
     * The constructor wraps each entrant inside a Competitor object and adds them to the
     * competitors and competitorsLeft sets
     * @param entrants a set of tournament participants
     */
    public Tournament(Set<Object> entrants) {
        for (Object entrant : entrants) {
            Competitor competitor = new Competitor(entrant);
            this.competitors.add(competitor);
            this.competitorsLeft.add(competitor);
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
            for (int i = 0; i < this.competitorsLeft.size() - sizeDifference; i++) {
                matchupPool.add(iterator.next());
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
    }

    /**
     * Checks if the tournament has completed
     * @return true if the tournament is completed, false otherwise
     */
    public boolean isDone() {
        return this.competitorsLeft.size() == 1;
    }

}
