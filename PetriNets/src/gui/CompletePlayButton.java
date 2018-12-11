package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class CompletePlayButton extends JButton {
    public CompletePlayButton() {
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/run_complete_logo.png"));
            this.setIcon(new ImageIcon(img));
            this.setToolTipText("Complete Play");

            this.setOpaque(true);


        } catch (Exception ex) {
            System.out.println("Error in loading image");
        }
    }
}