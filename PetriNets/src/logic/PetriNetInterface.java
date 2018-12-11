package logic;

import java.util.List;

public interface PetriNetInterface {
    void addPlace(PlaceInterface place);
    void addTransition(TransitionInterface transition);

    List<PlaceInterface> getPlaces();
    List<TransitionInterface> getTransitions();
    List<ArcInterface> getArcs();

    List<TransitionInterface> liveList(int liveNum);
    List<TransitionInterface> notLiveList(int liveNum);

    List<PlaceInterface> unboundedPlaces();
    List<PlaceInterface> boundedPlaces();

    List<PlaceInterface> unreachablePlaces();
    List<PlaceInterface> reachablePlaces();

//    boolean hasNext();
    boolean next();
    void complete();

    CoverabilityNodeInterface getCoverabilityTreeRoot();
    void abortTreeTraversal();
    void startTreeTraversal();
}
