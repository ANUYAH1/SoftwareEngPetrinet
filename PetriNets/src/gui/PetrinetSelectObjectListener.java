package gui;

/**
 * Listener to
 * listen for when a petrinet object is
 * selected
 */
public interface PetrinetSelectObjectListener {
    /**
     * fired when an object
     * is selected
     * @param selectObject
     */
    void selectedPetrinetObject(SelectObject selectObject);
}
