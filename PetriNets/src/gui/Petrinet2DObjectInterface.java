package gui;

import java.awt.*;

public interface Petrinet2DObjectInterface {

    /**
     * Get location point of the object on
     * the 2d canvase
     * @return
     */
    Point getPoint();





    /**
     * this takes in the 2d graphics and
     * draws the petrinet object
     * @param graphics
     */
    void draw(Graphics graphics);

    void setPoint(Point point);
    
    void setID (String Id);

    /**
     * Unique identifier that
     * returns the id of the object
     * drawn on the canvas
     * @param
     * @return
     */
    String getID();


    /**
     * Sets the name of the
     * 2d object model
     * @param Name
     */
    void setName(String Name);

    /**
     * returns name of the
     * 2d Object
     * @return
     */
    String getName();

    /**
     *  this returns the
     *  proximity that
     *  determines the region
     *  or grid an element
     *  occupies in the gui
     *
     * @return
     */
    int getTolerance ();

    /**
     *
     * @return clickable location for listening
     * to right click
     *
     */
    Point getEditClickableLocation();
}
