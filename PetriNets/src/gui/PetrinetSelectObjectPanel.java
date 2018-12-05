package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PetrinetSelectObjectPanel extends JPanel implements  ActionListener {
    private SelectObject selection;
    private PlaceSelectButton placeSelectButton;
    private ArcSelectButton arcSelectButton;
    private TransitionSelectButton transitionSelectButton;

    public PetrinetSelectObjectPanel(){
        placeSelectButton = new PlaceSelectButton();
        arcSelectButton = new ArcSelectButton();
        transitionSelectButton = new TransitionSelectButton();
        placeSelectButton.setName("place");
        arcSelectButton.setName("arc");
        transitionSelectButton.setName("trans");
        this.setLayout(new FlowLayout());
        transitionSelectButton.addActionListener(this);
        arcSelectButton.addActionListener(this);
        placeSelectButton.addActionListener(this);
        placeSelectButton.setBackground(Color.GRAY);
        transitionSelectButton.setBackground(Color.GRAY);
        arcSelectButton.setBackground(Color.GRAY);
        this.add(transitionSelectButton);
        this.add(arcSelectButton);
        this.add(placeSelectButton);
        this.setBorder(BorderFactory.createTitledBorder("Select Petrinet Model"));
        selection = SelectObject.TRANSITION;
    }

    public SelectObject getSelection(){
        return selection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((JButton)e.getSource()).getName().equals("place")){
            selection =  SelectObject.PLACE;
            placeSelectButton.setBackground(Color.LIGHT_GRAY);
            transitionSelectButton.setBackground(Color.GRAY);
            arcSelectButton.setBackground(Color.GRAY);
        }else  if (((JButton)e.getSource()).getName().equals("arc")){
            selection =  SelectObject.ARC;
            placeSelectButton.setBackground(Color.GRAY);
            transitionSelectButton.setBackground(Color.GRAY);
            arcSelectButton.setBackground(Color.LIGHT_GRAY);
        }else  if (((JButton)e.getSource()).getName().equals("trans")){
            selection =  SelectObject.TRANSITION;
            placeSelectButton.setBackground(Color.GRAY);
            transitionSelectButton.setBackground(Color.LIGHT_GRAY);
            arcSelectButton.setBackground(Color.GRAY);
        }
    }
}
