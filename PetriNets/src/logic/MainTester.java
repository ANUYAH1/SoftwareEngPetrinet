package logic;

public class MainTester {
    public static void main(String[] args) {
        PetriNetInterface net = new PetriNet();
        PlaceInterface[] places = new PlaceInterface[3];
        for(int i = 0; i < places.length; i++) {
            places[i] = new Place();
            places[i].setName("" + i);
            places[i].setNumTokens(2-i);
            net.addPlace(places[i]);
        }
        places[0].setNumTokens(1);
        TransitionInterface[] transitions = new TransitionInterface[4];
        for(int i = 0; i < transitions.length; i++){
            transitions[i] = new Transition();
            net.addTransition(transitions[i]);
        }
        ArcInterface[] arcs = new ArcInterface[9];
        arcs[0] = new PlaceToTransitionArc(places[0], transitions[0]);
        arcs[0].setWeight(2);
        arcs[1] = new TransitionToPlaceArc(transitions[0], places[1]);
        arcs[1].setWeight(2);

        arcs[2] = new PlaceToTransitionArc(places[1], transitions[1]);
        arcs[2].setWeight(2);
        arcs[3] = new TransitionToPlaceArc(transitions[1], places[2]);
        arcs[3].setWeight(1);

        arcs[4] = new PlaceToTransitionArc(places[2], transitions[2]);
        arcs[4].setWeight(2);

        arcs[5] = new TransitionToPlaceArc(transitions[2], places[0]);
        arcs[5].setWeight(2);

        arcs[6] = new TransitionToPlaceArc(transitions[2], places[1]);
        arcs[6].setWeight(2);

        arcs[7] = new PlaceToTransitionArc(places[0], transitions[3]);
        arcs[7].setWeight(1);

        arcs[8] = new TransitionToPlaceArc(transitions[3], places[0]);
        arcs[8].setWeight(1);


        net.startTreeTraversal();
        while(net.next()) {
            System.out.println(net.getCoverabilityTreeRoot());
        }
        System.out.println(net.notLiveList().size());
    }
}
