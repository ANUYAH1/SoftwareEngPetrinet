package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Storage implements StorageInterface {
    private final String EXTENSION =".petri";
    public Storage(){

    }



    /**
     * this loads the text from the
     * resource files
     * @return credits
     */
    public String getAbout() throws FileNotFoundException {
        Scanner scanner = new Scanner( new
                File(getClass().getResource("/text/about.txt").getPath()));
        String about ="";
        while(scanner.hasNextLine()){
            about+=scanner.nextLine() + "\n";
        }
        return about;


    }

    @Override
    public void saveProject(ProjectInterface project) {
        //Save to xml file here
    }

    @Override
    public ProjectInterface loadProject(String path) {
        // read from XML file here
        return new ProjectModel();
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
