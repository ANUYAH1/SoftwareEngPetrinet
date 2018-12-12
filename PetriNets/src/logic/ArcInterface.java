package logic;

public interface ArcInterface {
	/**
	 * Sets the name of the arc
	 * @param name
	 */
	void setName(String name);

	/**
	 * @return The name of the arc.
	 */
	String getName();

	/**
	 * Sets the weight of the arc.
	 * @param weight The desired weight of the arc.
	 */
	void setWeight(int weight);

	/**
	 *
	 * @return The weight of the arc.
	 */
	int getWeight();

	/**
	 * Sets the origin of the arc.
	 * @param p
	 */
	void setOrigin(ArcEndpointInterface p);

	/**
	 * Gets the origin of the arc.
	 * @return The origin of the arc.
	 */
	ArcEndpointInterface getOrigin();

	/**
	 * Sets the destination of the arc
	 * @param p
	 */
	void setDestination(ArcEndpointInterface p);

	/**
	 *
	 * Gets the destination of the arc
	 * @return The destination of the arc.
	 */
	ArcEndpointInterface getDestination();

	/**
	 * Removes an arc from a petrinet
	 */
	void removeArc();

	/**
	 * Adds a previously removed arc back to the petri net
	 */
	void readdArc();

}
