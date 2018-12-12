package logic;

import java.util.List;

public interface PetriNetInterface {
    /**
     *
     * @param place A place to be added to the PetriNet.
     */
    void addPlace(PlaceInterface place);

    /**
     *
     * @param place Place To Remove
     */
    void removePlace(PlaceInterface place);

    /**
     *
     * @param transition A Transition to be added to the PetriNet.
     */
    void addTransition(TransitionInterface transition);

    /**
     *
     * @param transition Transition to remove.
     */
    void removeTransition(TransitionInterface transition);

    /**
     *
     * @return A list of places in a consistent order.
     */
    List<PlaceInterface> getPlaces();

    /**
     *
     * @return A list of transitions in a consistent order.
     */
    List<TransitionInterface> getTransitions();

    /**
     * Generated a list of arcs
     *
     * @return A list of arcs in an INCONSISTENT order.
     */
    List<ArcInterface> getArcs();

    /**
     *
     * PRECONDITIONS:  The coverability tree should be completed
     * POSTCONDITIONS:  No changes, just the return value;
     *
     * @return A list of live nodes.
     */
    List<TransitionInterface> liveList();

    /**
     *
     * PRECONDITIONS:  The coverability tree should be completed
     * POSTCONDITIONS:  No changes, just the return value;
     *
     * @return A list of dead nodes.
     */
    List<TransitionInterface> notLiveList();

    /**
     *
     * PRECONDITIONS:  The coverability tree should be completed
     * POSTCONDITIONS:  No changes, just the return value;
     *
     * @return A list of places that can become infinite.
     */
    List<PlaceInterface> unboundedPlaces();

    /**
     *
     * PRECONDITIONS:  The coverability tree should be completed
     * POSTCONDITIONS:  No changes, just the return value;
     *
     * @return A list of places that can not become infinite.
     */
    List<PlaceInterface> boundedPlaces();

    /**
     * This function is not needed, as it is not a requested feature of the PetriNet.
     *
     * PRECONDITIONS:  The coverability tree should be completed
     * POSTCONDITIONS:  No changes, just the return value;
     *
     * @return A list of unreachable places.
     */
    List<PlaceInterface> unreachablePlaces();

    /**
     * This function is not needed, as it is not a requested feature of the PetriNet.
     *
     * PRECONDITIONS:  The coverability tree should be completed
     * POSTCONDITIONS:  No changes, just the return value;
     *
     * @return A list of reachable places.
     */
    List<PlaceInterface> reachablePlaces();

    /**
     * Returns whether a certain state or marking is accessible (NOT IMPLEMENTED IN PETRINET YET)
     *
     * @param destinationState A marking for a state
     * @return Whether destinationState is reachable.
     */
    boolean isReachable(int[] destinationState);

    /**
     * Executes one step in the coverability tree.
     * @return Whether there was a next or not.
     */
    boolean next();

    /**
     *
     * @return Whether there is a next.
     */
    boolean hasNext();

    /**
     * Completes the coverability tree.
     */
    void complete();

    /**
     * Gets the root of the tree, therefore the tree itself.
     *
     * @return root
     */
    CoverabilityNodeInterface getCoverabilityTreeRoot();

    /**
     * Removes an existing coverability tree from the PetriNet.
     * PRECONDITIONS:  none
     * POSTCONDITIONS:  All tree-related function should return null or false.
     */
    void abortTreeTraversal();

    /**
     *
     * Starts or restarts the coverability tree in the PetriNet.
     * PRECONSITIONS:  none
     * POSTCONDITIONS:  The tree is now root and is an incompleted tree.
     */
    void startTreeTraversal();

    /**
     * Checks to see if the tree traversal is completed.
     * @return true if last call of next() returned false.
     */
    boolean treeTraversalCompleted();

    /**
     * Resets the petrinet to an empty petri net
     */
    void emptyPetriNet();


}
