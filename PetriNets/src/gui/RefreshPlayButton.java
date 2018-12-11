package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class RefreshPlayButton extends JButton {
    public RefreshPlayButton(){
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/refresh_logo.png"));
            this.setIcon(new ImageIcon(img));
            this.setToolTipText("Refresh");

            this.setOpaque(true);


        }catch (Exception ex){
            System.out.println("Error in loading image");
        }
    }
}