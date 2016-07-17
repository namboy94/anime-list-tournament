package net.namibsun.maltourn.lib.gui;

import net.namibsun.maltourn.lib.gets.Authenticator;
import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;
import net.namibsun.maltourn.lib.posts.ScoreSetter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    ScoreSetter scoreSetter = null;
    ArrayList<AnimeSeries> series = new ArrayList<>();


    public SimpleVsRaterGui() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
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
        while (this.scoreSetter == null) {
            String username = JOptionPane.showInputDialog(null, "Username");
            String password = JOptionPane.showInputDialog(null, "Password");

            if (!Authenticator.isAuthenticated(username, password)) {
                JOptionPane.showConfirmDialog(null, "Invalid Username/Password");
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
    }

    private void setLayoutElements() throws IOException {

        int labelWidth = 300;
        int labelHeight = 50;
        int imageWidth = 200;
        int imageHeight = 300;
        int scoreEntryWidth = 30;
        int scoreEntryHeight = 20;
        int buttonWidth = 50;
        int buttonHeight = 50;

        this.leftContestantLabel = new JLabel(this.leftContestant.seriesTitle, SwingConstants.CENTER);
        this.leftContestantLabel.setLocation(50, 0);
        this.leftContestantLabel.setSize(labelWidth, labelHeight);
        this.add(this.leftContestantLabel);

        this.rightContestantLabel = new JLabel(this.rightContestant.seriesTitle, SwingConstants.CENTER);
        this.rightContestantLabel.setLocation(450, 0);
        this.rightContestantLabel.setSize(labelWidth, labelHeight);
        this.add(this.rightContestantLabel);

        URL leftImageUrl = new URL(this.leftContestant.seriesImage);
        BufferedImage leftImage = ImageIO.read(leftImageUrl);
        this.leftContestantImage = new JLabel();
        this.leftContestantImage.setSize(imageWidth, imageHeight);
        this.leftContestantImage.setIcon(new ImageIcon(leftImage));
        this.leftContestantImage.setLocation(100, 100);
        this.add(this.leftContestantImage);

        URL rightImageUrl = new URL(this.rightContestant.seriesImage);
        BufferedImage rightImage = ImageIO.read(rightImageUrl);
        this.rightContestantImage = new JLabel();
        this.rightContestantImage.setSize(imageWidth, imageHeight);
        this.leftContestantImage.setIcon(new ImageIcon(rightImage));
        this.rightContestantImage.setLocation(500, 100);
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
        this.drawButton.setSize(50, 50);
        this.drawButton.setLocation(0, 0);
        this.drawButton.addActionListener(actionEvent -> SimpleVsRaterGui.this.evaluateBets(true));

        this.leftContestantScore = new JTextField("");
        this.leftContestantScore.setSize(scoreEntryWidth, scoreEntryHeight);
        this.leftContestantScore.setLocation(0, 0);

        this.rightContestantScore = new JTextField("");
        this.rightContestantScore.setSize(scoreEntryWidth, scoreEntryHeight);
        this.rightContestantScore.setLocation(0, 0);

        this.scoreConfirmer = new JButton("Confirm");
        this.scoreConfirmer.setSize(buttonWidth, buttonHeight);
        this.scoreConfirmer.setLocation(0, 0);
        this.scoreConfirmer.addActionListener(actionEvent -> SimpleVsRaterGui.this.confirmScores());

        this.scoreCancler = new JButton("Cancel");
        this.scoreCancler.setSize(buttonWidth, buttonHeight);
        this.scoreCancler.setLocation(0, 0);
        this.scoreCancler.addActionListener(actionEvent -> SimpleVsRaterGui.this.loadNextContestants());


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

        this.leftContestantLabel.setText(this.leftContestant.seriesTitle);
        this.rightContestantLabel.setText(this.rightContestant.seriesTitle);


    }

    private void confirmScores() {
        this.scoreSetter.setScore(this.leftContestant, Integer.parseInt(this.leftContestantScore.getText()));
        this.scoreSetter.setScore(this.rightContestant, Integer.parseInt(this.rightContestantScore.getText()));
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
