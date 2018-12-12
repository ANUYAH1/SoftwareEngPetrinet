package gui;

import logic.TransitionInterface;


import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


/**
 * Concrete class that holds
 * the gui information of a transition
 */
public class Transition2DObject implements Petrinet2DObjectInterface {
    private Point point;
    private TransitionInterface transition;
    private final int TRANSITION_WIDTH =5;
    private final int TRANSITION_HEIGHT =20;
    private final int TEXTPADDING =15;
    private final Color TRANSITIONCOLOR = Color.black;
    private final Color TEXTCOLOR = Color.BLACK;
    private final Color TRANSITIONFIREDCOLOR = Color.green;
    private final int TOLERANCE = 30;
    private String Id;
    private ArrayList<Petrinet2DObjectInterface> arcIn;
    private ArrayList<Petrinet2DObjectInterface> arcOut;
    private Point editableClickLocation;

    //TODO change this name back to the back end's
    private String Name;

    public Transition2DObject(TransitionInterface transition){
        this.transition = transition;
        this.arcIn = new ArrayList<>();
        this.arcOut = new ArrayList<>();

    }

    public List<Petrinet2DObjectInterface> getArcsIn (){
        return  arcIn;
    }

    public List<Petrinet2DObjectInterface> getArcsOut(){
        return arcOut;
    }

    public void removeArcOut(Petrinet2DObjectInterface arc){
        arcOut.remove(arc);

    }

    @Override
    public Point getPoint() {
        return point;
    }


    @Override
    public void draw(Graphics graphics) {
        Color currentColor = transition.justFired()?TRANSITIONFIREDCOLOR:TRANSITIONCOLOR;
        graphics.setColor(currentColor);
        graphics.fillRect((int)point.getX(),(int)point.getY(),TRANSITION_WIDTH,TRANSITION_HEIGHT);
        graphics.drawString(getName(),(int)point.getX(),(int)point.getY()+TRANSITION_HEIGHT+TEXTPADDING);
        editableClickLocation = new Point(point.x,point.y+TRANSITION_HEIGHT+TEXTPADDING);


    }

    @Override
    public void setPoint(Point point) {
        this.point = point;
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
        return  TOLERANCE;
    }

    @Override
    public Point getEditClickableLocation() {
        return editableClickLocation;
    }

    /**
     * returns the transition
     * back end instance
     * @return
     */
    public TransitionInterface getTransition() {
        return transition;
    }



}
