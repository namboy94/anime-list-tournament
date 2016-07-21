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

import net.namibsun.maltourn.lib.authentication.MalAuthenticator;
import net.namibsun.maltourn.lib.lists.MalListGetter;
import net.namibsun.maltourn.lib.matchup.SimpleVs;
import net.namibsun.maltourn.lib.objects.AnimeSeries;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

/**
 * Class that models a GUI for pitting two anime series against each other and
 * potentially adjusting their myanimelist.net scores
 */
public class SimpleVsRaterGui extends JFrame {

    /**
     * The generic simple vs structure
     */
    private SimpleVs simpleVs;

    /**
     * A label with the series title for the left contestant
     */
    private JLabel leftContestantLabel;

    /**
     * A label with the series title for the right contestant
     */
    private JLabel rightContestantLabel;

    /**
     * An image visualizing the left contestant
     */
    private JLabel leftContestantImage;

    /**
     * An image visualizing the right contestant
     */
    private JLabel rightContestantImage;

    /**
     * A field that displays the score of the left contestant
     */
    private JTextField leftContestantScore;

    /**
     * A field that displays the score of the right contestant
     */
    private JTextField rightContestantScore;

    /**
     * A flag set whenever the user decided on an outcome;
     */
    private boolean decided;

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

        try {
            if (!new MalAuthenticator().isAuthenticated(username, password)) {
                JOptionPane.showMessageDialog(null, "Invalid Username/Password");
                System.exit(1);
            }
            else {
                Set<AnimeSeries> series = new MalListGetter().getCompletedList(username);
                this.simpleVs = new SimpleVs(series, username, password);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection Error");
            System.exit(1);
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
                SimpleVsRaterGui.this.setWinner(SimpleVsRaterGui.this.leftContestantLabel,
                                                SimpleVsRaterGui.this.rightContestantLabel);
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
                SimpleVsRaterGui.this.setWinner(SimpleVsRaterGui.this.rightContestantLabel,
                                                SimpleVsRaterGui.this.leftContestantLabel);
            }
        });
        this.add(this.rightContestantImage);

        //Score Field
        this.rightContestantScore = new JTextField("");
        this.rightContestantScore.setSize(scoreEntryWidth, scoreEntryHeight);
        this.rightContestantScore.setLocation(580, 425);
        this.add(this.rightContestantScore);

        JButton drawButton = new JButton("Draw");
        drawButton.setSize(buttonWidth, buttonHeight);
        drawButton.setLocation(325, 175);
        drawButton.addActionListener(actionEvent -> {
            SimpleVsRaterGui.this.simpleVs.setDrawDecision();
            SimpleVsRaterGui.this.evaluate();
        });
        this.add(drawButton);

        JButton scoreConfirmer = new JButton("Confirm");
        scoreConfirmer.setSize(buttonWidth, buttonHeight);
        scoreConfirmer.setLocation(400, 425);
        scoreConfirmer.addActionListener(actionEvent -> SimpleVsRaterGui.this.confirmScores());
        this.add(scoreConfirmer);

        JButton scoreCancler = new JButton("Cancel");
        scoreCancler.setSize(buttonWidth, buttonHeight);
        scoreCancler.setLocation(250, 425);
        scoreCancler.addActionListener(actionEvent -> SimpleVsRaterGui.this.loadNextContestants());
        this.add(scoreCancler);

    }

    /**
     * Loads the data for the next contestants into the UI elements
     */
    private void loadNextContestants() {

        this.decided = false;
        this.leftContestantScore.setText("");
        this.rightContestantScore.setText("");

        try {
            String[] urls = this.simpleVs.getCoverUrls();

            URL leftImageUrl = new URL(urls[0]);
            BufferedImage leftImage = ImageIO.read(leftImageUrl);
            ImageIcon leftIcon = new ImageIcon(leftImage);

            URL rightImageUrl = new URL(urls[1]);
            BufferedImage rightImage = ImageIO.read(rightImageUrl);
            ImageIcon rightIcon = new ImageIcon(rightImage);

            this.leftContestantImage.setIcon(leftIcon);
            this.rightContestantImage.setIcon(rightIcon);
        } catch (IOException e) {
            // Just don't display images if IOError
        }

        String[] titles = this.simpleVs.getTitles();

        // HTML tags used for multi-line label
        this.leftContestantLabel.setText("<html>" + titles[0] + "</html>");
        this.rightContestantLabel.setText("<html>" + titles[1] + "</html>");

    }

    /**
     * Confirms the input scores and sets them on the user's myanimelist.net account
     */
    private void confirmScores() {
        try {
            int leftScore = Integer.parseInt(this.leftContestantScore.getText());
            int rightScore = Integer.parseInt(this.rightContestantScore.getText());
            this.simpleVs.setScores(leftScore, rightScore);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to set scores");
        }
    }

    /**
     * Evaluates a user decision
     */
    private void evaluate() {
        if (this.simpleVs.isDecisionAcceptable()) {
            this.loadNextContestants();
        }
        else {
            this.decided = true;
            int[] scores = this.simpleVs.getCurrentScores();
            this.leftContestantScore.setText("" + scores[0]);
            this.rightContestantScore.setText("" + scores[1]);
            this.decided = true;
        }
    }

    /**
     * Sets the winner
     * @param winner the title label of the winner
     * @param loser the title label of the loser
     */
    private void setWinner(JLabel winner, JLabel loser) {
        String winnerTitle = winner.getText().split("<html>")[1].split("</html>")[0];
        String loserTitle = loser.getText().split("<html>")[1].split("</html>")[0];
        this.simpleVs.setWinningDecision(winnerTitle, loserTitle);
        this.evaluate();
    }
}
