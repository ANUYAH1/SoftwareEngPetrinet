package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    public void undo() {
        drawPanel.undo();
    }
    public void redo(){
        drawPanel.redo();
    }

    public ArrayList<Petrinet2DObjectInterface> getGuiObjects() {
        return drawPanel.getGuiObjects();
    }

    public void clearProject() {
        drawPanel.clearAll();
    }
}
