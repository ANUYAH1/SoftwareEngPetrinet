package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is responsible for
 * the panel in the GUI required to
 * know what has been selected to
 * draw on the canvas
 */
public class ControlPanel extends JPanel implements  ActionListener {
    private Element selection;
    private PlaceSelectButton placeSelectButton;
    private ArcSelectButton arcSelectButton;
    private TransitionSelectButton transitionSelectButton;
    private ElementSelectListener listener;
    private CompletePlayButton completPlayButton;

    private StepPlayButton stepPlayButton;


    /**
     * Takes in a listener to
     * listen for changes in this panel
     * @param listener
     */
    public ControlPanel(ElementSelectListener listener){
        this.listener = listener;
        this.completPlayButton = new CompletePlayButton();
        this.stepPlayButton = new StepPlayButton();
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
        completPlayButton.setBackground(Color.GRAY);
        stepPlayButton.setBackground(Color.GRAY);
        this.add(stepPlayButton);
        this.add(completPlayButton);
        this.add(transitionSelectButton);
        this.add(arcSelectButton);
        this.add(placeSelectButton);

        this.setRequestFocusEnabled(false);
        this.setBorder(BorderFactory.createTitledBorder("Controls"));
        selection = Element.NONE;





        stepPlayButton.addActionListener(this);
        completPlayButton.addActionListener(this);


    }

    public Element getSelection(){
        return selection;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((JButton)e.getSource()).getName().equals("place")){
            selection =  Element.PLACE;
            placeSelectButton.setBackground(Color.LIGHT_GRAY);
            transitionSelectButton.setBackground(Color.GRAY);
            arcSelectButton.setBackground(Color.GRAY);
        }else  if (((JButton)e.getSource()).getName().equals("arc")){
            selection =  Element.ARC;
            placeSelectButton.setBackground(Color.GRAY);
            transitionSelectButton.setBackground(Color.GRAY);
            arcSelectButton.setBackground(Color.LIGHT_GRAY);
        }else  if (((JButton)e.getSource()).getName().equals("trans")){
            selection =  Element.TRANSITION;
            placeSelectButton.setBackground(Color.GRAY);
            transitionSelectButton.setBackground(Color.LIGHT_GRAY);
            arcSelectButton.setBackground(Color.GRAY);
        }
        listener.selectedPetrinetObject(selection);

    }
}
