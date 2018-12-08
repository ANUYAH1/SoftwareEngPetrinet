package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This houses the whole main
 * petrinet structure
 */
public class PetrinetPanel extends JPanel implements PetrinetSelectObjectListener,LogListener {
    private DrawPanel drawPanel;
    private PetrinetSelectObjectPanel selectObjectPanel;
    private LogPanel logPanel;
    public PetrinetPanel(){
        drawPanel = new DrawPanel(this);
        selectObjectPanel = new PetrinetSelectObjectPanel(this);
        logPanel = new LogPanel();

        this.setLayout(new BorderLayout());
        this.add(selectObjectPanel,BorderLayout.NORTH);
        this.add(drawPanel,BorderLayout.CENTER);
        this.add(logPanel,BorderLayout.SOUTH);
        this.setEnabled(false);

        this.setPreferredSize(new Dimension(800,700));
    }




    @Override
    public void selectedPetrinetObject(SelectObject selectObject) {
        drawPanel.setCurrentPetrinetObject(selectObject);
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
    public ArrayList<Petrinet2DObjectInterface> getGuiObjects() {
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

    public void loadObjects(ArrayList<Petrinet2DObjectInterface> guiObjects) {
        drawPanel.loadElements(guiObjects);
    }
}
