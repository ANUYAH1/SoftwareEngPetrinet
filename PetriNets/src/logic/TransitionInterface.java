package logic;

public interface TransitionInterface extends ArcEndpointInterface {

	/**
	 * Sets the name of the transition
	 * @param name
	 */
	void setName(String name);

	/**
	 * Gets the name of a transition
	 * @return The transition's name/
	 */
	String getName();

	/**
	 *
	 * @return true if all the places connected by arcs from the place to the transition have enough tokens.  Else, false.
	 */
	boolean checkTransition();

	/**
	 * Fires the transition if checkTransition() returns true.
	 */
	void executeTransition();

	/**
	 *
	 * @param justFired True if it was just fired in the coverability tree.
	 */
	void setJustFired(boolean justFired);

	/**
	 *
	 * @return Whether this transition ws just fired in the coverability tree
	 */
	boolean justFired();
	
}
