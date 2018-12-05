package logic;

public interface TransitionInterface extends ArcEndpointInterface {
	void setName(String name);
	String getName();
	
	boolean attemptTransition();
	
}
