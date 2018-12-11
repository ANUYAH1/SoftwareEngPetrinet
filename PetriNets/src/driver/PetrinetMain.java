package driver;

import gui.PetrinetGUI;

import javax.swing.*;

public class PetrinetMain {
    public static void main (String [] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PetrinetGUI(); // Let the constructor do the job
            }
        });
    }
}
