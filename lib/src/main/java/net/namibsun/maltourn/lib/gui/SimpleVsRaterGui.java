package net.namibsun.maltourn.lib.gui;

import net.namibsun.maltourn.lib.gets.Authenticator;
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

public class SimpleVsRaterGui extends JFrame {

    JLabel leftContestantLabel;
    JLabel rightContestantLabel;
    JLabel leftContestantImage;
    JLabel rightContestantImage;
    JTextField leftContestantScore;
    JTextField rightContestantScore;
    JButton scoreConfirmer;
    JButton scoreCancler;
    JButton drawButton;
    AnimeSeries leftContestant;
    AnimeSeries rightContestant;
    ScoreSetter scoreSetter;
    ArrayList<AnimeSeries> series = new ArrayList<>();


    public SimpleVsRaterGui() {

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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPosition = (int)(screenSize.getWidth() / 2) - (width / 2);
        int yPosition = (int)(screenSize.getHeight() / 2) - (height / 2);

        this.setTitle("MAL VS RATER");
        this.setLayout(null);
        this.setLocation(xPosition, yPosition);
        this.getContentPane().setPreferredSize(new Dimension(width, height));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        this.login();

        try {
            this.setLayoutElements();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setResizable(false);
        this.setVisible(true);
    }

    private void login() {
        String username = JOptionPane.showInputDialog(null, "Username");
        String password = JOptionPane.showInputDialog(null, "Password");

        if (!Authenticator.isAuthenticated(username, password)) {
            JOptionPane.showConfirmDialog(null, "Invalid Username/Password");
            System.exit(1);
        }
        else {
            this.scoreSetter = new ScoreSetter(username, password);
            Set<AnimeSeries> series = ListGetter.getList(username);
            for (AnimeSeries anime: series) {
                this.series.add(anime);
            }
            Collections.shuffle(this.series);
            this.leftContestant = this.series.remove(0);
            this.rightContestant = this.series.remove(0);
        }
    }

    private void setLayoutElements() throws IOException {

        int labelWidth = 200;
        int labelHeight = 65;
        int imageWidth = 225;
        int imageHeight = 318;
        int scoreEntryWidth = 40;
        int scoreEntryHeight = 30;
        int buttonWidth = 150;
        int buttonHeight = 50;

        this.leftContestantLabel = new JLabel("<html>" + this.leftContestant.seriesTitle + "</html>",
                SwingConstants.CENTER);
        this.leftContestantLabel.setLocation(100, 0);
        this.leftContestantLabel.setSize(labelWidth, labelHeight);
        this.add(this.leftContestantLabel);

        this.rightContestantLabel = new JLabel("<html>" + this.rightContestant.seriesTitle + "</html>",
                SwingConstants.CENTER);
        this.rightContestantLabel.setLocation(500, 0);
        this.rightContestantLabel.setSize(labelWidth, labelHeight);
        this.add(this.rightContestantLabel);

        URL leftImageUrl = new URL(this.leftContestant.seriesImage);
        BufferedImage leftImage = ImageIO.read(leftImageUrl);
        this.leftContestantImage = new JLabel();
        this.leftContestantImage.setSize(imageWidth, imageHeight);
        this.leftContestantImage.setIcon(new ImageIcon(leftImage));
        this.leftContestantImage.setLocation(75, 75);
        this.add(this.leftContestantImage);

        URL rightImageUrl = new URL(this.rightContestant.seriesImage);
        BufferedImage rightImage = ImageIO.read(rightImageUrl);
        this.rightContestantImage = new JLabel();
        this.rightContestantImage.setSize(imageWidth, imageHeight);
        this.rightContestantImage.setIcon(new ImageIcon(rightImage));
        this.rightContestantImage.setLocation(500, 75);
        this.add(this.rightContestantImage);

        this.leftContestantImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                SimpleVsRaterGui.this.evaluateBets(SimpleVsRaterGui.this.leftContestant,
                                                   SimpleVsRaterGui.this.rightContestant);
            }
        });

        this.rightContestantImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                SimpleVsRaterGui.this.evaluateBets(SimpleVsRaterGui.this.rightContestant,
                                                   SimpleVsRaterGui.this.leftContestant);
            }
        });

        this.drawButton = new JButton("Draw");
        this.drawButton.setSize(buttonWidth, buttonHeight);
        this.drawButton.setLocation(325, 175);
        this.drawButton.addActionListener(actionEvent -> SimpleVsRaterGui.this.evaluateBets(true));
        this.add(this.drawButton);

        this.leftContestantScore = new JTextField("");
        this.leftContestantScore.setSize(scoreEntryWidth, scoreEntryHeight);
        this.leftContestantScore.setLocation(180, 425);
        this.add(this.leftContestantScore);

        this.rightContestantScore = new JTextField("");
        this.rightContestantScore.setSize(scoreEntryWidth, scoreEntryHeight);
        this.rightContestantScore.setLocation(580, 425);
        this.add(this.rightContestantScore);

        this.scoreConfirmer = new JButton("Confirm");
        this.scoreConfirmer.setSize(buttonWidth, buttonHeight);
        this.scoreConfirmer.setLocation(400, 425);
        this.scoreConfirmer.addActionListener(actionEvent -> SimpleVsRaterGui.this.confirmScores());
        this.add(this.scoreConfirmer);

        this.scoreCancler = new JButton("Cancel");
        this.scoreCancler.setSize(buttonWidth, buttonHeight);
        this.scoreCancler.setLocation(250, 425);
        this.scoreCancler.addActionListener(actionEvent -> SimpleVsRaterGui.this.loadNextContestants());
        this.add(this.scoreCancler);


    }

    private void loadNextContestants() {

        this.leftContestantScore.setText("");
        this.rightContestantScore.setText("");

        this.series.add(this.leftContestant);
        this.series.add(this.rightContestant);
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
            e.printStackTrace();
        }

        this.leftContestantLabel.setText("<html>" + this.leftContestant.seriesTitle + "</html>");
        this.rightContestantLabel.setText("<html>" + this.rightContestant.seriesTitle + "</html>");

    }

    private void confirmScores() {
        int leftScore = Integer.parseInt(this.leftContestantScore.getText());
        int rightScore = Integer.parseInt(this.rightContestantScore.getText());
        if (leftScore > 0 && leftScore <= 10 && rightScore > 0 && leftScore <= 10) {
            if (leftScore != this.leftContestant.myScore) {
                this.scoreSetter.setScore(this.leftContestant, leftScore);
            }
            if (rightScore != this.rightContestant.myScore) {
                this.scoreSetter.setScore(this.rightContestant, rightScore);
            }
            this.loadNextContestants();
        }
    }

    private void evaluateBets() {
        this.leftContestantScore.setText("" + this.leftContestant.myScore);
        this.rightContestantScore.setText("" + this.rightContestant.myScore);
    }

    private void evaluateBets(boolean drawn) {
        if (this.leftContestant.myScore != this.rightContestant.myScore) {
            this.evaluateBets();
        }
    }

    private void evaluateBets(AnimeSeries winner, AnimeSeries loser) {
        if (winner.myScore > loser.myScore) {
            this.loadNextContestants();
        }
        else {
            this.evaluateBets();
        }
    }

}
