package gui;

import logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.UUID;

/**
 * This houses the core part of
 * the GUI it is responsible
 * for all drawings and majority
 * of the user interactions as well
 * ass the coverability tree panel
 */
public class DrawPanel extends JPanel implements MouseListener, ActionListener {
    // this stores all to be drawn on the
    // gui
    private ArrayList<Petrinet2DObjectInterface> objects;
    private DrawCanvas canvas;
    // Grid dimensions
    private final int  rows  = 20;
    private final int columns =20;
    private Element currentPetrinetObject;
    private LogListener  logListener;


    private ArrayList<Petrinet2DObjectInterface>redoHistory;
    private Petrinet2DObjectInterface originObject;

    private Point originPoint ;
    private Point destinationPoint ;
    private Petrinet2DObjectInterface destinationObject;

    private Petrinet2DObjectInterface currentSelectedObject ;
    private ElementOptionMenu elementOptionMenu;

    private AttributePanel attributePanel;

    // back end logic that houses
    // the algorithms
    private PetriNetInterface petrinetLogic ;




    public DrawPanel(LogListener logListener,PetriNetInterface petrinetLogic){
        this.logListener = logListener;

        canvas = new DrawCanvas();
        canvas.addMouseListener(this);
        objects =new ArrayList<>();
        redoHistory = new ArrayList<>();
        attributePanel = new AttributePanel();
        this.elementOptionMenu = new ElementOptionMenu(this);
        elementOptionMenu.enablePaste(false);
        this.setLayout(new BorderLayout());
        //keep the coverability tree panel about 1/4 of the
        // width of the screen



        this.petrinetLogic = petrinetLogic;
        this.add(attributePanel,BorderLayout.EAST);
        this.add(canvas,BorderLayout.CENTER);
        this.currentPetrinetObject = Element.NONE;
    }

    public void setCurrentPetrinetObject(Element element) {
        currentPetrinetObject = element;
    }

    /**
     * this returns the closest
     * object solely based on origin
     * @param x
     * @param y
     * @return
     */
    private Petrinet2DObjectInterface getClosestObject(int x, int y){

        for(Petrinet2DObjectInterface object:objects){

                if (Math.abs(object.getPoint().getY() - y) <= object.getTolerance() &&
                        Math.abs(object.getPoint().getX() - x) <= object.getTolerance()

                ) {
                    return object;
                }

        }
        return null;
    }


    /**
     *  this returns an object that
     *  can be edited based on its area text
     *  location

     * @param x the mouse clicked x
     * @param y the mouse clicked y
     * @return
     */
    private Petrinet2DObjectInterface getClosestForEdit (int x, int y){

        for(Petrinet2DObjectInterface object:objects){

            if (Math.abs(object.getEditClickableLocation().getY() - y) <= object.getTolerance() &&
                    Math.abs(object.getEditClickableLocation().getX() - x) <= object.getTolerance()

            ) {
                return object;
            }

        }
        return null;
    }
    
