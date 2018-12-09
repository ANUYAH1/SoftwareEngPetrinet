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
    void saveProject(ProjectInterface project) throws Exception;

    /**
     * * Takes in the path and
     *      * returns the project object
     *      * expects not null string
     * @param path
     * @return
     * @throws IllegalArgumentException throws exception for illegal argument
     * @throws FileNotFoundException throws when file is not found
     */
    ProjectInterface loadProject(String path) throws Exception;

    /**
     * Get extension type from
     * the storage interface
     *
     * @return
     */
    String getExtension ();

    /**
     * returns supported file description
     * @return
     */
    String getSupportedFileDescription();
}
