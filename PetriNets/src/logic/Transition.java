package logic;


import java.util.ArrayList;
import java.util.List;

public class Transition implements TransitionInterface{
    private String name;

    private ArrayList<ArcInterface> arcInputs = new ArrayList<ArcInterface>();
    private ArrayList<ArcInterface> arcOutputs = new ArrayList<ArcInterface>();

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean checkTransition()
    {
        for(ArcInterface a : arcInputs)
        {
            PlaceInterface place = ((PlaceInterface)a.getOrigin());
            if(!place.hasTokens(a.getWeight())){

                return false;
            }
        }
        return true;
    }

    @Override
    public void executeTransition()
    {
        if(checkTransition() == false) return;
        for(ArcInterface a : arcInputs)
        {
            PlaceInterface place = ((PlaceInterface)a.getOrigin());
            if(!place.hasTokens(a.getWeight())){
                throw new IllegalStateException("Transition can not be executed.");
            }
            else {
                place.removeTokens(a.getWeight());
            }
        }
        for(ArcInterface a : arcOutputs)
        {
            PlaceInterface place = ((PlaceInterface)a.getDestination());
            place.addTokens(a.getWeight());
        }
    }


    @Override
    public List<ArcInterface> getArcInputs()
    {
        return new ArrayList<ArcInterface>(arcInputs);
    }

    @Override
    public List<ArcInterface> getArcOutputs()
    {
        return new ArrayList<ArcInterface>(arcOutputs);
    }

    @Override
    public void addArcInput(ArcInterface a)
    {
        if(a.getDestination() != this) {
            throw new IllegalArgumentException("Illegal logic.AbstractArc for this logic.Transition");
        }
        if(!arcInputs.contains(a)){
            arcInputs.add(a);
        }
        else throw new IllegalArgumentException("arc a already exists in this logic.Transition");
    }

    @Override
    public void addArcOutput(ArcInterface a)
    {
        if(a.getOrigin() != this) {
            throw new IllegalArgumentException("Illegal logic.AbstractArc for this logic.Transition");
        }
        if(!arcOutputs.contains(a)){
            arcOutputs.add(a);
        }

        else throw new IllegalArgumentException("arc a already exists in this logic.Transition");
    }

    @Override
    public void removeArcInput(ArcInterface a)
    {
        if(arcInputs.contains(a)){
            arcInputs.remove(a);
        }
        else throw new IllegalArgumentException("arc does not exist here.");
    }

    @Override
    public void removeArcOutput(ArcInterface a)
    {
        if(arcOutputs.contains(a)){
            arcOutputs.remove(a);
        }
        else throw new IllegalArgumentException("arc does not exist here.");

    }

}
