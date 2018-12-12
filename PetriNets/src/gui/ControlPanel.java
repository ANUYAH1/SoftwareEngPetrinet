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
    private RefreshPlayButton refreshPlayButton ;


    /**
     * Takes in a listener to
     * listen for changes in this panel
     * @param listener
     */
    public ControlPanel(ElementSelectListener listener,ActionListener playListener){
        this.listener = listener;
        this.completPlayButton = new CompletePlayButton();
        this.stepPlayButton = new StepPlayButton();
        refreshPlayButton= new RefreshPlayButton();


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
        refreshPlayButton.setBackground(Color.GRAY);
        stepPlayButton.setName("control_step_play");
        completPlayButton.setName("control_complete_play");
        refreshPlayButton.setName("control_refresh_play");
        stepPlayButton.addActionListener(playListener);
        refreshPlayButton.addActionListener(playListener);
        completPlayButton.addActionListener(playListener);

        this.add(stepPlayButton);
        this.add(completPlayButton);
        this.add(refreshPlayButton);
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


    public void setPlayEnabled(boolean b) {
        stepPlayButton.setEnabled(b);
    }

    public void setCompleteEnabled(boolean b) {
        completPlayButton.setEnabled(b);
    }
}
