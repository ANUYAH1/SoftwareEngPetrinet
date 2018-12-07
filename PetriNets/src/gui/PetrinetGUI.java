package gui;

import storage.ProjectInterface;
import storage.ProjectModel;
import storage.Storage;
import storage.StorageInterface;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * This houses the GUI component
 * of this project it is the main
 * frame
 */
public class PetrinetGUI extends JFrame implements MenuBarListener {
    //petrinet back end instance initialized here
    private ProjectInterface petrinetProject;
    private PetrinetPanel petrinetPanel;
    private MenuBar menuBar;
    private    JFileChooser fileChooser;
    private StorageInterface storage;
    private JOptionPane optionPane;

    private FileFilter fileFilter ;


    public PetrinetGUI(){
        menuBar = new MenuBar(this);
        petrinetPanel = new PetrinetPanel();
        fileChooser= new JFileChooser();
        storage = new Storage();
        optionPane = new JOptionPane();

        fileFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {

                return f.getPath().endsWith("."+storage.getExtension());
            }

            @Override
            public String getDescription() {
                return "Petri Project";
            }
        };

        enablePanel(petrinetPanel,false);
        this.getContentPane().add(petrinetPanel);
        this.setJMenuBar(menuBar);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("PETRINETS");
        this.pack();
        this.setVisible(true);





    }

    /**
     * An inefficient way of disabling
     * panels
     * @param panel
     * @param enable
     */
    private void enablePanel(JPanel panel , boolean enable){
        Component [] components = panel.getComponents();
        for (Component c:
             components) {
            c.setEnabled(enable);
            try {
                JPanel jp = (JPanel) c;
                if (jp != null) {
                    enablePanel(jp, enable);
                }
            }catch (Exception ex){

            }
        }
        panel.revalidate();
    }
    @Override
    public void openFileMenuCommand() {
        try{
            // get plast accessed folder
            // from storage

            fileChooser.showOpenDialog(this);
            fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
            File file = fileChooser.getSelectedFile();
            fileChooser.setFileFilter(fileFilter);
            petrinetProject =storage.loadProject(file.getPath());
            if(petrinetProject!=null){
                this.setTitle("Petrinet- "+petrinetProject.getName());
            }
            String message ="Project " + petrinetProject.getName() + " Loaded!!";
            LogUIModel log =LogUIModel.createErrorLog(message);
            petrinetPanel.log(log);
            enablePanel(petrinetPanel,true);
            // send this file path to storage
        }catch (Exception ex){
            String message = "Failed to open Project";

            LogUIModel log =LogUIModel.createErrorLog(message);
            petrinetPanel.log(log);
        }

    }

    @Override
    public void saveFileMenuCommand() {

        try{

            if(petrinetProject== null){
                newMenuCommand();
                return;
            }


            fileChooser.showSaveDialog(this);
            fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
            fileChooser.setFileFilter(fileFilter);



            File file = fileChooser.getSelectedFile();
            if(file  != null &&!file.getPath().isEmpty()) {
                if (!file.exists()) {
                    file.createNewFile();
                }

                ArrayList<Petrinet2DObjectInterface> objectCopy =
                        petrinetPanel.getGuiObjects();
                petrinetProject.setGuiObjects(objectCopy);

                petrinetProject.setFilePath(file.getPath());
                storage.saveProject(petrinetProject);
                String message = "Info: Project " + petrinetProject.getName() + " Saved!!";
                LogUIModel log =LogUIModel.createErrorLog(message);
                petrinetPanel.log(log);
            }
            // send this file path to storage
        }catch (Exception ex){
            String message = "Error: Could not save project!!";
            LogUIModel log =LogUIModel.createErrorLog(message);
            petrinetPanel.log(log);
        }
    }

    @Override
    public void exitMenuCommand() {
        this.dispatchEvent(new
                WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void undoMenuCommand() {
        petrinetPanel.undo();
    }

    @Override
    public void redoMenuCommand() {
        petrinetPanel.redo();
    }

    @Override
    public void aboutMenuCommand() {
        try{
            String about = storage.getAbout();
            JOptionPane.showMessageDialog(this,about,"About",JOptionPane.PLAIN_MESSAGE);
        }catch (Exception ex){
            String message = "Error: Could not load About!!";
            LogUIModel log =LogUIModel.createErrorLog(message);
            petrinetPanel.log(log);
        }

    }

    @Override
    public void newMenuCommand() {
        try{
            String projectName= "";
            // get plast accessed folder
            CustomDialog dialog = new CustomDialog(this,"Create New Project","New Project");
            if (dialog.isPostiveSelection()){
                projectName = dialog.getValidatedText();
                this.setTitle("Petrinet- "+projectName);
                petrinetProject = new ProjectModel();
                petrinetProject.setName(projectName);
                enablePanel(petrinetPanel,true);
                //clear the gui
                petrinetPanel.clearProject();
                String message =" New Project " + petrinetProject.getName() + " Starte!!: Dont forget to save " +
                ":)";
                LogUIModel log =LogUIModel.createInfoLog(message);
                petrinetPanel.log(log);
            }




        }catch (Exception ex){
            String message ="Failed to open project!!";
            LogUIModel log =LogUIModel.createErrorLog(message);
            petrinetPanel.log(log);
        }
    }
}
