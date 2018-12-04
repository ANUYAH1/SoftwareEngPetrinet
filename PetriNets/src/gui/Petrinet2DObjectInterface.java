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
     * @param grpahics
     */
    void draw(Graphics2D grpahics);

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

}
