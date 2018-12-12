package logic;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoverabilityNode implements CoverabilityNodeInterface{
    public enum CompareResult{
        NONE, LESS, EQUAL, GREATER;
    }

    private int[] petriState = null;
    private CoverabilityNodeInterface parent = null;
    private List<CoverabilityNodeInterface> smallerAncestors = null;
    private List<CoverabilityNodeInterface> children = null;
    private CoverabilityNodeInterface equalAncestor = null;
    private TransitionInterface transition = null;
    private boolean terminal = false;

    private int lastChild;  //Purely internal for traversal.

    public CoverabilityNode(CoverabilityNodeInterface parent, TransitionInterface transition, int[] placeState){
        petriState = placeState.clone();
        this.parent = parent;
        this.transition = transition;

        //Check to see if this is all infinity.
        CoverabilityNodeInterface temp;
        //Determine whether there are propagatable ancestors
        if(parent != null && !parent.hasPropagatableAncestors()) {
            smallerAncestors = new ArrayList<CoverabilityNodeInterface>();
            ArrayList<CoverabilityNodeInterface> redundantSmaller = new ArrayList<CoverabilityNodeInterface>();
            temp = parent;
            while (temp != null) {

                CompareResult compareValue = compare(temp);
                if (compareValue == CompareResult.GREATER) {
                    if (!redundantSmaller.contains(temp)) {
                        smallerAncestors.add(temp);
                        if(temp.getSmallerAncestor() != null) {
                            redundantSmaller.addAll(temp.getSmallerAncestor());
                        }
                    }
                }
                else if(compareValue == CompareResult.EQUAL){
                    terminal = true;
                    equalAncestor = temp;
                    break;
                }
                temp = temp.getParent();
            }
            if(smallerAncestors.size() == 0) smallerAncestors = null;
        }
//        if(smallerAncestors.size() != 0 && terminal == true){
//            System.out.println("Huh.  This doesn't really matter, but I want to know if this happens");
//            smallerAncestors.clear();
//        }

        children = new ArrayList<CoverabilityNodeInterface>();
//        boolean terminal = true;
//        for(int i = 0; i < placeState.length; i++){
//            if(placeState[i] != -1) {
//                terminal = false;
//                break;
//            }
//        }
//        this.terminal = this.terminal || terminal;
//        if(terminal) return;


    }

    @Override
    public void addChild(CoverabilityNodeInterface newChild){
        children.add(newChild);
    }

    @Override
    public List<CoverabilityNodeInterface> getChildren(){
        return children;
    }
    @Override
    public int[] getPetriState() {
        return petriState.clone();
    }

    @Override
    public CoverabilityNodeInterface getParent() {
        return parent;
    }

    @Override
    public List<CoverabilityNodeInterface> getSmallerAncestor() {
        return smallerAncestors;
    }

    @Override
    public CoverabilityNodeInterface getEqualAncestor() {
        return equalAncestor;
    }

    @Override
    public boolean hasPropagatableAncestors() {

        return smallerAncestors != null && smallerAncestors.size() != 0;
    }

    @Override
    public CoverabilityNodeInterface generatePropagate() {
        int[] placeState = new int[petriState.length];
        for(CoverabilityNodeInterface n : smallerAncestors){
            int[] otherState = n.getPetriState();
            for(int i = 0; i < petriState.length; i++){
                if(petriState[i] == -1 || petriState[i] > otherState[i]){
                    placeState[i] = -1;
                }
                else placeState[i] = petriState[i];
            }
        }
        CoverabilityNodeInterface toReturn = new CoverabilityNode(this, null, placeState);


        return toReturn;
    }

    @Override
    public boolean isTerminal() {
        return terminal;
    }

    @Override
    public TransitionInterface usedTransition() {
        return transition;
    }

    public CompareResult compare(CoverabilityNodeInterface other){
        boolean lessThan = false;
        boolean greaterThan = false;
        boolean onlyNeg = true;
        int[] otherPetriState = other.getPetriState();
        if(petriState.length != otherPetriState.length) throw new ArrayIndexOutOfBoundsException();
        for(int i = 0; i <  petriState.length; i++) {
            if (petriState[i] > otherPetriState[i] || (petriState[i] == -1) && otherPetriState[i] != -1) {
                greaterThan = true;
                if(petriState[i] != -1) onlyNeg = false;
                if(lessThan) return CompareResult.NONE;
            } else if ((petriState[i] < otherPetriState[i] || otherPetriState[i] == -1) && petriState[i] != -1) {
                lessThan = true;
                if(otherPetriState[i] != -1) onlyNeg = false;
                if(greaterThan) return CompareResult.NONE;
            }

        }
        if(!onlyNeg) {
            if (greaterThan) return CompareResult.GREATER;
            if (lessThan) return CompareResult.LESS;
            return CompareResult.EQUAL;
        }
        else{
            if(greaterThan || lessThan) return CompareResult.NONE;
            else return CompareResult.EQUAL;
        }
    }

    @Override
    public String toString()
    {
        String toReturn = Arrays.toString(petriState);
        if(children != null && children.size() != 0) {
            toReturn += "\n>";
            for (CoverabilityNodeInterface n : children) {
                toReturn += n.toString();
            }
            toReturn += "\n<";
        }
        return "\n" + toReturn;
    }
}