package gui;

import javax.swing.*;
import java.awt.*;

public class PetrinetPanel extends JPanel {
    private DrawPanel drawPanel;
    private PetrinetSelectObjectPanel selectObjectPanel;
    private LogPanel logPanel;
    public PetrinetPanel(){
        drawPanel = new DrawPanel();
        selectObjectPanel = new PetrinetSelectObjectPanel();
        logPanel = new LogPanel();

        this.setLayout(new BorderLayout());
        this.add(selectObjectPanel,BorderLayout.NORTH);
        this.add(drawPanel,BorderLayout.CENTER);
        this.add(logPanel,BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension(800,700));
    }

}
