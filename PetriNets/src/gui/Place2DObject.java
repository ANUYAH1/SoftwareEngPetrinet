package gui;

import logic.PlaceInterface;

import java.awt.*;
import java.util.Random;

/**
 * This class is responsible for
 * drawing a
 * place
 */
public class Place2DObject implements  Petrinet2DObjectInterface{
    private Point point;
    private PlaceInterface place;
    private final int PLACE_RADIUS =40;
    private final int TOKEN_RADIUS =5;
    private Element objectType;
    private final int TOLERANCE =25;
    private final int TEXTPADDING =40;
    private final Color TRANSITIONCOLOR = Color.black;
    private final Color TEXTCOLOR = Color.BLACK;
    private final Color TOKENCOLOR = Color.black;
    private final Color INFINITYCOLOR = Color.red;
    private final int MAXTOKENTODISPLAY =10;

    private String Id;

    //TODO change this name back to the back end's
    private String Name;
    private Point editableClickLocation;

    public Place2DObject(PlaceInterface place){
        this.place = place;
    }



    @Override
    public Point getPoint() {
        return point;
    }


    @Override
    public void draw(Graphics graphics) {
        Random ran = new Random();
        graphics.setColor(TRANSITIONCOLOR);
        graphics.drawOval((int)point.getX(),(int)point.getY(),PLACE_RADIUS,PLACE_RADIUS);

        // This simple logic is to draw tokens within


        String tokenString = "";

        if(place.getNumTokens()>=0) {
            // the circle
            int randX = 0;
            int randY = 0;
            graphics.setColor(TOKENCOLOR);
            int tokenCount = Math.min(MAXTOKENTODISPLAY,place.getNumTokens());
            int randPadding=12;
            for (int i = 0; i < tokenCount; i++) {
                randX = ran.nextInt(PLACE_RADIUS - randPadding) +
                        (int) point.getX() + (i == 0 ? randPadding : 0);
                randY = ran.nextInt(PLACE_RADIUS - randPadding) +
                        (int) point.getY() + (i == 0 ? randPadding : 0);

                graphics.fillOval(randX, randY, TOKEN_RADIUS, TOKEN_RADIUS);
            }
            tokenString = " <"+place.getNumTokens()+">";
        }else{
            tokenString = " <Infinity>";
        }

        graphics.setColor(TEXTCOLOR);

        graphics.drawString(getName() +tokenString,(int)point.getX(),
                (int)point.getY()+PLACE_RADIUS+TEXTPADDING);
        //this is to make the text clickable
        editableClickLocation = new Point(point.x,point.y);

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
        return TOLERANCE;
    }

    @Override
    public Point getEditClickableLocation() {
        return editableClickLocation;
    }


    public PlaceInterface getPlace() {
        return place;
    }
}
