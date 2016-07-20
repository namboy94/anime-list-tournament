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

package net.namibsun.maltourn.java.gui;

import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;
import net.namibsun.maltourn.lib.posts.ScoreSetter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Class that models a GUI for pitting two anime series against each other and
 * potentially adjusting their myanimelist.net scores
 */
public class SimpleVsRaterGui extends JFrame {

    /**
     * A label with the series title for the left contestant
     */
    JLabel leftContestantLabel;

    /**
     * A label with the series title for the right contestant
     */
    JLabel rightContestantLabel;

    /**
     * An image visualizing the left contestant
     */
    JLabel leftContestantImage;

    /**
     * An image visualizing the right contestant
     */
    JLabel rightContestantImage;

    /**
     * A field that displays the score of the left contestant
     */
    JTextField leftContestantScore;

    /**
     * A field that displays the score of the right contestant
     */
    JTextField rightContestantScore;

    /**
     * A button that sets the currently entered scores
     */
    JButton scoreConfirmer;

    /**
     * Cancels the current matchup and goes to the next one, changing nothing
     */
    JButton scoreCancler;

    /**
     * Button that can be pressed in case of a draw
     */
    JButton drawButton;

    /**
     * The left contestant
     */
    AnimeSeries leftContestant;

    /**
     * The right contestant
     */
    AnimeSeries rightContestant;

    /**
     * An authenticated score setter
     */
    ScoreSetter scoreSetter;

    /**
     * An Array List of completed anime series of the user
     */
    ArrayList<AnimeSeries> series = new ArrayList<>();

    /**
     * A flag set whenever the user decided on an outcome;
     */
    boolean decided;

    /**
     * Constructor that authenticates the user and creates the layout
     * The GUI is non-scalable to decrease complexity
     */
    public SimpleVsRaterGui() {

        // Set to the system's native look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                UnsupportedLookAndFeelException |
                IllegalAccessException e) {
            e.printStackTrace();
        }

        int width = 800;
        int height = 500;

        // Calculate the middle position in the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPosition = (int)(screenSize.getWidth() / 2) - (width / 2);
        int yPosition = (int)(screenSize.getHeight() / 2) - (height / 2);

        // Set window options
        this.setTitle("MAL VS RATER");
        this.setLayout(null);
        this.setLocation(xPosition, yPosition);
        this.getContentPane().setPreferredSize(new Dimension(width, height));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();

        // log in and set layout elements
        this.login();
        this.setLayoutElements();
        this.loadNextContestants();

