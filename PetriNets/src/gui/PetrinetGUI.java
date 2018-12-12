package gui;

import logic.PetriNet;
import logic.PetriNetInterface;
import storage.ProjectInterface;
import storage.ProjectModel;
import storage.Storage;
import storage.StorageInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;

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
    private final String LAST_USED_FOLDER  = "last_used_folder_pref";

    private Preferences prefs ;

    private PetriNetInterface petrinetLogic ;

    private Image appIcon;

    public PetrinetGUI(){

        petrinetLogic = new PetriNet();
        storage = new Storage();
        setUpGUI();


    }

    private void setUpGUI() {
        petrinetPanel = new PetrinetPanel(petrinetLogic);
        menuBar = new MenuBar(this);
        prefs = Preferences.userRoot().node(getClass().getName());
        fileChooser= new JFileChooser(prefs.get(LAST_USED_FOLDER,
                new File(".").getAbsolutePath()));

        optionPane = new JOptionPane();
        fileFilter = new FileFilter() {
            @Override
            public boolean accept(File f) {

                return f.getPath().endsWith(storage.getExtension());
            }

            @Override
            public String getDescription() {
                return storage.getSupportedFileDescription();
            }
        };
        fileChooser.setFileFilter(fileFilter);
        setIcon();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitMenuCommand();
            }
        });
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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

            int res =fileChooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
                File file = fileChooser.getSelectedFile();
                fileChooser.setFileFilter(fileFilter);
                petrinetProject = storage.loadProject(file.getPath());
                this.setTitle("Petrinet- " + petrinetProject.getName());

                petrinetLogic.abortTreeTraversal();
                petrinetLogic.emptyPetriNet();

                petrinetPanel.clearProject();


                petrinetPanel.loadProject(petrinetProject.getGuiObjects());
                String message = "Project " + petrinetProject.getName() + " Loaded!!";
                LogUIModel log = LogUIModel.createInfoLog(message);
                petrinetPanel.log(log);
                enablePanel(petrinetPanel, true);
                prefs.put(LAST_USED_FOLDER, fileChooser.getSelectedFile().getParent());
            }
            // send this file path to storage
        }catch (Exception ex){
            String message = "Failed to open Project";

            LogUIModel log =LogUIModel.createErrorLog(message);
            petrinetPanel.log(log);
            ex.printStackTrace();
        }

    }

    @Override
    public void saveFileMenuCommand() {

        try{

            if(petrinetProject== null){
                newMenuCommand();
                return;
            }


            int res =fileChooser.showSaveDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();


                petrinetLogic.abortTreeTraversal();

                ArrayList<Petrinet2DObjectInterface> objectCopy =
                        petrinetPanel.getProjectStateForSave();
                petrinetProject.setGuiObjects(objectCopy);

                petrinetProject.setFilePath(file.getPath());
                storage.saveProject(petrinetProject);
                prefs.put(LAST_USED_FOLDER, fileChooser.getSelectedFile().getParent());
                String message = "Project " + petrinetProject.getName() + " Saved!!";
                LogUIModel log = LogUIModel.createInfoLog(message);
                petrinetPanel.log(log);

            }
            // send this file path to storage
        }catch (Exception ex){
            String message = "Could not save project!!:"+ex.getMessage();
            LogUIModel log =LogUIModel.createErrorLog(message);
            petrinetPanel.log(log);
        }
    }

    @Override
    public void exitMenuCommand() {
        if (petrinetProject!=null) {
//            if (petrinetProject.getGuiObjects().size() !=
//                    petrinetPanel.getProjectStateForSave().size()) {
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(this,
                        "Save changes to " + petrinetProject.getName() + " ?", "Save Changes");
                if (confirmationDialog.isPostiveSelection()) {
                    saveFileMenuCommand();
                   System.exit(0);
                } else {
                    System.exit(0);
                }
//            } else {
//                this.dispatchEvent(new
//                        WindowEvent(this, WindowEvent.WINDOW_CLOSING));
//            }
        }else{
            System.exit(0);
        }
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

            JOptionPane.showMessageDialog(this,about,
                    "About",JOptionPane.PLAIN_MESSAGE,new ImageIcon(appIcon));
        }catch (Exception ex){
            String message = "Could not load About!!";
            LogUIModel log =LogUIModel.createErrorLog(message);
            petrinetPanel.log(log);
        }

    }

    @Override
    public void newMenuCommand() {
        try{
            String projectName= "";
            // get plast accessed folder
            CustomDialog dialog = new CustomDialog(this,"Create New Project","New Project","");
            if (dialog.isPostiveSelection()){
                projectName = dialog.getValidatedText();
                this.setTitle("Petrinet- "+projectName);
                petrinetProject = new ProjectModel();
                petrinetProject.setName(projectName);

                petrinetPanel.clearProject();

                enablePanel(petrinetPanel,true);
                //clear the gui
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
    private void setIcon(){
        try {
            appIcon = ImageIO.read(getClass().getResource("/icons/app_logo.png"));

            this.setIconImage(appIcon);



        }catch (Exception ex){
            System.out.println("Error in loading image");
        }
    }

}
