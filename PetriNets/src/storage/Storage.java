package storage;

import gui.Arc2DObject;
import gui.Petrinet2DObjectInterface;
import gui.Place2DObject;
import gui.Transition2DObject;
import logic.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage implements StorageInterface {
    private final String EXTENSION =".xml";
    private final String PETRINETNODE = "petrinet";
    private final String FILEDESCRIPTIONNAME = "Petrinet Project";
    private final String TRANSITIONNODE = "transition";
    private final String ARCNODE = "arc";
    private final String PLACENODE = "place";
    public Storage(){

    }



    /**
     * this loads the text from the
     * resource files
     * @return credits
     */
    public String getAbout() throws FileNotFoundException {
        Scanner scanner = new Scanner( new
                File(getClass().getResource("/text/about.txt").getPath()));
        String about ="";
        while(scanner.hasNextLine()){
            about+=scanner.nextLine() + "\n";
        }
        scanner.close();
        return about;


    }

    @Override
    public void saveProject(ProjectInterface project) throws Exception {
        //Save to xml file here

        try{
            String path = project.getFilePath();
            if(!path.endsWith(EXTENSION)){
                project.setFilePath(path +EXTENSION);
            }
            File file = new File(project.getFilePath());

            if (!file.exists()) {
                file.createNewFile();
            }
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();

           Element petrinetNode = doc.createElement(PETRINETNODE);
           Element name  =doc.createElement("name");
           name.setTextContent(project.getName());
           petrinetNode.appendChild(name);

            Element transitions  =doc.createElement("transitions");
            Element places  =doc.createElement("places");
            Element arcs=doc.createElement("arcs");
            
            
            // go through the gui objects
            
            ArrayList<Petrinet2DObjectInterface> objects =project.getGuiObjects();
            
            for(Petrinet2DObjectInterface o :objects){
                if (o instanceof  Transition2DObject){
                    Element transition = doc.createElement("transition");

                    Element transition_name  =doc.createElement("name");
                    transition_name.setTextContent(o.getName());
                    Element transition_Id  =doc.createElement("id");
                    transition_Id.setTextContent(o.getID());

                    Element transition_x  =doc.createElement("x");
                    transition_x.setTextContent(String.valueOf((int)o.getPoint().getX()));
                    Element transition_y  =doc.createElement("y");
                    transition_y.setTextContent(String.valueOf((int)o.getPoint().getY()));
                    transition.appendChild(transition_name);
                    transition.appendChild(transition_Id);
                    transition.appendChild(transition_x);
                    transition.appendChild(transition_y);
                    transitions.appendChild(transition);
                }else if (o instanceof Place2DObject){
                    Place2DObject placeObject = (Place2DObject) o; 
                    Element place = doc.createElement("place");

                    Element place_name  =doc.createElement("name");
                    place_name.setTextContent(placeObject.getName());
                    Element place_Id  =doc.createElement("id");
                    place_Id.setTextContent(placeObject.getID());

                    Element place_x  =doc.createElement("x");
                    place_x.setTextContent(String.valueOf((int)placeObject.getPoint().getX()));

                    Element place_token  =doc.createElement("tokens");
                    place_token.setTextContent(String.valueOf(placeObject.getPlace().getNumTokens()));
                    Element place_y  =doc.createElement("y");
                    place_y.setTextContent(String.valueOf((int)o.getPoint().getY()));
                    place.appendChild(place_name);
                    place.appendChild(place_Id);
                    place.appendChild(place_x);
                    place.appendChild(place_y);
                    place.appendChild(place_token);

                    places.appendChild(place);
                }else if (o instanceof Arc2DObject){
                    Arc2DObject arcObject = (Arc2DObject) o;
                    Element arc = doc.createElement("arc");

                    Element arc_name  =doc.createElement("name");
                    arc_name.setTextContent(arcObject.getName());
                    Element arc_Id  =doc.createElement("id");
                    arc_Id.setTextContent(arcObject.getID());

                    Element arc_origin_x  =doc.createElement("origin_x");
                    arc_origin_x.setTextContent(String.valueOf((int)arcObject.getPoint().getX()));


                    Element arc_origin_y  =doc.createElement("origin_y");
                    arc_origin_y.setTextContent(String.valueOf((int)arcObject.getPoint().getY()));


                    Element arc_destination_x  =doc.createElement("destination_x");
                    arc_destination_x.setTextContent(String.valueOf((int)arcObject.getDestinationPoint().getX()));


                    Element arc_destination_y  =doc.createElement("destination_y");
                    arc_destination_y.setTextContent(String.valueOf((int)arcObject.getDestinationPoint().getY()));

                    Element arc_weight  =doc.createElement("weight");
                    arc_weight.setTextContent(String.valueOf(arcObject.getArc().
                                    getWeight()));



                    Element origin_Id  =doc.createElement("origin_id");
                    origin_Id.setTextContent(arcObject.getOrigin().getID());
                    Element destination_Id  =doc.createElement("destination_id");
                    destination_Id.setTextContent(arcObject.getDestination().getID());
                    arc.appendChild(arc_name);
                    arc.appendChild(arc_Id);
                    arc.appendChild(origin_Id);
                    arc.appendChild(destination_Id);
                    arc.appendChild(arc_destination_x);
                    arc.appendChild(arc_destination_y);

                    arc.appendChild(arc_origin_x);
                    arc.appendChild(arc_origin_y);

                    arc.appendChild(arc_weight);


                    arcs.appendChild(arc);
                }
            }

            petrinetNode.appendChild(transitions);
            petrinetNode.appendChild(places);
            petrinetNode.appendChild(arcs);
            doc.appendChild(petrinetNode);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);


        }catch (Exception ex){
            ex.printStackTrace();
            throw new Exception();
        }

    }

    @Override
    public ProjectInterface loadProject(String path) throws Exception {
        // read from XML file here
        if (path == null){
            throw new IllegalArgumentException("Requires path parameter to be non null");
        }

        File petrinetFile = new File(path);
        if(!petrinetFile.exists()){
            throw new FileNotFoundException("File not found");
        }
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(petrinetFile);
            doc.getDocumentElement().normalize();
            Node  petrinetNode = doc.
                    getElementsByTagName(PETRINETNODE).item(0);
            String name = "";
            ArrayList<Petrinet2DObjectInterface> guiObjects = new ArrayList<>();

            Element  petrinetRootElement = (Element) petrinetNode;
            name =getTagValue("name",petrinetRootElement);
            // read transitions from data base

              NodeList transitionList = 
                    petrinetRootElement.getElementsByTagName("transitions").item(0).getChildNodes();
            
            for(int i = 0; i <transitionList.getLength();i++){
                Node node  = transitionList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element transitionItem = (Element) node;

                    String transitionId =getTagValue("id",transitionItem);
                    String transitionName = getTagValue("name",transitionItem);
                    int xCoordinate = Integer.parseInt(getTagValue("x",transitionItem));

                    int yCoordinate = Integer.parseInt(getTagValue("y",transitionItem));
                    TransitionInterface transition = new Transition();
                    transition.setName(transitionName);

                    Petrinet2DObjectInterface transtionGui = new Transition2DObject(transition);
                    transtionGui.setID(transitionId);
                    transtionGui.setName(transitionName);
                    transtionGui.setPoint(new Point(xCoordinate, yCoordinate));
                    guiObjects.add(transtionGui);

                }
            }
            
            // read places from XML file

            NodeList  placeList =
                    petrinetRootElement.getElementsByTagName("places").item(0).getChildNodes();
          
            for(int i = 0; i <placeList.getLength();i++){
                Node node  = placeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element placeItem = (Element) node;
                    String placeId =getTagValue("id",placeItem);
                    String placeName = getTagValue("name",placeItem);
                    int xCoordinate = Integer.parseInt(getTagValue("x",placeItem));

                    int yCoordinate = Integer.parseInt(getTagValue("y",placeItem));
                    int tokens = Integer.parseInt(getTagValue("tokens",placeItem));
                    PlaceInterface place = new Place();
                    place.setName(placeName);
                    place.setNumTokens(tokens);


                    Petrinet2DObjectInterface placeGui = new Place2DObject(place);
                    placeGui.setID(placeId);
                    placeGui.setName(placeName);
                    placeGui.setPoint(new Point(xCoordinate, yCoordinate));
                    guiObjects.add(placeGui);
                }


            }



            // read arcs from XML file

            
            NodeList arcList =
                    petrinetRootElement.getElementsByTagName("arcs").
                            item(0).getChildNodes();

            for(int i = 0; i <arcList.getLength();i++){

                Node node  = arcList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element arcItem = (Element) node;



                    String arcId =getTagValue("id",arcItem);
                    String arcName = getTagValue("name",arcItem);
                    int originXCoordinate = Integer.parseInt(getTagValue("origin_x",arcItem));

                    int originYCoordinate = Integer.parseInt(getTagValue("origin_y",arcItem));

                    int destinationXCoordinate = Integer.parseInt(getTagValue("destination_x",arcItem));

                    int destinationYCoordinate = Integer.parseInt(getTagValue("destination_y",arcItem));


                    int weight = Integer.parseInt(getTagValue("weight",arcItem));
                   




                    String originId = getTagValue("origin_id",arcItem);

                    String destinationId = getTagValue("destination_id",arcItem);

                    Point destinationPoint = new Point(destinationXCoordinate, destinationYCoordinate);
                    Point originPoint = new Point(originXCoordinate, originYCoordinate);
                    // search the loaded place and transitions
                    // for the following Ids
                    Petrinet2DObjectInterface destination = null;
                    Petrinet2DObjectInterface origin = null;
                    for (Petrinet2DObjectInterface obj : guiObjects) {
                        if (obj.getID().equals(destinationId)) {
                            destination = obj;
                        }
                        if (obj.getID().equals(originId)) {
                            origin = obj;
                        }
                        if (origin != null && destination != null) {
                            break;
                        }

                    }

                    ArcInterface arc = null;

                    if (origin instanceof Transition2DObject
                            && destination instanceof Place2DObject) {
                        Transition2DObject transition2DObject = (Transition2DObject) origin;
                        TransitionInterface transition = transition2DObject.getTransition();

                        Place2DObject place2DObject = (Place2DObject) destination;
                        PlaceInterface place = place2DObject.getPlace();

                        arc = new TransitionToPlaceArc
                                (transition, place);
                        arc.setWeight(weight);
                        arc.setName(arcName);

                        // now add this arc instance to the back end
                        // for the coverability tree
                        // TODO ask for explanation
                       // transition.addArcOutput(arc);
                        //place.addArcInput(arc);


                    } else if (origin instanceof Place2DObject
                            && destination instanceof Transition2DObject) {
                        Transition2DObject transition2DObject = (Transition2DObject) destination;
                        TransitionInterface transition = transition2DObject.
                                getTransition();

                        Place2DObject place2DObject = (Place2DObject) origin;
                        PlaceInterface place = place2DObject.getPlace();

                        arc = new PlaceToTransitionArc
                                (place, transition);
                        arc.setWeight
                                (weight);
                        arc.setName(name);


                        //transition.addArcInput(arc);
                        //place.addArcOutput(arc);

                    }

                    if (arc != null) {
                        Arc2DObject arc2DObject = new Arc2DObject(arc);
                        arc2DObject.setName(arcName);
                        arc2DObject.setDestinationPoint(destinationPoint);
                        arc2DObject.setPoint(originPoint);
                        arc2DObject.setDestination(destination);
                        arc2DObject.setOrigin(origin);
                        arc2DObject.setID(arcId);


                        Petrinet2DObjectInterface arcObject = arc2DObject;


                        guiObjects.add(arcObject);
                    }
                }


            }
            ProjectInterface project = new ProjectModel();
            project.setGuiObjects(guiObjects);
            project.setFilePath(path);
            project.setName(name);
            return project;



        }catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception();
        }


    }
    
    
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }

    @Override
    public String getSupportedFileDescription() {
        return FILEDESCRIPTIONNAME + " ("+EXTENSION+")";

    }
}
