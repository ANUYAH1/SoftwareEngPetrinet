package logic;

import java.util.ArrayList;
import java.util.List;


public class PetriNet implements PetriNetInterface {

    private ArrayList<PlaceInterface> places = new ArrayList<PlaceInterface>();
    private ArrayList<TransitionInterface> transitions = new ArrayList<TransitionInterface>();
    private CoverabilityNodeInterface currentNode = null;
    private CoverabilityNodeInterface root = null;

    public PetriNet(){

    }
    @Override
    public void addPlace(PlaceInterface newPlace){
        if(newPlace.getArcInputs().size() != 0 || newPlace.getArcOutputs().size() != 0){
            throw new IllegalArgumentException("Place has Arcs in PetriNet");
        }
        if(!places.contains(newPlace)) {
            places.add(newPlace);
        }
        else throw new IllegalArgumentException("Place already exists");
    }

    @Override
    public void addTransition(TransitionInterface newTransition){
        if(newTransition.getArcInputs().size() != 0 || newTransition.getArcOutputs().size() != 0){
            throw new IllegalArgumentException("Transition has Arcs");
        }
        if(!transitions.contains(newTransition)) {
            transitions.add(newTransition);
        }
        else throw new IllegalArgumentException("Transition already exists in PetriNet");
    }

    @Override
    public List<PlaceInterface> getPlaces(){
        return places;
    }

    @Override
    public List<TransitionInterface> getTransitions(){
        return transitions;
    }

    @Override
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

    @Override
    public List<TransitionInterface> liveList(int liveNum){
        return null;
    }

    @Override
    public List<TransitionInterface> notLiveList(int liveNum) {
        return null;
    }

    @Override
    public List<PlaceInterface> unboundedPlaces(){
        boolean[] boundedPlaces = new boolean[places.size()];
        ArrayList<PlaceInterface> toReturn = new ArrayList<PlaceInterface>();
        unboundedPlacesRecursiveHelper(root, boundedPlaces);
        for(int i = 0; i < boundedPlaces.length; i++){
            if(boundedPlaces[i]){
                toReturn.add(places.get(i));
            }
        }
        return toReturn;
    }

    @Override
    public List<PlaceInterface> boundedPlaces() {

        boolean[] boundedPlaces = new boolean[places.size()];
        ArrayList<PlaceInterface> toReturn = new ArrayList<PlaceInterface>();
        unboundedPlacesRecursiveHelper(root, boundedPlaces);
        for(int i = 0; i < boundedPlaces.length; i++){
            if(!boundedPlaces[i]){
                toReturn.add(places.get(i));
            }
        }
        return toReturn;
    }

    @Override
    public List<PlaceInterface> reachablePlaces(){
        boolean[] reachablePlaces = new boolean[places.size()];
        ArrayList<PlaceInterface> toReturn = new ArrayList<PlaceInterface>();
        reachablePlacesRecursiveHelper(root, reachablePlaces);
        for(int i = 0; i < reachablePlaces.length; i++){
            if(reachablePlaces[i]){
                toReturn.add(places.get(i));
            }
        }
        return toReturn;
    }

    @Override
    public List<PlaceInterface> unreachablePlaces() {
        boolean[] reachablePlaces = new boolean[places.size()];
        ArrayList<PlaceInterface> toReturn = new ArrayList<PlaceInterface>();
        reachablePlacesRecursiveHelper(root, reachablePlaces);
        for(int i = 0; i < reachablePlaces.length; i++){
            if(!reachablePlaces[i]){
                toReturn.add(places.get(i));
            }
        }
        return toReturn;
    }

