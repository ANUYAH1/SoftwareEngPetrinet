package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ElementSelectButton  extends JButton {
    public ElementSelectButton(){
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/select_logo.png"));
            this.setIcon(new ImageIcon(img));
            this.setToolTipText("Arc");
            this.setOpaque(true);


        }catch (Exception ex){
            System.out.println("Error in loading image");
        }
    }
}