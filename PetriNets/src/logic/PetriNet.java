package logic;

import java.util.ArrayList;
import java.util.List;

public class PetriNet implements PetriNetInterface {
    private List<PlaceInterface> places = new ArrayList<PlaceInterface>();
    private List<TransitionInterface> transitions = new ArrayList<TransitionInterface>();
    private CoverabilityNodeInterface currentNode = null;
    private CoverabilityNodeInterface root = null;
    private List<CoverabilityNodeInterface> leaves = null;

    private boolean treeComplete = false;

    public PetriNet(){

    }
    @Override
    public void addPlace(PlaceInterface newPlace){
//        if(newPlace.getArcInputs().size() != 0 || newPlace.getArcOutputs().size() != 0){
//            throw new IllegalArgumentException("Place has Arcs in PetriNet");
//        }
        if(!places.contains(newPlace)) {
            places.add(newPlace);
        }
        else throw new IllegalArgumentException("Place already exists");
    }

    @Override
    public void addTransition(TransitionInterface newTransition){
//        if(newTransition.getArcInputs().size() != 0 || newTransition.getArcOutputs().size() != 0){
//            throw new IllegalArgumentException("Transition has Arcs");
//        }
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
    public List<TransitionInterface> liveList(){
        if(root == null) return null;
        List<CoverabilityNodeInterface> leaves = getLeaves();
        List<CoverabilityNodeInterface> assessedPoints = new ArrayList<CoverabilityNodeInterface>();

        boolean[] netResult = new boolean[transitions.size()];
        for(int i = 0; i < netResult.length; i++){
            netResult[i] = true;
        }
        for(CoverabilityNodeInterface node : leaves) {
            boolean[] localResult = new boolean[transitions.size()];
            for(int i = 0; i < localResult.length; i++){
                localResult[i] = false;
            }
            CoverabilityNodeInterface miniRoot = node.getEqualAncestor();
            if (miniRoot == null) {
                netResult = localResult;
                break;
            }
            if(assessedPoints.contains(miniRoot)){
                continue;
            }
            assessedPoints.add(miniRoot);
            recursiveLiveHelper(miniRoot, localResult);
            for (int i = 0; i < netResult.length; i++) {
                netResult[i] = netResult[i] && localResult[i];
            }
        }
        List<TransitionInterface> toReturn = new ArrayList<TransitionInterface>();
        for(int i = 0; i < netResult.length; i++){
            if(netResult[i]){
                toReturn.add(transitions.get(i));
            }
        }
        return toReturn;
    }

    @Override
    public List<TransitionInterface> notLiveList() {
        if(root == null) return null;
        List<CoverabilityNodeInterface> leaves = getLeaves();
        List<CoverabilityNodeInterface> assessedPoints = new ArrayList<CoverabilityNodeInterface>();

        boolean[] netResult = new boolean[transitions.size()];
        for(int i = 0; i < netResult.length; i++){
            netResult[i] = true;
        }
        for(CoverabilityNodeInterface node : leaves) {
            boolean[] localResult = new boolean[transitions.size()];
            for(int i = 0; i < localResult.length; i++){
                localResult[i] = false;
            }
            CoverabilityNodeInterface miniRoot = node.getEqualAncestor();
            if (miniRoot == null) {
                netResult = localResult;
                break;
            }
            if(assessedPoints.contains(miniRoot)){
                continue;
            }
            assessedPoints.add(miniRoot);
            recursiveLiveHelper(miniRoot, localResult);
            for (int i = 0; i < netResult.length; i++) {
                netResult[i] = netResult[i] && localResult[i];
            }
        }
        List<TransitionInterface> toReturn = new ArrayList<TransitionInterface>();
        for(int i = 0; i < netResult.length; i++){
            if(!netResult[i]){
                toReturn.add(transitions.get(i));
            }
        }
        return toReturn;
    }
    private List<CoverabilityNodeInterface> getLeaves(){
        if(leaves != null) return leaves;
        if(root == null) return null;
        leaves = new ArrayList<CoverabilityNodeInterface>();
        recursiveLeafFinder(root, leaves);
        return leaves;
    }
    private void recursiveLeafFinder(CoverabilityNodeInterface node, List<CoverabilityNodeInterface> leaves){
        if(node.getChildren().size() == 0){
            leaves.add(node);
            return;
        }
        for(CoverabilityNodeInterface n : node.getChildren()){
            recursiveLeafFinder(n, leaves);
        }

    }
    private void recursiveLiveHelper(CoverabilityNodeInterface node, boolean[] live){
        for(CoverabilityNodeInterface n : node.getChildren()){
            TransitionInterface t = n.usedTransition();
            if(t != null){
                live[transitions.indexOf(t)] = true;
            }
            recursiveLiveHelper(n, live);
        }
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
        if(!treeComplete) return null;
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
        if(!treeComplete) return null;
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
    public boolean isReachable(int[] destinationState) {
        return false;
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
        leaves = null;
    }

    @Override
    public boolean treeTraversalCompleted() {
        return treeComplete;
    }

    @Override
    public boolean next(){
        if(root == null) {
            startTreeTraversal();
        }
        if(currentNode == null)
        {
            treeComplete = true;
            return false;
        }
        leaves = null;
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
        leaves = null;
        root = null;
        currentNode = null;
        treeComplete = false;
    }

}
