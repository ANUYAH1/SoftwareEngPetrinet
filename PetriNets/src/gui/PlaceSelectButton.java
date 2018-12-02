package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class PlaceSelectButton extends JButton {
    public PlaceSelectButton(){
        try {
            Image img = ImageIO.read(getClass().getResource("resources/place_logo.png"));
            this.setIcon(new ImageIcon(img));
        }catch (Exception ex){

        }
    }
}
