package net.namibsun.maltourn.lib.gui;

import javax.swing.*;
import java.awt.*;

public class SimpleVsRaterGui extends JFrame {


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
        this.setResizable(false);
        this.setVisible(true);
    }

}
