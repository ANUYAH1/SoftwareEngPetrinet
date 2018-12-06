package storage;

import java.io.FileNotFoundException;
import java.util.logging.Filter;

public interface StorageInterface {
    /**
     * Returns the about
     * of the project
     * @return
     */
    String getAbout() throws FileNotFoundException;

    /**
     * saves project based
     * on the GUI data
     * @param project
     */
    void saveProject(ProjectInterface project);

    /**
     * Takes in the path and
     * returns the project object
     * @param path
     * @return
     */
    ProjectInterface loadProject(String path);

    /**
     * Get extension type from
     * the storage interface
     *
     * @return
     */
    String getExtension ();


}
