package net.namibsun.maltourn.lib.gui;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SimpleVsRaterGui extends JFrame {


    JLabel leftContestantLabel;
    JLabel rightContestantLabel;
    JLabel leftContestantImage;
    JLabel rightContestantImage;
    AnimeSeries leftContestant;
    AnimeSeries rightContestant;


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

        try {
            this.setLayoutElements();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setResizable(false);
        this.setVisible(true);
    }

    private void setLayoutElements() throws IOException {

        int labelWidth = 100;
        int labelHeight = 50;
        int imageWidth = 200;
        int imageHeight = 300;

        this.leftContestantLabel = new JLabel("Left", SwingConstants.CENTER);
        this.leftContestantLabel.setLocation(0, 0);
        this.leftContestantLabel.setSize(labelWidth, labelHeight);
        this.add(this.leftContestantLabel);

        this.rightContestantLabel = new JLabel("Right", SwingConstants.CENTER);
        this.rightContestantLabel.setLocation(400, 0);
        this.rightContestantLabel.setSize(labelWidth, labelWidth);
        this.add(this.rightContestantLabel);

        URL leftImageUrl = new URL("http://images.ichkoche.at/data/image/variations/496x384/1/vanillekipferl-das-klassische-rezept-img-327.jpg");
        BufferedImage leftImage = ImageIO.read(leftImageUrl);
        this.leftContestantImage = new ImageLabel(leftImage, imageWidth, imageHeight, ImageLabel.ScaleMode.STRETCH, Color.black);
        this.leftContestantImage.setLocation(0, 100);
        this.add(this.leftContestantImage);

        URL rightImageUrl = new URL("http://images.ichkoche.at/data/image/variations/496x384/4/vanillekipferl-rezept-img-31386.jpg");
        BufferedImage rightImage = ImageIO.read(rightImageUrl);
        this.rightContestantImage = new ImageLabel(rightImage, imageWidth, imageHeight, ImageLabel.ScaleMode.STRETCH, Color.black);
        this.rightContestantImage.setLocation(400, 100);
        this.add(this.rightContestantImage);


    }

}
