package logic;

import java.util.List;

public interface ArcEndpointInterface {

	/**
	 *
	 * @return The incoming arcs for this ArcEndpoint
	 */
	List<ArcInterface> getArcInputs();
	/**
	 *
	 * @return The outgoing arcs for this ArcEndpoint
	 */
	List<ArcInterface> getArcOutputs();

	/**
	 *
	 * @param a A new incoming arc for an arc endpoint
	 */
	void addArcInput(ArcInterface a);
	/**
	 *
	 * @param a A new outgoing arc for an arc endpoint
	 */
	void addArcOutput(ArcInterface a);

	/**
	 *
	 * @param a An incoming arc to be removed.
	 */
	void removeArcInput(ArcInterface a);

	/**
	 *
	 * @param a An outgoing arc to be removed.
	 */
	void removeArcOutput(ArcInterface a);

	/**
	 * Removes an endpoint
	 */
	void remove();

	/**
	 * Readds a previously removed endpoint
	 */
	void readd();

}
