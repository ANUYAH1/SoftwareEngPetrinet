package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class StepPlayButton extends JButton {
    public StepPlayButton(){
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/pla_logo.png"));
            this.setIcon(new ImageIcon(img));
            this.setToolTipText("Step Play");
            this.setBackground(Color.GREEN);
            this.setOpaque(true);


        }catch (Exception ex){
            System.out.println("Error in loading image");
        }
    }
}