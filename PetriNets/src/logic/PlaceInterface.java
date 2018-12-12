package logic;


public interface PlaceInterface extends ArcEndpointInterface {
	/**
	 *
	 * @param name The new name of this place.
	 */
	void setName(String name);

	/**
	 *
	 * @return The name of this place
	 */
	String getName();

	/**
	 * PRECONDITION:  tokens >= 0 OR tokens == -1.  If tokens == -1, that represents infinity.
	 * POSTCONDITION:  The place now has tokens tokens
	 *
	 * @param tokens The new number of tokens on this place
	 */
	void setNumTokens(int tokens);

	/**
	 * Adds a number of tokens to the place.
	 *
	 * PRECONDITION:  tokens must be positive
	 * POSTCONDITION:  The place now has tokens more tokens.  No change if the place has infinite tokens.
	 *
	 * @param tokens The number of tokens to add
	 */
	void addTokens(int tokens);

	/**
	 * Removes a number of tokens from a place.
	 *
	 * PRECONDITION:  tokens must be positive and greater than the value of getNumTokens()
	 * POSTCONDITION:  The place now has tokens fewer tokens.  No change if the place has infinite tokens.
	 *
	 * @param tokens The number of tokens to remove
	 */
	void removeTokens(int tokens);

	/**
	 *
	 * @return The number of tokens on a place.
	 */
	int getNumTokens();

	/**
	 * Determines if a place has at least a certain number of tokens.
	 * @param tokens The desired number of tokens
	 * @return true if the place has infinite tokens or has more tokens than the parameter tokens.
	 */
	boolean hasTokens(int tokens);
}
