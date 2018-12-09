package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * responsible for drawing Place Button
 */
public class PlaceSelectButton extends JButton {
    public PlaceSelectButton(){
        try {
            Image img = ImageIO.read(getClass().getResource("/icons/place_logo.png"));
            this.setIcon(new ImageIcon(img));
            this.setToolTipText("Place");
            this.setOpaque(true);
        }catch (Exception ex){

        }
    }
}
