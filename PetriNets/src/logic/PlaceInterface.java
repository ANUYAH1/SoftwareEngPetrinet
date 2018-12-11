package logic;

public interface PlaceInterface extends ArcEndpointInterface {
	void setName(String name);
	String getName();
	
	void setNumTokens(int tokens);
	void addTokens(int tokens);
	void removeTokens(int tokens);
	int getNumTokens();

	boolean hasTokens(int tokens);
}
