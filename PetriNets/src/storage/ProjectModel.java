package storage;

import gui.Petrinet2DObjectInterface;

import java.util.ArrayList;

public class ProjectModel implements ProjectInterface {
    private String Name;
    private String filePath;
    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String Name) {
        this.Name = Name;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(String FilePath) {
        this.filePath = filePath;
    }

    @Override
    public ArrayList<Petrinet2DObjectInterface> getGuiObjects() {
        return null;
    }

    @Override
    public void setGuiObjects(ArrayList<Petrinet2DObjectInterface>  guiObjects) {

    }
}
