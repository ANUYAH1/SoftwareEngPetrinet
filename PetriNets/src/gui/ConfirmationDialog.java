package gui;

import javax.swing.*;
import java.awt.*;

public class ConfirmationDialog {
    private String typedText = null;

    private boolean postiveSelection;

    /**
     * Returns null if the typed string was invalid;
     * otherwise, returns the string as the user entered it.
     *
     */
    public String getValidatedText() {
        return typedText;
    }



    /** Creates the reusable dialog. */
    public ConfirmationDialog(Component aFrame, String Message, String title,String confirmationText, String cancelText) {

        //Create an array of the text and components to be displayed.


        Object[] array = {Message};
        String [] options =new String[]{
                confirmationText,cancelText
        };


        int res =JOptionPane.showOptionDialog(aFrame,array,title,
                JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        postiveSelection =res==0;

    }

    /**
     * returns a true if the user clicked
     * okay
     * @return
     */

    public boolean isPostiveSelection() {
        return postiveSelection;
    }

}
