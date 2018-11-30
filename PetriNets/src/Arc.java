public class Arc implements ArcInterface{
    private String name;
    private int weight;
    private ArcEndpointInterface origin;
    private ArcEndpointInterface destination;


    public Arc(ArcEndpointInterface origin, ArcEndpointInterface destination){
        this.origin = origin;
        this.destination = destination;
        origin.addArcOutput(this);
        destination.addArcOutput(this);
        weight = 1;
        name = "";
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setWeight(int weight) {
        if(weight >= 0)
            this.weight = weight;
        else throw new IllegalArgumentException("weight is negative:  " + weight);
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setOrigin(ArcEndpointInterface p) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArcEndpointInterface getOrigin() {
        return origin;
    }

    @Override
    public void setDestination(ArcEndpointInterface p) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArcEndpointInterface getDestination() {
        return destination;
    }
}
