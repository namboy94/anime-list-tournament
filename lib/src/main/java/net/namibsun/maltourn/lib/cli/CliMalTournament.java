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

package net.namibsun.maltourn.lib.cli;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import net.namibsun.maltourn.lib.gets.Authenticator;
import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.matchup.Competitor;
import net.namibsun.maltourn.lib.matchup.Matchup;
import net.namibsun.maltourn.lib.matchup.Tournament;
import net.namibsun.maltourn.lib.objects.AnimeSeries;

/**
 * Class that creates a CLI for the MAL tournament
 */
public class CliMalTournament {

    /**
     * The input scanner to scan stdin for user input
     */
    Scanner inputScanner = new Scanner(System.in);

    /**
     * Constructor that asks for authentication and starts the tournament procedure
     */
    public CliMalTournament() {

        System.out.println("Please enter your username");
        String username = this.inputScanner.nextLine();
        System.out.println("Please enter your password");
        String password = this.inputScanner.nextLine();

        if (!Authenticator.isAuthenticated(username, password)) {
            System.out.println("Invalid username/password");
            System.exit(1);
        }

        Set<AnimeSeries> completedSeries = ListGetter.getList(username);
        Tournament tournament = new Tournament(completedSeries, 8);

        int matchupCount = 0;

        while (!tournament.isDone()) {
            Set<Matchup> matchups = tournament.getNextMatchups();
            Set<Matchup> matchupResults = new HashSet<>();

            for (Matchup matchup : tournament.getNextMatchups()) {
                matchupCount++;

                Competitor competitorOne = matchup.getCompetitors()[0];
                Competitor competitorTwo = matchup.getCompetitors()[1];

                AnimeSeries seriesOne = (AnimeSeries) competitorOne.getObject();
                AnimeSeries seriesTwo = (AnimeSeries) competitorTwo.getObject();

                System.out.println("1: " + seriesOne.seriesTitle + "  vs.  2: " + seriesTwo.seriesTitle);
                String userResponse = "";
                while (!userResponse.equals("1") && !userResponse.equals("2")) {
                    userResponse = this.inputScanner.nextLine();
                }

                if (userResponse.equals("1")) {
                    matchup.setResult(competitorOne, competitorTwo);
                } else {
                    matchup.setResult(competitorTwo, competitorOne);
                }
                matchupResults.add(matchup);
            }
            tournament.setMatchResults(matchupResults);
            System.out.println("Round complete");
            matchupCount = 0;
        }
    }
}
