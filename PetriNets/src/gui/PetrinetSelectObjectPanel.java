package gui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PetrinetSelectObjectPanel extends JPanel {
    private PlaceSelectButton placeSelectButton;
    private ArcSelectButton arcSelectButton;
    private TransitionSelectButton transitionSelectButton;
    private  ActionListener actionListener;
    public PetrinetSelectObjectPanel(ActionListener actionListener){
        placeSelectButton = new PlaceSelectButton();
        arcSelectButton = new ArcSelectButton();
        transitionSelectButton = new TransitionSelectButton();
    }
}
