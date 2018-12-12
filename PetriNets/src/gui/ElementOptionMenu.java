package gui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ElementOptionMenu extends JPopupMenu {
    private JMenuItem editItem;
    private JMenuItem deleteItem;
    private JMenuItem fireTransitionItem;
    private JMenuItem copyItem;
    private JMenuItem pasteItem;

    public ElementOptionMenu(ActionListener listener){
        editItem = new JMenuItem("Edit");
        fireTransitionItem = new JMenuItem("Fire Transition");
        deleteItem = new JMenuItem("Delete");
        copyItem =  new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        editItem.addActionListener(listener);
        deleteItem.addActionListener(listener);
        copyItem.addActionListener(listener);
        pasteItem.addActionListener(listener);
        editItem.setName("element_option_edit");
        deleteItem.setName("element_option_delete");
        copyItem.setName("element_option_copy");
        pasteItem.setName("element_option_paste");
        fireTransitionItem.setName("element_option_fire");
        this.add(fireTransitionItem);
        this.add(editItem);
        this.add(deleteItem);
        this.add(copyItem);
        this.add(pasteItem);

    }

    public void enablePaste (boolean isEnabled){
        pasteItem.setEnabled(isEnabled);
    }

    public void setFireOptionVisible(boolean b) {
        fireTransitionItem.setVisible(b);
    }

    public void setFireOptionEnabled(boolean b) {
        fireTransitionItem.setEnabled(b);
    }
}
