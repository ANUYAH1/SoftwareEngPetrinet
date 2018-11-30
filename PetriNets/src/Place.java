import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public class Place implements PlaceInterface {
    private String name = "";
    private int numTokens = 0;

    private ArrayList<ArcInterface> arcInputs = new ArrayList<ArcInterface>();
    private ArrayList<ArcInterface> arcOutputs = new ArrayList<ArcInterface>();

    public Place(){

    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setNumTokens(int tokens) {
        numTokens = tokens;
    }

    @Override
    public void addTokens(int tokens) {
        if(tokens >= 0){
            numTokens += tokens;
        }
        else throw new IllegalArgumentException("tokens is less than 0.  tokens = " + tokens);
    }

    @Override
    public void removeTokens(int tokens) {
        if(tokens >= 0 && numTokens - tokens >= 0){
            numTokens -= tokens;
        }
        else {
            if(tokens < 0){
                throw new IllegalArgumentException("tokens is less than 0.  tokens = " + tokens);
            }
            else throw new NumberFormatException("removing more tokens than exist.  tokens = " + tokens + ".  numTokens = " + numTokens);
        }

    }

    @Override
    public int getNumTokens() {
        return numTokens;
    }

    @Override
    public List<ArcInterface> getArcInputs() {
        return new ArrayList<ArcInterface>(arcInputs);
    }

    @Override
    public List<ArcInterface> getArcOutputs() {
        return new ArrayList<ArcInterface>(arcOutputs);
    }

    @Override
    public void addArcInput(ArcInterface a) {
        if(a.getDestination() != this) {
            throw new IllegalArgumentException("Illegal Arc for this Place");
        }
        if(!arcInputs.contains(a)){
            arcInputs.add(a);
        }
        else throw new IllegalArgumentException("arc a already exists in this place");
    }

    @Override
    public void addArcOutput(ArcInterface a) {

        if(a.getOrigin() != this) {
            throw new IllegalArgumentException("Illegal Arc for this Place");
        }
        if(!arcOutputs.contains(a)){
            arcOutputs.add(a);
        }

        else throw new IllegalArgumentException("arc a already exists in this place");
    }

    @Override
    public void removeArcInput(ArcInterface a) {
        if(arcInputs.contains(a)){
            arcInputs.remove(a);
        }
        else throw new IllegalArgumentException("arc does not exist here.");
    }

    @Override
    public void removeArcOutput(ArcInterface a) {
        if(arcOutputs.contains(a)){
            arcOutputs.remove(a);
        }
        else throw new IllegalArgumentException("arc does not exist here.");

    }
}
