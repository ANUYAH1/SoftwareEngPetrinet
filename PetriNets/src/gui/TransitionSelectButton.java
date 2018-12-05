package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class TransitionSelectButton extends JButton {

    public TransitionSelectButton(){
        try {
            Image img = ImageIO.read(getClass().getResource("resources/transition_logo.png"));
            this.setIcon(new ImageIcon(img));
        }catch (Exception ex){
            System.out.println("Error in loading image");
        }
    }
}