        // Start the GUI
        this.setVisible(true);
    }

    /**
     * Prompts the user to log in. If the login attempt fails, the program is closed
     */
    private void login() {
        String username = JOptionPane.showInputDialog(null, "Username");
        String password = JOptionPane.showInputDialog(null, "Password");

        if (!Authenticator.isAuthenticated(username, password)) {
            JOptionPane.showMessageDialog(null, "Invalid Username/Password");
            System.exit(1);
        }
        else {
            this.scoreSetter = new ScoreSetter(username, password);
            Set<AnimeSeries> series = ListGetter.getList(username);
            for (AnimeSeries anime: series) {
                this.series.add(anime);
            }
        }
    }

    /**
     * Sets the layout of the UI Elements
     */
    private void setLayoutElements() {

        // Constants
        int labelWidth = 200;
        int labelHeight = 65;
        int imageWidth = 225;
        int imageHeight = 318;
        int scoreEntryWidth = 40;
        int scoreEntryHeight = 30;
        int buttonWidth = 150;
        int buttonHeight = 50;

        // Left Contestant
        // Title Label
        this.leftContestantLabel = new JLabel("", SwingConstants.CENTER);
        this.leftContestantLabel.setLocation(100, 0);
        this.leftContestantLabel.setSize(labelWidth, labelHeight);
        this.add(this.leftContestantLabel);

        // Image
        this.leftContestantImage = new JLabel();
        this.leftContestantImage.setSize(imageWidth, imageHeight);
        this.leftContestantImage.setLocation(75, 75);
        this.leftContestantImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                SimpleVsRaterGui.this.evaluateBets(SimpleVsRaterGui.this.leftContestant,
                                                   SimpleVsRaterGui.this.rightContestant);
            }
        });
        this.add(this.leftContestantImage);

        // Score Field
        this.leftContestantScore = new JTextField("");
        this.leftContestantScore.setSize(scoreEntryWidth, scoreEntryHeight);
        this.leftContestantScore.setLocation(180, 425);
        this.add(this.leftContestantScore);

        // Right Contestant
        // Title Label
        this.rightContestantLabel = new JLabel("", SwingConstants.CENTER);
        this.rightContestantLabel.setLocation(500, 0);
        this.rightContestantLabel.setSize(labelWidth, labelHeight);
        this.add(this.rightContestantLabel);

        // Image
        this.rightContestantImage = new JLabel();
        this.rightContestantImage.setSize(imageWidth, imageHeight);
        this.rightContestantImage.setLocation(500, 75);
        this.rightContestantImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                SimpleVsRaterGui.this.evaluateBets(SimpleVsRaterGui.this.rightContestant,
                                                   SimpleVsRaterGui.this.leftContestant);
            }
        });
        this.add(this.rightContestantImage);

        //Score Field
        this.rightContestantScore = new JTextField("");
        this.rightContestantScore.setSize(scoreEntryWidth, scoreEntryHeight);
        this.rightContestantScore.setLocation(580, 425);
        this.add(this.rightContestantScore);

        // Draw Button
        this.drawButton = new JButton("Draw");
        this.drawButton.setSize(buttonWidth, buttonHeight);
        this.drawButton.setLocation(325, 175);
        this.drawButton.addActionListener(actionEvent -> SimpleVsRaterGui.this.evaluateBets(true));
        this.add(this.drawButton);

        // Confirmation Button
        this.scoreConfirmer = new JButton("Confirm");
        this.scoreConfirmer.setSize(buttonWidth, buttonHeight);
        this.scoreConfirmer.setLocation(400, 425);
        this.scoreConfirmer.addActionListener(actionEvent -> SimpleVsRaterGui.this.confirmScores());
        this.add(this.scoreConfirmer);

        // Cancel Button
        this.scoreCancler = new JButton("Cancel");
        this.scoreCancler.setSize(buttonWidth, buttonHeight);
        this.scoreCancler.setLocation(250, 425);
        this.scoreCancler.addActionListener(actionEvent -> SimpleVsRaterGui.this.loadNextContestants());
        this.add(this.scoreCancler);

    }

    /**
     * Loads the data for the next contestants into the UI elements
     */
    private void loadNextContestants() {

        this.decided = false;
        this.leftContestantScore.setText("");
        this.rightContestantScore.setText("");

        if (this.leftContestant != null && this.rightContestant != null) {
            this.series.add(this.leftContestant);
            this.series.add(this.rightContestant);
        }
        Collections.shuffle(this.series);

        this.leftContestant = this.series.remove(0);
        this.rightContestant = this.series.remove(0);

        try {
            URL leftImageUrl = new URL(this.leftContestant.seriesImage);
            BufferedImage leftImage = ImageIO.read(leftImageUrl);
            ImageIcon leftIcon = new ImageIcon(leftImage);

            URL rightImageUrl = new URL(this.rightContestant.seriesImage);
            BufferedImage rightImage = ImageIO.read(rightImageUrl);
            ImageIcon rightIcon = new ImageIcon(rightImage);

            this.leftContestantImage.setIcon(leftIcon);
            this.rightContestantImage.setIcon(rightIcon);
        } catch (IOException e) {
            // Just don't display images if IOError
        }

        // HTML tags used for multi-line label
        this.leftContestantLabel.setText("<html>" + this.leftContestant.seriesTitle + "</html>");
        this.rightContestantLabel.setText("<html>" + this.rightContestant.seriesTitle + "</html>");

    }

    /**
     * Confirms and input scores and sets them on the user's myanimelist.net account
     */
    private void confirmScores() {
        try {
            int leftScore = Integer.parseInt(this.leftContestantScore.getText());
            int rightScore = Integer.parseInt(this.rightContestantScore.getText());

            // Check if values are legal
            if (leftScore > 0 && leftScore <= 10 && rightScore > 0 && leftScore <= 10) {
                if (leftScore != this.leftContestant.myScore) {
                    this.scoreSetter.setScore(this.leftContestant, leftScore);
                }
                if (rightScore != this.rightContestant.myScore) {
                    this.scoreSetter.setScore(this.rightContestant, rightScore);
                }
                this.loadNextContestants();  // Start the new round
            }
        }
        catch (NumberFormatException e) {
            // Don't do stuff
        }
    }

    /**
     * Sets the current scores into the score fields
     */
    private void evaluateBets() {
        this.decided = true;
        this.leftContestantScore.setText("" + this.leftContestant.myScore);
        this.rightContestantScore.setText("" + this.rightContestant.myScore);
    }

    /**
     * Evaluates a draw
     * @param drawn true if draw, false otherwise
     */
    private void evaluateBets(boolean drawn) {
        if (this.leftContestant.myScore != this.rightContestant.myScore) {
            this.evaluateBets();
        } else if (!this.decided){
            this.loadNextContestants();
        }
    }

    /**
     * Evaluates a win/loss
     * @param winner the winning competitor
     * @param loser the losing competitor
     */
    private void evaluateBets(AnimeSeries winner, AnimeSeries loser) {
        if (winner.myScore > loser.myScore && !this.decided) {
            this.loadNextContestants();
        }
        else {
            this.evaluateBets();
        }
    }
}
