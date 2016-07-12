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

import net.namibsun.maltourn.lib.gets.Authenticator;
import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;
import net.namibsun.maltourn.lib.posts.ScoreSetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

/**
 * Class that allows the user to simply compare two shows and change their rating relative
 * to each other
 */
public class CliSimpleVsRater {

    private Scanner inputScanner = new Scanner(System.in);
    private ArrayList<AnimeSeries> animeList = new ArrayList<>();
    ScoreSetter scoreSetter;

    public CliSimpleVsRater(){

        System.out.println("Welcome to the Simple vs Rater CLI");
        System.out.println("Please enter your myanimelist.net account information");

        System.out.println("Please enter your username");
        String username = this.inputScanner.nextLine();
        System.out.println("Please enter your password");
        String password = this.inputScanner.nextLine();

        if (!Authenticator.isAuthenticated(username, password)) {
            System.out.println("Invalid username/password");
            System.exit(1);
        }
        else {
            this.scoreSetter = new ScoreSetter(username, password);
            System.out.println("Authentication successful");
        }

        System.out.println("Fetching list data. This might take a while.");
        Set<AnimeSeries> animeSet = ListGetter.getList(username);
        for (AnimeSeries anime: animeSet) {
            this.animeList.add(anime);
        }
        Collections.shuffle(this.animeList);
        System.out.println("Fetched list data");

        System.out.println("To select the better of the two shows, enter the number to the left of" +
                "the title. If you think both shows are equally good, enter '='." +
                "\nTo skip a matchup, just press enter/return without entering anything.");

        boolean running = true;
        while (running) {
            this.ratingLoop();
            System.out.println("Would you like to do a new comparison? (y/n)");
            String userResponse = this.inputScanner.nextLine();
            if (userResponse.equals("n")) {
                running = false;
            }
        }

    }

    private void ratingLoop() {
        AnimeSeries entrantOne = this.animeList.remove(0);
        AnimeSeries entrantTwo = this.animeList.remove(0);

        System.out.println("1: " + entrantOne.seriesTitle + "  vs.  2: " + entrantTwo.seriesTitle);

        String userRating = this.inputScanner.nextLine();
        while (!userRating.equals("1") &&
                !userRating.equals("2") &&
                !userRating.equals("=") &&
                !userRating.equals("")) {
            System.out.println("Invalid input. Please enter '1', '2' or '='");
            userRating = this.inputScanner.nextLine();
        }

        switch (userRating) {
            case "1":
                this.evaluateRating(entrantOne, entrantTwo, false);
                break;
            case "2":
                this.evaluateRating(entrantTwo, entrantOne, false);
                break;
            case "=":
                this.evaluateRating(entrantOne, entrantTwo, true);
                break;
            default:
                break;
        }

        this.animeList.add(entrantOne);
        this.animeList.add(entrantTwo);
    }

    private void evaluateRating(AnimeSeries winner, AnimeSeries loser, boolean wasDraw) {
        if ((!wasDraw && winner.myScore <= loser.myScore) || (wasDraw && winner.myScore != loser.myScore)) {
            System.out.println("Current scores for theses shows are:");
            System.out.println(winner.myScore + "   " + winner.seriesTitle);
            System.out.println(loser.myScore + "   " + loser.seriesTitle);
            System.out.println("Do you want to change these scores? (y/n)");
            String response = this.inputScanner.nextLine();
            while (!response.equals("y") && !response.equals("n")) {
                System.out.println("Please enter 'y' or 'n'.");
                response = this.inputScanner.nextLine();
            }
            if (response.equals("y")) {
                System.out.println("Please enter the new scores for the shows:");
                System.out.println(winner.seriesTitle);
                int winnerScore = Integer.parseInt(this.inputScanner.nextLine());
                System.out.println(loser.seriesTitle);
                int loserScore = Integer.parseInt(this.inputScanner.nextLine());
                this.scoreSetter.setScore(winner, winnerScore);
                this.scoreSetter.setScore(loser, loserScore);
                System.out.println("Score set.");
            }
        }
    }

}
