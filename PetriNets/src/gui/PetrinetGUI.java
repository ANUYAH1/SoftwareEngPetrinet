package gui;

import javax.swing.*;
import java.awt.*;
//<div>Icons made by <a href="https://www.flaticon.com/authors/dave-gandy" title="Dave Gandy">Dave Gandy</a>
// from <a href="https://www.flaticon.com/"
// title="Flaticon">www.flaticon.com</a> is licensed by
// <a href="http://creativecommons.org/licenses/by/3.0/"

// title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
public class PetrinetGUI extends JFrame implements MenuBarListener {
    //petrinet back end instance initialized here
    private PetrinetPanel petrinetPanel;
    private MenuBar menuBar;
    private Desktop desktop;
    public PetrinetGUI(){
        menuBar = new MenuBar(this);
        petrinetPanel = new PetrinetPanel();
        this.getContentPane().add(petrinetPanel);
        this.setJMenuBar(menuBar);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("PETRINETS");
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void openFileMenuCommand() {

    }

    @Override
    public void saveFileMenuCommand() {

    }

    @Override
    public void exitMenuCommand() {

    }

    @Override
    public void undoMenuCommand() {

    }

    @Override
    public void redoMenuCommand() {

    }

    @Override
    public void aboutMenuCommand() {

    }
}
