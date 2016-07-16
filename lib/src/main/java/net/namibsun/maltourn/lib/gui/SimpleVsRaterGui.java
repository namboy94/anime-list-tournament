package net.namibsun.maltourn.lib.gui;

import net.namibsun.maltourn.lib.objects.AnimeSeries;

import javax.swing.*;
import java.awt.*;

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

        this.setLayoutElements();

        this.setResizable(false);
        this.setVisible(true);
    }

    private void setLayoutElements() {

        this.leftContestantLabel = new JLabel("Test", SwingConstants.CENTER);
        this.leftContestantLabel.setLocation(1, 1);
        this.leftContestantLabel.setSize(200, 200);
        this.add(this.leftContestantLabel);

    }

}
