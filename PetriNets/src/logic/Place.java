package logic;

import java.util.ArrayList;
import java.util.List;

public class Place implements PlaceInterface {
    private String name = "";
    private int numTokens = 0;
    private int originalNumTokens = 0;

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
        setNumTokens(tokens, true);
    }
    @Override
    public void setNumTokens(int tokens, boolean permanent) {
        if(tokens >= -1){
            numTokens = tokens;
            if(permanent) originalNumTokens = tokens;
        }
        else throw new IllegalArgumentException("tokens is less than 0.  tokens = " + tokens);
    }
    @Override
    public void resetTokens(){
        numTokens = originalNumTokens;
    }
    @Override
    public void addTokens(int tokens) {
        if(tokens >= 0){
            if(numTokens != -1){
                numTokens += tokens;
            }
        }
        else throw new IllegalArgumentException("tokens is less than 0.  tokens = " + tokens);
    }

    @Override
    public void removeTokens(int tokens) {
        if(tokens < 0){
            throw new IllegalArgumentException("tokens is less than 0.  tokens = " + tokens);
        }
        if(numTokens == -1) return;
        if(numTokens - tokens >= 0) {
            numTokens -= tokens;
        }
        else {
            throw new NumberFormatException("removing more tokens than exist.  tokens = " + tokens + ".  numTokens = " + numTokens);
        }

    }

    @Override
    public int getNumTokens() {
        return numTokens;
    }

    @Override
    public boolean hasTokens(int tokens) {
        return numTokens == -1 || numTokens >= tokens;
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
            throw new IllegalArgumentException("Illegal logic.AbstractArc for this logic.Place");
        }
        if(!arcInputs.contains(a)){
            arcInputs.add(a);
        }
        else throw new IllegalArgumentException("arc a already exists in this place");
    }

    @Override
    public void addArcOutput(ArcInterface a) {

        if(a.getOrigin() != this) {
            throw new IllegalArgumentException("Illegal logic.AbstractArc for this logic.Place");
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

    @Override
    public void remove() {
        for(ArcInterface a : arcInputs){
            a.getOrigin().removeArcOutput(a);
        }
        for(ArcInterface a : arcOutputs){
            a.getDestination().removeArcInput(a);
    }

    }

    @Override
    public void readd() {
        List<ArcInterface> arcsIn = arcInputs;
        List<ArcInterface> arcsOut = arcOutputs;
        arcInputs = new ArrayList<ArcInterface>(arcsIn.size());
        arcOutputs = new ArrayList<ArcInterface>(arcsOut.size());

        for(ArcInterface a : arcsIn){
            a.readdArc();
        }
        for(ArcInterface a : arcsOut){
            a.readdArc();
        }
    }
}
