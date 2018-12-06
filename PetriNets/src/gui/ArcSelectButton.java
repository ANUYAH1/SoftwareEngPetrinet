package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ArcSelectButton extends JButton {
    public ArcSelectButton(){
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/arc_logo.png"));
            this.setIcon(new ImageIcon(img));
            this.setToolTipText("Arc");


        }catch (Exception ex){
            System.out.println("Error in loading image");
        }
    }
}
