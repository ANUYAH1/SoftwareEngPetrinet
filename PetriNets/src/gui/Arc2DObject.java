package gui;

import logic.ArcEndpointInterface;
import logic.ArcInterface;

import java.awt.*;

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
    private String Id;
    private String Name;
    private SelectObject selectObject;



    public Arc2DObject(ArcInterface arc){
        this.arc = arc;
    }
    @Override
    public Point getPoint() {
        return point;
    }

    @Override
    public SelectObject getObjectType() {
        return selectObject;
    }

    @Override
    public void setObjectType(SelectObject selectObject) {
        this.selectObject = selectObject;
    }

    public Point getDestinationPoint() {
        return destinationPoint;
    }

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
        int [] x = new int [] {(int)destinationPoint.getX() ,(int)destinationPoint.getX(),
                (int)destinationPoint.getX()+(ARROWHEAD*2)};
        int [] y = new int []{
                (int) destinationPoint.getY() + ARROWHEAD, (int) destinationPoint.getY() - ARROWHEAD,
                (int) destinationPoint.getY()
        };

        graphics.fillPolygon(x,y,3);

        // get the center of the arc and plavce name tag
        int centerX = (int)(point.getX() +destinationPoint.getX())/2;
        int centerY = (int)(point.getY()+destinationPoint.getY())/2 ;

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
}
