package logic;

import java.util.List;

public interface CoverabilityNodeInterface {
    int[] getPetriState();
    CoverabilityNodeInterface getParent();
    List<CoverabilityNodeInterface> getSmallerAncestor();
    CoverabilityNodeInterface getEqualAncestor();

    boolean hasPropagatableAncestors();
    CoverabilityNodeInterface generatePropagate();

    boolean isTerminal();

    List<CoverabilityNodeInterface> getChildren();

    void addChild(CoverabilityNodeInterface newChild);

    TransitionInterface usedTransition();
}
