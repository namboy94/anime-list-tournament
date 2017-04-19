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

package net.namibsun.maltourn.java.cli;

import net.namibsun.maltourn.lib.authentication.MalAuthenticator;
import net.namibsun.maltourn.lib.lists.MalListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

/**
 * Class that allows the user to simply compare two shows and change their rating relative
 * to each other
 */
public class CliSimpleVsRater {

    String username;
    String password;
    
    /**
     * A scanner that scans stdin for user input
     */
    private Scanner inputScanner = new Scanner(System.in);

    /**
     * A list of anime series associated with the specified username
     */
    private ArrayList<AnimeSeries> animeList = new ArrayList<>();

    /**
     * Starts the CLI loop. Asks for an checks user credentials
     */
    public CliSimpleVsRater() throws IOException {

        System.out.println("Welcome to the Simple vs Rater CLI");
        System.out.println("Please enter your myanimelist.net account information");

        System.out.println("Please enter your username");
        String username = this.inputScanner.nextLine();
        System.out.println("Please enter your password");
        String password = this.inputScanner.nextLine();

        if (!new MalAuthenticator().isAuthenticated(username, password)) {
            System.out.println("Invalid username/password");
            System.exit(1);
        }
        else {
            this.username = username;
            this.password = password;
            System.out.println("Authentication successful");
        }

        System.out.println("Fetching list data. This might take a while.");
        Set<AnimeSeries> animeSet = new MalListGetter().getCompletedList(username);
        for (AnimeSeries anime: animeSet) {
            this.animeList.add(anime);
        }
        Collections.shuffle(this.animeList);
        System.out.println("Fetched list data");

        System.out.println("To select the better of the two shows, enter the number to the " +
                "left of the title. If you think both shows are equally good, enter '='." +
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

    /**
     * Lets the user rate two shows
     */
    private void ratingLoop() throws IOException {
        AnimeSeries entrantOne = this.animeList.remove(0);
        AnimeSeries entrantTwo = this.animeList.remove(0);

        System.out.println("1: " + entrantOne.getTitle() + "  vs.  2: " + entrantTwo.getTitle());

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

    /**
     * Lets the user evaluate his own rating
     * @param winner the winner of the rating
     * @param loser the loser of the rating
     * @param wasDraw true if it was a draw, false otherwise
     */
    private void evaluateRating(AnimeSeries winner, AnimeSeries loser, boolean wasDraw)
            throws IOException {

        if ((!wasDraw && winner.getScore() <= loser.getScore()) ||
                (wasDraw && winner.getScore() != loser.getScore())) {
            System.out.println("Current scores for theses shows are:");
            System.out.println(winner.getScore() + "   " + winner.getTitle());
            System.out.println(loser.getScore() + "   " + loser.getTitle());
            System.out.println("Do you want to change these scores? (y/n)");
            String response = this.inputScanner.nextLine();
            while (!response.equals("y") && !response.equals("n")) {
                System.out.println("Please enter 'y' or 'n'.");
                response = this.inputScanner.nextLine();
            }
            if (response.equals("y")) {
                System.out.println("Please enter the new scores for the shows:");
                System.out.println(winner.getTitle());
                int winnerScore = Integer.parseInt(this.inputScanner.nextLine());
                System.out.println(loser.getTitle());
                int loserScore = Integer.parseInt(this.inputScanner.nextLine());
                winner.setScore(winnerScore, this.username, this.password);
                loser.setScore(loserScore, this.username, this.password);
                System.out.println("Score set.");
            }
        }
    }

}
