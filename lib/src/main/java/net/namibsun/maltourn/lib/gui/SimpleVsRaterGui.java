package net.namibsun.maltourn.lib.gui;

import net.namibsun.maltourn.lib.gets.Authenticator;
import net.namibsun.maltourn.lib.gets.ListGetter;
import net.namibsun.maltourn.lib.objects.AnimeSeries;
import net.namibsun.maltourn.lib.posts.ScoreSetter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
    AnimeSeries leftContestant;
    AnimeSeries rightContestant;
    ScoreSetter scoreSetter = null;
    ArrayList<AnimeSeries> series = new ArrayList<>();


    public SimpleVsRaterGui() {

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

        int labelWidth = 100;
        int labelHeight = 50;
        int imageWidth = 200;
        int imageHeight = 300;

        this.leftContestantLabel = new JLabel(this.leftContestant.seriesTitle, SwingConstants.CENTER);
        this.leftContestantLabel.setLocation(150, 0);
        this.leftContestantLabel.setSize(labelWidth, labelHeight);
        this.add(this.leftContestantLabel);

        this.rightContestantLabel = new JLabel(this.rightContestant.seriesTitle, SwingConstants.CENTER);
        this.rightContestantLabel.setLocation(550, 0);
        this.rightContestantLabel.setSize(labelWidth, labelHeight);
        this.add(this.rightContestantLabel);

        URL leftImageUrl = new URL(this.leftContestant.seriesImage);
        BufferedImage leftImage = ImageIO.read(leftImageUrl);
        this.leftContestantImage = new ImageLabel(leftImage, imageWidth, imageHeight, ImageLabel.ScaleMode.STRETCH, Color.black);
        this.leftContestantImage.setLocation(100, 100);
        this.add(this.leftContestantImage);

        URL rightImageUrl = new URL(this.rightContestant.seriesImage);
        BufferedImage rightImage = ImageIO.read(rightImageUrl);
        this.rightContestantImage = new ImageLabel(rightImage, imageWidth, imageHeight, ImageLabel.ScaleMode.STRETCH, Color.black);
        this.rightContestantImage.setLocation(500, 100);
        this.add(this.rightContestantImage);

    }

}
