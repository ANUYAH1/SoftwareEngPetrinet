package logic;

public abstract class AbstractArc implements ArcInterface{
    private String name;
    private int weight;
    private ArcEndpointInterface origin;
    private ArcEndpointInterface destination;


    public AbstractArc(ArcEndpointInterface origin, ArcEndpointInterface destination){
        this.origin = origin;
        this.destination = destination;
        origin.addArcOutput(this);
        destination.addArcInput(this);
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
        else throw new IllegalArgumentException("weight can not be negative:  weight = " + weight);
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

    @Override
    public boolean equals(Object other){
        if(other == this) return true;
        if (!(other instanceof ArcEndpointInterface)) {
            return false;
        }
        ArcInterface arc = (ArcInterface) other;
        if(arc.getOrigin() == origin && arc.getDestination() == destination)
            return true;
        return false;
    }
    @Override
    public void removeArc(){
        origin.removeArcOutput(this);
        destination.removeArcInput(this);
    }
    @Override
    public void readdArc(){
        origin.addArcOutput(this);
        destination.addArcInput(this);

    }
}