    private void log (LogUIModel log){
        logListener.log(log);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

        if(!this.isEnabled())
            return;

        try{
            int positionX =e.getX();
            int positionY = e.getY();
            // check if there is an object closeby
            // around the tolerance of the GUI

            if (SwingUtilities.isLeftMouseButton(e)) {
                Petrinet2DObjectInterface closeBy =
                    getClosestObject(positionX,positionY);
                if (closeBy != null) {
                    logListener.log(LogUIModel.createErrorLog("Please select another location, " + closeBy.getName() + " has close proximity!!"));
                    return;
                }
                petrinetLogic.abortTreeTraversal();
                if (currentPetrinetObject == Element.TRANSITION) {

                    // at this point a transition was selected
                    // create a dialog asking for transition name
                    CustomDialog dialog = new CustomDialog(this, "Enter Transition name",
                            "Add Transition","");
                    if (dialog.isPostiveSelection()) {
                        String name = dialog.getValidatedText();
                        if (!name.isEmpty()) {
                            TransitionInterface transition = new Transition();
                            transition.setName(name);
                            // add transition instance in the back end as well
                            petrinetLogic.addTransition(transition);

                            //if (transitionInterface.attemptTransition())
                            Petrinet2DObjectInterface transitionObject =
                                    new Transition2DObject(transition);

                            transitionObject.setID(UUID.randomUUID().toString());
                            transitionObject.setName(name);

                            transitionObject.setPoint(new Point(positionX, positionY));
                            objects.add(transitionObject);
                            redoHistory.clear();

                            log(LogUIModel.createInfoLog("Added Transition: " + name));
                            refresh();
                        } else {

                            log(LogUIModel.createErrorLog("Transition name cannot be empty: :("));
                        }
                    }

                } else if (currentPetrinetObject == Element.PLACE) {
                    // at this point a transition was selected
                    // create a dialog asking for transition name
                    CustomDialog dialog = new CustomDialog(this, "Enter Place " +
                            "in format: name<tokens>", "Add place","");
                    if (dialog.isPostiveSelection()) {
                        String value = dialog.getValidatedText();
                        if (!value.isEmpty()) {
                            // parse value
                            String[] parsedContent = parsePlaceString(value);
                            if (parsedContent != null) {
                                String name = parsedContent[0];
                                int numTokens = Integer.parseInt(parsedContent[1]);
                                PlaceInterface place = new Place();
                                place.setName(name);
                                place.setNumTokens(numTokens);
                                // add in the back end as well
                                petrinetLogic.addPlace(place);

                                //if (transitionInterface.attemptTransition())
                                Petrinet2DObjectInterface place2DObject =
                                        new Place2DObject(place);

                                place2DObject.setID
                                        (UUID.randomUUID().toString());
                                place2DObject.setName(name);

                                place2DObject.setPoint
                                        (new Point(positionX, positionY));
                                objects.add(place2DObject);
                                redoHistory.clear();

                                log(LogUIModel.createInfoLog("Added Place: " + value));
                                refresh();
                            } else {
                                log(LogUIModel.createErrorLog("Unable to parse place, follow this placename<tokens>"));
                            }
                        } else {
                            logListener.log(LogUIModel.createErrorLog("Transition name cannot be empty: :("));
                        }
                    }

                } else if (currentPetrinetObject == Element.NONE) {
                    logListener.log(LogUIModel.createErrorLog("Choose a petrinet model"));
                }
            }else if (SwingUtilities.isRightMouseButton(e)){
                Petrinet2DObjectInterface closeForEdit = getClosestForEdit(positionX,positionY);
                if (closeForEdit==null)
                    return ;
                currentSelectedObject = closeForEdit;
                if (closeForEdit instanceof  Transition2DObject) {
                    Transition2DObject  transition2DObject = (Transition2DObject)closeForEdit;
                    // this checks if the transition object is fireable
                    if (transition2DObject.getTransition().checkTransition()){
                        elementOptionMenu.setFireOptionEnabled(true);
                    }
                    elementOptionMenu.setFireOptionVisible(true);
                }
                else {
                    elementOptionMenu.setFireOptionVisible(false);
                }
                elementOptionMenu.setInvoker(this);
                elementOptionMenu.setLocation(e.getX(),e.getY());
                elementOptionMenu.setVisible(true);
            }

        } catch (Exception ex){
            logListener.log(LogUIModel.createErrorLog("An unexpected error occured!! msg: "+ex.getMessage()));
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(!this.isEnabled())
            return;
        Petrinet2DObjectInterface closeBy =
                getClosestObject(x, y);

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (currentPetrinetObject == Element.ARC) {


                originObject = closeBy;


                if (originObject != null &&
                        !(originObject instanceof Arc2DObject)) {
                    logListener.log(LogUIModel.createInfoLog("drawing arc from origin: " + originObject.getName()));
                    originPoint = new Point(x, y);
                } else
                    logListener.log(LogUIModel.createErrorLog("error starting arc: No transition or place"));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try{
            if(currentPetrinetObject == Element.ARC){
                int x = e.getX();
                int y = e.getY();
                if(!this.isEnabled())
                    return;

                destinationObject = getClosestObject(x,y);
                if(destinationObject!=null && originObject != null) {
                    logListener.log(LogUIModel.createInfoLog("drawing arc to: " + destinationObject.getName()));
                    destinationPoint = new Point(x,y);
                    petrinetLogic.abortTreeTraversal();
                    if(destinationObject.getClass() != originObject.getClass()) {
                        CustomDialog dialog = new CustomDialog(this, "Enter Arc " +
                                "in format: Arc Name<Weight>", "Add Arc", "");
                        if (dialog.isPostiveSelection()) {
                            String value = dialog.getValidatedText();
                            if (!value.isEmpty()) {
                                // parse value
                                String[] parsedContent = parsePlaceString(value);
                                if (parsedContent != null) {
                                    String name = parsedContent[0];
                                    int weight = Integer.parseInt(parsedContent[1]);

                                    // TODO someone has to compromise polymorphism
                                    ArcInterface arc = null;
                                    try {
                                        if (originObject instanceof  Transition2DObject
                                                && destinationObject instanceof Place2DObject) {
                                            Transition2DObject transition2DObject = (Transition2DObject) originObject;
                                            TransitionInterface transition = transition2DObject.getTransition();

                                            Place2DObject place2DObject = (Place2DObject) destinationObject;
                                            PlaceInterface place = place2DObject.getPlace();

                                            arc = new TransitionToPlaceArc
                                                    (transition, place);
                                            arc.setWeight(weight);
                                            arc.setName(name);

                                            // now add this arc instance to the back end
                                            // for the coverability tree
                                            // TODO ask for explanation
                                            //transition.addArcOutput(arc);
                                            //place.addArcInput(arc);
                                            refresh();
                                            logListener.log(LogUIModel.createInfoLog("Arc Drawn from : " +
                                                    originObject.getName() + " to" + destinationObject.getName()));


                                        } else if (originObject instanceof  Place2DObject
                                                && destinationObject instanceof Transition2DObject) {
                                            Transition2DObject transition2DObject = (Transition2DObject) destinationObject;
                                            TransitionInterface transition = transition2DObject.getTransition();

                                            Place2DObject place2DObject = (Place2DObject) originObject;
                                            PlaceInterface place = place2DObject.getPlace();

                                            arc = new PlaceToTransitionArc
                                                    (place, transition);
                                            arc.setWeight(weight);
                                            arc.setName(name);

                                            // now add this arc instance to the back end
                                            // for the coverability tree
                                            // TODO ASK FOR EXPLANATION
                                            // transition.addArcInput(arc);
                                            //place.addArcOutput(arc);

                                        } else {
                                            logListener.log(LogUIModel.createErrorLog("The above case should not occur"));
                                        }
                                    }catch (IllegalArgumentException ex){
                                        logListener.log(LogUIModel.createErrorLog(""+ex.getMessage()));
                                    }
                                    if(arc!=null){
                                        Arc2DObject arc2DObject = new Arc2DObject(arc);
                                        arc2DObject.setName(name);
                                        arc2DObject.setDestinationPoint(destinationPoint);
                                        arc2DObject.setPoint(originPoint);
                                        arc2DObject.setDestination(destinationObject);
                                        arc2DObject.setOrigin(originObject);
                                        arc2DObject.setID(UUID.randomUUID().toString());

                                        Petrinet2DObjectInterface arcObject= arc2DObject;


                                        objects.add(arcObject);

                                        refresh();
                                        logListener.log(LogUIModel.createInfoLog("Arc Drawn from : " +
                                                originObject.getName() + " to" + destinationObject.getName()));
                                    }


                                }else{
                                    logListener.log(LogUIModel.createErrorLog("Unable to parse arc, -> Format ArcName<Weight>"));
                                }
                            }
                        }
                    }else{
                        logListener.log(LogUIModel.createErrorLog("Cannot draw arc between the same object type"));
                    }
                }
                else
                    logListener.log(LogUIModel.createErrorLog("Ending arc no transition / place"));

                originObject = null;
                destinationObject = null;
                originPoint = null;
                destinationPoint = null;

            }
        }catch (Exception ex){
            logListener.log(LogUIModel.createErrorLog("an un expected error occured: "+ex.getMessage()));
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     *
     * This function is called to
     * undo changes by one step
     */
    public void undo(){
        if (objects.size()!=0) {
            Petrinet2DObjectInterface object =
                    objects.remove(objects.size() - 1);

            //also remove the instance contained in that
            //object model from the beck end interface
            redoHistory.add(object);
            refresh();
            logListener.log(LogUIModel.createInfoLog("Undo last operation!!"));
        }

    }

    public void clearAll(){
        objects.clear();
        attributePanel.clear();
        refresh();
    }

    /**
     * This takes in a string, matches with
     * name:string<token:int>
     * @param string
     * @return
     */
    private String[] parsePlaceString(String string) {

        if(string.matches("^[a-zA-Z0-9_ ]+<\\d+>$")) {
            String[] split = string.split("<");
            split[1] =split[1].replace(">", "");
            return split;
        }else
            return null;
    }


    /**
     * This function is called to redo
     * changes by one step
     */
    public void redo (){
        if (redoHistory.size()!=0) {
            Petrinet2DObjectInterface object =
                    redoHistory.remove(redoHistory.size() - 1);
            objects.add(object);
            refresh();
            logListener.log(LogUIModel.createInfoLog("Redo last operation!!"));
        }
    }

    public ArrayList<Petrinet2DObjectInterface> getGuiObjects() {
        return new ArrayList<>(objects);
    }

    public void loadElements(ArrayList<Petrinet2DObjectInterface> guiObjects) {
        this.objects = guiObjects;

        refresh();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem button = ((JMenuItem)e.getSource());
        petrinetLogic.abortTreeTraversal();
        if(button.getName().equals("element_option_delete")){

            if (currentSelectedObject!=null) {

                objects.remove(currentSelectedObject);
                if (currentSelectedObject instanceof  Transition2DObject){
                    Transition2DObject  transition2DObject = (Transition2DObject)currentSelectedObject;
                    TransitionInterface transition = transition2DObject.getTransition();
                    // look for referencing arcs

                    petrinetLogic.removeTransition(transition);
                } else if (currentSelectedObject instanceof  Place2DObject){
                    Place2DObject place2DObject = (Place2DObject) currentSelectedObject;
                    petrinetLogic.removePlace(place2DObject.getPlace());

                }

                redoHistory.add(currentSelectedObject);
                refresh();
            }

        }else if(button.getName().equals("element_option_edit")){
            petrinetLogic.abortTreeTraversal();
            if (currentSelectedObject!=null) {
                petrinetLogic.abortTreeTraversal();
               if (currentSelectedObject instanceof Transition2DObject) {
                   Transition2DObject transition = (Transition2DObject)currentSelectedObject;
                   CustomDialog dialog = new CustomDialog(this, "Enter Transition name",
                           "Edit Transition",
                           currentSelectedObject.getName());
                   if (dialog.isPostiveSelection()) {
                       String name = dialog.getValidatedText();
                       if (!name.isEmpty()) {
                           transition.setName(name);
                           transition.getTransition().setName(name);

                       }else{
                           logListener.log(LogUIModel.createErrorLog("Transition name cannot be empty"));
                       }
                   }

               }else if (currentSelectedObject instanceof Place2DObject){
                   Place2DObject place = (Place2DObject)currentSelectedObject;
                   CustomDialog dialog = new CustomDialog(this, "Enter Place " +
                           "in format: name<tokens>", "Edit place", place.getName()
                           + " <"+ place.getPlace().getNumTokens()+">");
                   if (dialog.isPostiveSelection()) {
                       String value = dialog.getValidatedText();
                       if (!value.isEmpty()) {
                           // parse value
                           String[] parsedContent = parsePlaceString(value);
                           if (parsedContent != null) {
                               String name = parsedContent[0];
                               int numTokens = Integer.parseInt(parsedContent[1]);
                               place.getPlace().setNumTokens(numTokens);
                               place.getPlace().setName(name);
                               place.setName(name);
                           }
                       }
                   }
               }else if (currentSelectedObject instanceof Arc2DObject){
                   Arc2DObject arc = (Arc2DObject)currentSelectedObject;
                   CustomDialog dialog = new CustomDialog(this, "Enter Arc " +
                           "in format: name<tokens>", "Edit arc", arc.getName()
                           + " <"+ arc.getArc().getWeight()+">");
                   if (dialog.isPostiveSelection()) {
                       String value = dialog.getValidatedText();
                       if (!value.isEmpty()) {
                           // parse value
                           String[] parsedContent = parsePlaceString(value);
                           if (parsedContent != null) {
                               String name = parsedContent[0];
                               int arcWeight = Integer.parseInt(parsedContent[1]);
                               arc.getArc().setWeight(arcWeight);
                               arc.getArc().setName(name);
                               arc.setName(name);
                           }
                       }
                   }
               }


            }
            refresh();

        }else if (button.getName().equals("element_option_fire")){
            if (currentSelectedObject instanceof Transition2DObject) {
                petrinetLogic.abortTreeTraversal();
                Transition2DObject transition = (Transition2DObject) currentSelectedObject;
                // attempt transition fire at that point
                transition.getTransition().executeTransition();
                refresh();
            }
        }


    }
    public  void setPetriAttrib(String text){
        attributePanel.setText(text);
    }

    public void refresh() {
        canvas.repaint();
        if (petrinetLogic.getCoverabilityTreeRoot()!=null) {
            attributePanel.
                    loadTree(petrinetLogic.getCoverabilityTreeRoot());
        }
    }



    public void clearPetriAttribPanel(){

        attributePanel.clear();
    }

    class DrawCanvas extends JPanel {


        @Override
        public void paintComponent(Graphics g) {  // invoke via repaint()
            super.paintComponent(g);    // fill background
            setBackground(Color.WHITE); // set its background color

            // Draw the grid-lines
            g.setColor(Color.LIGHT_GRAY);

            int width = getSize().width;
            int height = getSize().height;

            int htOfRow = height / (rows);
            for (int k = 0; k < rows; k++)
                g.drawLine(0, k * htOfRow , width, k * htOfRow );

            int wdOfRow = width / (columns);
            for (int k = 0; k < columns; k++)
                g.drawLine(k*wdOfRow , 0, k*wdOfRow , height);

            // printgui object
            for(Petrinet2DObjectInterface objectInterface: objects)
                objectInterface.draw(g);


        }
    }
}
