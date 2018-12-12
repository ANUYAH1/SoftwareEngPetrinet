package gui;

import logic.ArcEndpointInterface;
import logic.ArcInterface;

import java.awt.*;

/**
 * This class is responsible for
 * drawing the arc in the petrinet
 * project it implements the Petrnet2dObjectInterface
 */
public class Arc2DObject implements Petrinet2DObjectInterface {
    private ArcInterface arc;
    private Point point;
    private Point destinationPoint ;
    private final Color ARCCOLOR= Color.BLACK;
    private final Color ARCHEADCOLOR = Color.BLACK;
    private final Color NAMECOLOR = Color.black;
    private final int ARCTHICKNESS = 4;
    private final int ARROWHEAD = 6;
    private final int TEXTPADDING =15;
    private final int TOLERANCE = 30;
    private String Id;
    private String Name;

    private Petrinet2DObjectInterface origin;
    private Petrinet2DObjectInterface destination;
    private Point editClickableLocation;

    /**
     * returns the origin of the arc
     * @return
     */
    public Petrinet2DObjectInterface getOrigin() {
        return origin;
    }

    /**
     * sets the origin of the arc
     * @param origin
     */
    public void setOrigin(Petrinet2DObjectInterface origin) {
        this.origin = origin;

    }

    /**
     * returns the destination of
     * the arc
     * @return
     */
    public Petrinet2DObjectInterface getDestination() {
        return destination;
    }

    /**
     * Sets the destination of the arc
     * @param destination
     */
    public void setDestination(Petrinet2DObjectInterface destination) {
        this.destination = destination;
    }

    /**
     * Initializes the arc with the
     * back end instances using its interface
     * for decoupling
     * @param arc
     */
    public Arc2DObject(ArcInterface arc){
        this.arc = arc;

    }

    /**
     * returns the origin point of the arc
     * @return
     */
    @Override
    public Point getPoint() {
        return point;
    }



    /**
     * Get the destination point
     * of the arc
     * @return
     */
    public Point getDestinationPoint() {
        return destinationPoint;
    }

    /**
     * Sets the destination point of the
     * arc
     * @param destinationPoint
     */
    public void setDestinationPoint(Point destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(ARCCOLOR);

        graphics.drawLine((int)point.getX(),
                (int)point.getY(),
                (int)destinationPoint.getX(),
        (int)destinationPoint.getY());
        // draw triangular head;
        int [] x =null;
        int [] y = null;
        //this is towards the left
        // so let arrow face right
        if(destinationPoint.getX() - point.getX() >0){
            x =new int[] {(int)destinationPoint.getX() ,(int)destinationPoint.getX(),
                    (int)destinationPoint.getX()+(ARROWHEAD*2)};
            y =new int []{
                (int) destinationPoint.getY() + ARROWHEAD, (int) destinationPoint.getY() - ARROWHEAD,
                        (int) destinationPoint.getY()
            };
        }else{
            x =new int[] {(int)destinationPoint.getX() ,(int)destinationPoint.getX(),
                    (int)destinationPoint.getX()-(ARROWHEAD*2)};
            y =new int []{
                    (int) destinationPoint.getY() + ARROWHEAD, (int) destinationPoint.getY() - ARROWHEAD,
                    (int) destinationPoint.getY()
            };
        }

        graphics.fillPolygon(x,y,3);

        // get the center of the arc and plavce name tag
        int centerX = (int)(point.getX() +destinationPoint.getX())/2;
        int centerY = (int)(point.getY()+destinationPoint.getY())/2 ;
        editClickableLocation= new Point(centerX,centerY);
        graphics.drawString(Name + " <weight: "+arc.getWeight()+">",centerX+TEXTPADDING,centerY+TEXTPADDING);


        // now lets draw the weight



    }

    @Override
    public void setPoint(Point point) {
        this.point =point;
    }

    @Override
    public void setID(String Id) {
        this.Id = Id;

    }

    @Override
    public String getID() {
        return Id;
    }

    @Override
    public void setName(String Name) {
        this.Name = Name;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public int getTolerance() {
        return TOLERANCE;
    }

    public ArcInterface getArc() {
        return arc;
    }

    public Point getEditClickableLocation(){
        return editClickableLocation;
    }


}