    private void unboundedPlacesRecursiveHelper(CoverabilityNodeInterface node, boolean[] boundedPlaces){
        for(CoverabilityNodeInterface n : node.getChildren()){
            int[] state = n.getPetriState();
            for(int i = 0; i < state.length; i++){
                if(state[i] == -1){
                    boundedPlaces[i] = true;
                }
            }
            unboundedPlacesRecursiveHelper(n, boundedPlaces);
        }
    }
    private void reachablePlacesRecursiveHelper(CoverabilityNodeInterface node, boolean[] reachablePlaces){

        int[] nodeState = node.getPetriState();

        for(CoverabilityNodeInterface n : node.getChildren()){
            int[] state = n.getPetriState();
            for(int i = 0; i < state.length; i++){
                if(state[i] >= nodeState[i]){
                    reachablePlaces[i] = true;
                }
            }
            unboundedPlacesRecursiveHelper(n, reachablePlaces);
        }
    }

    @Override
    public void startTreeTraversal(){
        int[] startState = getPlaceStateArray();

        root = new CoverabilityNode(null, null, startState);
        currentNode = root;
    }

//    @Override
//    public boolean hasNext(){
//        //Determine if there is a transition that can be used
//        CoverabilityNodeInterface nextNode
//        if(currentNode.isTerminal()){
//
//        }
//
//        List<CoverabilityNodeInterface> children = currentNode.getChildren();
//
//        CoverabilityNodeInterface lastNode = null;
//        TransitionInterface lastTransition = null;
//        if(children.size() != 0) {
//            children.get(children.size() - 1);
//            lastTransition = lastNode.usedTransition();
//        }
//
//        int index;
//        if(lastTransition == null){
//            index = 0;
//        }
//        else{
//            index = transitions.indexOf(lastTransition);
//        }
//
//        for(int i = index + 1; i < transitions.size(); i++){
//            TransitionInterface transition = transitions.get(i);
//            if(transition.checkTransition()){
//                transition.executeTransition();
//                int[] newPlaceState = new int[places.size()];
//                for(int j = 0; j < newPlaceState.length; j++){
//                    newPlaceState[j] = places.get(j).getNumTokens();
//                }
//                CoverabilityNodeInterface newNode = new CoverabilityNode(currentNode);
//            }
//        }
//        return false;
//    }

    @Override
    public boolean next(){
        if(currentNode == null){
            return false;
        }
        if(currentNode.hasPropagatableAncestors())
        {
            if(currentNode.getChildren().size() == 0)
            {
                currentNode = currentNode.generatePropagate();
                setPlaceStateArray(currentNode.getPetriState());
                currentNode.getParent().addChild(currentNode);
                return true;
            }else{
                currentNode = currentNode.getParent();

                setPlaceStateArray(currentNode.getPetriState());
                return next();
            }
        }
        int index = -1;
        if(currentNode.isTerminal()){
            currentNode = currentNode.getParent();
            int[] state = currentNode.getPetriState();
            setPlaceStateArray(state);
        }
        if(currentNode.getChildren().size() != 0){

            index = transitions.indexOf(currentNode.getChildren().get(currentNode.getChildren().size()-1).usedTransition());
        }
        for (int i = index + 1; i < transitions.size(); i++) {

            TransitionInterface t = transitions.get(i);

            if (t.checkTransition()) {
                t.executeTransition();
                int[] newPlaceState = getPlaceStateArray();
                CoverabilityNodeInterface newNode = new CoverabilityNode(currentNode, t, newPlaceState);
                currentNode.addChild(newNode);
                currentNode = newNode;
                return true;
            }
        }

        currentNode = currentNode.getParent();
        if(currentNode != null) setPlaceStateArray(currentNode.getPetriState());

        return next();
    }

    private int[] getPlaceStateArray(){
        int[] newPlaceState = new int[places.size()];
        for (int j = 0; j < newPlaceState.length; j++) {
            newPlaceState[j] = places.get(j).getNumTokens();
        }
        return newPlaceState;
    }
    private void setPlaceStateArray(int[] placeArray){
        for(int i = 0; i < places.size(); i++){
            places.get(i).setNumTokens(placeArray[i]);
        }
    }

    @Override
    public void complete(){
        while(next());
    }

    @Override
    public CoverabilityNodeInterface getCoverabilityTreeRoot() {
        return root;
    }

    @Override
    public void abortTreeTraversal(){
        root = null;
        currentNode = null;
    }

}
