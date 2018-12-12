package gui;

import logic.PetriNetInterface;
import logic.PlaceInterface;
import logic.TransitionInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This houses the whole main
 * petrinet structure
 */
public class PetrinetPanel extends JPanel implements ElementSelectListener,LogListener, ActionListener {
    private DrawPanel drawPanel;
    private ControlPanel controlPanel;
    private LogPanel logPanel;
    private PetriNetInterface petrinetLogic;

    public PetrinetPanel(PetriNetInterface petrinetLogic){
        this.petrinetLogic = petrinetLogic;
        drawPanel = new DrawPanel(this,petrinetLogic);
        controlPanel = new ControlPanel(this,this);
        logPanel = new LogPanel();

        this.setLayout(new BorderLayout());
        this.add(controlPanel,BorderLayout.NORTH);

        this.add(drawPanel,BorderLayout.CENTER);
        this.add(logPanel,BorderLayout.SOUTH);
        this.setEnabled(false);

        this.setPreferredSize(new Dimension(800,700));
    }




    @Override
    public void selectedPetrinetObject(Element element) {
        drawPanel.setCurrentPetrinetObject(element);
    }

    @Override
    public void log(LogUIModel log) {
        logPanel.log(log);
    }

    /**
     * Undo last operation
     */
    public void undo() {
        drawPanel.undo();
    }

    /**
     * redo last operation
     */
    public void redo(){
        drawPanel.redo();
    }

    /**
     * Returns a copy of the
     * GUI objects with its
     * back end instances
     * @return
     */
    public ArrayList<Petrinet2DObjectInterface> getProjectStateForSave() {

        return drawPanel.getGuiObjects();
    }

    /**
     * Clears the project
     * called when you need to
     * start a new project
     */
    public void clearProject() {
        drawPanel.clearAll();
    }

    public void loadProject(ArrayList<Petrinet2DObjectInterface> guiObjects) {
        for(Petrinet2DObjectInterface obj : guiObjects)
        {
            if (obj instanceof  Transition2DObject){
                Transition2DObject transObject =(Transition2DObject)obj;
                petrinetLogic.addTransition(
                        transObject.getTransition()
                );
            }else  if (obj instanceof  Place2DObject){
                Place2DObject placeObject =(Place2DObject)obj;
                petrinetLogic.addPlace(
                        placeObject.getPlace()
                );
            }
        }

        drawPanel.loadElements(guiObjects);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
       JButton button = ((JButton)actionEvent.getSource());
       if(button.getName().equals("control_step_play")){
           boolean canDo = petrinetLogic.hasNext();
           button.setEnabled(canDo);
           if(canDo) {
               petrinetLogic.next();

           }else{
               String otherInfo = getFullPetriInformation();
               drawPanel.setPetriAttrib(otherInfo);
               controlPanel.setCompleteEnabled(false);
           }
           drawPanel.refresh();
       }else if(button.getName().equals("control_complete_play")){

           petrinetLogic.complete();
           button.setEnabled(false);
           controlPanel.setPlayEnabled(false);

           String otherInfo = getFullPetriInformation();
           drawPanel.setPetriAttrib(otherInfo);



           drawPanel.refresh();
       }else if(button.getName().equals("control_refresh_play")){

          petrinetLogic.abortTreeTraversal();

          controlPanel.setPlayEnabled(true);
          controlPanel.setCompleteEnabled (true);
          drawPanel.refresh();

          drawPanel.clearPetriAttribPanel();
       }
    }

    private String getFullPetriInformation() {

        String otherInfo = "";
        otherInfo += "Liveness: "+petrinetLogic.liveList().size() +"\n";
        otherInfo+= "==== Live List ====\n";
        for (TransitionInterface trans : petrinetLogic.liveList()) {
            otherInfo += " " + trans.getName() + "\n";
        }
        otherInfo+= "==== Live List End====\n";

        otherInfo += "Unbounded: "+petrinetLogic.unboundedPlaces().size() +"\n";
        otherInfo += "==== Unbounded Places ====\n";
        for (PlaceInterface place : petrinetLogic.unboundedPlaces()) {
            otherInfo += " " + place.getName() + "<" + (place.getNumTokens() == -1 ? "Infinity" :
                    "" + place.getNumTokens()) + ">" + "\n";
        }
        otherInfo+= "==== Unbounded Places End ====\n";
        otherInfo += "Bounded: "+petrinetLogic.boundedPlaces().size() +"\n";
        otherInfo += "==== Bounded Places ====\n";
        for (PlaceInterface place : petrinetLogic.boundedPlaces()) {
            otherInfo += " " + place.getName() + "<" + (place.getNumTokens() == -1 ? "Infinity" :
                    "" + place.getNumTokens()) + ">" + "\n";
        }

        otherInfo += "==== Reachable Places (Extra) ====\n";
        for (PlaceInterface place : petrinetLogic.reachablePlaces()) {
            otherInfo += " " + place.getName() + "<" + (place.getNumTokens() == -1 ? "Infinity" :
                    "" + place.getNumTokens()) + ">" + "\n";
        }
        otherInfo += "==== Reachable Places End ====\n";

        otherInfo += "==== Unreachable Places (Extra) ====\n";
        for (PlaceInterface place : petrinetLogic.unreachablePlaces()) {
            otherInfo += " " + place.getName() + "<" + (place.getNumTokens() == -1 ? "Infinity" :
                    "" + place.getNumTokens()) + ">" + "\n";
        }
        otherInfo += "==== Unreachable Places End====\n";
        return otherInfo;
    }
}
