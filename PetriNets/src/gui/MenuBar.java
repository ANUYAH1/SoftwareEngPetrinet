package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar implements ActionListener {
    private MenuBarListener listener;
    public MenuBar(MenuBarListener listener){

        this.listener =listener;

        // Work on File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("file_menu");

        JMenuItem newFile = new JMenuItem("New");

        newFile.setName("file_menu_new");
        newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.VK_ALT));
        newFile.addActionListener(this);

        JMenuItem openFile = new JMenuItem("Open");

        openFile.setName("file_menu_open");
        openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.VK_ALT));
        openFile.addActionListener(this);

        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.setName("file_menu_save");
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.VK_ALT));
        saveFile.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("file_menu_exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,KeyEvent.VK_ALT));
        exit.addActionListener(this);
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(exit);

        this.add(fileMenu);
        //end work on File Menu


        //Work on Edit Menu

        JMenu editMenu = new JMenu("Edit");
        editMenu.setName("edit_menu");
        JMenuItem undo = new JMenuItem("Undo");
        undo.setName("edit_menu_undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,KeyEvent.VK_ALT));
        undo.addActionListener(this);

        JMenuItem redo = new JMenuItem("Redo");
        redo.setName("edit_menu_redo");
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.VK_ALT));
        redo.addActionListener(this);
        editMenu.add(undo);
        editMenu.add(redo);
        this.add(editMenu);



        // End work on Edit Menu


        // Work on Help Menu
        JMenu helpMenu = new JMenu("Help");
        editMenu.setName("help_menu");
        JMenuItem about = new JMenuItem("About");
        about.setName("help_menu_about");

        about.addActionListener(this);
        helpMenu.add(about);
        this.add(helpMenu);
        //End work on Help Menu
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JMenuItem)e.getSource()).getName().equals("help_menu_about")){
            listener.aboutMenuCommand();
        }else if(((JMenuItem)e.getSource()).getName().equals("edit_menu_undo")){
            listener.undoMenuCommand();
        }else if(((JMenuItem)e.getSource()).getName().equals("edit_menu_redo")){
            listener.redoMenuCommand();
        }else if(((JMenuItem)e.getSource()).getName().equals("file_menu_exit")){
            listener.exitMenuCommand();
        }else if(((JMenuItem)e.getSource()).getName().equals("file_menu_save")){
            listener.saveFileMenuCommand();
        }else if(((JMenuItem)e.getSource()).getName().equals("file_menu_open")){
            listener.openFileMenuCommand();
        }else if(((JMenuItem)e.getSource()).getName().equals("file_menu_new")){
            listener.newMenuCommand();
        }
    }
}
