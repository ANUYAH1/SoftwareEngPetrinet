package gui;

/**
 * Listener to
 * listen for when a petrinet object is
 * selected
 */
public interface ElementSelectListener {
    /**
     * fired when an object
     * is selected
     * @param element
     */
    void selectedPetrinetObject(Element element);
}
