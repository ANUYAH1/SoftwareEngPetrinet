package storage;

import gui.Petrinet2DObjectInterface;

import java.util.ArrayList;

public interface ProjectInterface {
     /**
      * returnns the name
      * of the project
      * @return
      */
     String getName();

     /**
      * Sets the name of the project
      * @param Name
      */
     void setName(String Name);

     /**
      * Returns the file path of
      * the project
      * @return
      */
     String getFilePath();

     /**
      * Sets the file path of the project
      * @param FilePath
      */
    void setFilePath(String FilePath);

     /**
      * This returns the saved gui object
      * that also houses interfaces for the
      * instance
      * @return
      */
    ArrayList<Petrinet2DObjectInterface> getGuiObjects ();


     /**
      * This set the gui objects for easy
      * save
      * @param guiObjects
      */
     void setGuiObjects(ArrayList<Petrinet2DObjectInterface> guiObjects);
}
