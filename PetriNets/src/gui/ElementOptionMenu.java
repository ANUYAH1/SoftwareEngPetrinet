package gui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ElementOptionMenu extends JPopupMenu {
    private JMenuItem editItem;
    private JMenuItem deleteItem;
    private JMenuItem copyItem;
    private JMenuItem pasteItem;

    public ElementOptionMenu(ActionListener listener){
        editItem = new JMenuItem("Edit");
        deleteItem = new JMenuItem("Delete");
        copyItem =  new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        editItem.addActionListener(listener);
        deleteItem.addActionListener(listener);
        copyItem.addActionListener(listener);
        pasteItem.addActionListener(listener);
        this.add(editItem);
        this.add(deleteItem);
        this.add(copyItem);
        this.add(pasteItem);

    }

    public void enablePaste (boolean isEnabled){
        pasteItem.setEnabled(isEnabled);
    }

}