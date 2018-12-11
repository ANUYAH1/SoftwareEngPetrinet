package gui;
 
import javax.swing.*;
import java.beans.*; //property change stuff
import java.awt.*;
import java.awt.event.*;

/**
 * This class creates a custom input
 * dialog popup
 */
class CustomDialog {
    private String typedText = null;
    private JTextField textField;
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
    public CustomDialog(Component aFrame,String Message,String title) {
        textField = new JTextField(10);

        //Create an array of the text and components to be displayed.


        Object[] array = {Message, textField};
        String [] options =new String[]{
                "Ok","Cancel"
        };


        int res =JOptionPane.showOptionDialog(aFrame,array,title,
                JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        postiveSelection =res==0;
        typedText = textField.getText();
        textField.setText(null);
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