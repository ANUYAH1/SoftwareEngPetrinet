package gui;

/**
 * This interacts with the menu bar
 * and listens for when a menu item / instruction
 * is requested
 */
public interface MenuBarListener {
    /**
     * this is called
     * when an open command
     * is selected
     */
    void openFileMenuCommand();

    /**
     * called to listen for
     * save file command
     */
    void saveFileMenuCommand();

    /**
     * Called to listen for exit command
     *
     */
    void exitMenuCommand();

    /**
     * Called to listen for
     * undo command from the menu
     *
     */
    void undoMenuCommand();

    /**
     * listener for redo
     * menu command
     */
    void redoMenuCommand();

    /**
     * Listener for about menu
     */
    void aboutMenuCommand();

}
