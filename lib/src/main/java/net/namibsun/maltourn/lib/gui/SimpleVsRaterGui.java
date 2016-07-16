package net.namibsun.maltourn.lib.gui;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SimpleVsRaterGui extends JFrame {


    JLabel leftContestantLabel;
    JLabel rightContestantLabel;
    JLabel leftContestantImage;
    JLabel rightContestantImage;
    AnimeSeries leftContestant;
    AnimeSeries rightContestant;


    public SimpleVsRaterGui() {

        int width = 750;
        int height = 450;

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

        int labelWidth = 200;
        int labelHeight = 50;

        this.leftContestantLabel = new JLabel("Test", SwingConstants.CENTER);
        this.leftContestantLabel.setLocation(10, 200);
        this.leftContestantLabel.setSize(labelWidth, labelHeight);
        this.add(this.leftContestantLabel);

        this.rightContestantLabel = new JLabel("Test", SwingConstants.CENTER);
        this.rightContestantLabel.setLocation(250, 200);
        this.rightContestantLabel.setSize(labelWidth, labelWidth);
        this.add(this.rightContestantLabel);

        URL leftImageUrl = new URL("https://pixabay.com/static/uploads/photo/2015/10/01/21/39/background-image-967820_960_720.jpg");
        BufferedImage leftImage = ImageIO.read(leftImageUrl);
        this.leftContestantImage = new ImageLabel(leftImage, 200, 200, ImageLabel.ScaleMode.STRETCH, Color.black);
        this.leftContestantImage.setLocation(300, 300);
        this.add(this.leftContestantImage);


    }

}
