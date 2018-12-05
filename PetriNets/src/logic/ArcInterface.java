package logic;

public interface ArcInterface {
	void setName(String name);
	String getName();
	
	void setWeight(int weight);
	int getWeight();
	
	void setOrigin(ArcEndpointInterface p);
	ArcEndpointInterface getOrigin();
	void setDestination(ArcEndpointInterface p);
	ArcEndpointInterface getDestination();
}
