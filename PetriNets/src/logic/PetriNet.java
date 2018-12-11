package logic;

import java.util.ArrayList;
import java.util.List;

public class PetriNet {
    private ArrayList<PlaceInterface> places = new ArrayList<PlaceInterface>();
    private ArrayList<TransitionInterface> transitions = new ArrayList<TransitionInterface>();

    public PetriNet(){

    }

    public void addPlace(PlaceInterface newPlace){
        if(newPlace.getArcInputs().size() != 0 || newPlace.getArcOutputs().size() != 0){
            throw new IllegalArgumentException("Place has Arcs in PetriNet");
        }
        if(!places.contains(newPlace)) {
            places.add(newPlace);
        }
        else throw new IllegalArgumentException("Place already exists");
    }

    public void addTransition(TransitionInterface newTransition){
        if(newTransition.getArcInputs().size() != 0 || newTransition.getArcOutputs().size() != 0){
            throw new IllegalArgumentException("Transition has Arcs");
        }
        if(!transitions.contains(newTransition)) {
            transitions.add(newTransition);
        }
        else throw new IllegalArgumentException("Transition already exists in PetriNet");
    }

    public List<PlaceInterface> getPlaces(){
        return places;
    }

    public List<TransitionInterface> getTransitions(){
        return transitions;
    }

    public List<ArcInterface> getArcs(){
        ArrayList<ArcInterface> arcs = new ArrayList<ArcInterface>();
        for(PlaceInterface p : places){
            for(ArcInterface a : p.getArcOutputs()){
                arcs.add(a);
            }
        }
        for(TransitionInterface t : transitions){
            for(ArcInterface a : t.getArcOutputs()){
                arcs.add(a);
            }
        }
        return arcs;
    }

    public List<TransitionInterface> liveList(int liveNum){
        return null;
    }

    public List<PlaceInterface> unboundedPlaces(){
        return null;
    }
    public boolean hasNext(){
        return false;
    }
    public void next(){

    }
    public void complete(){

    }
    public CoverabilityTree getTree(){
        return null;
    }





}
