package com.github.deno207.inventory.caretaker.view.adaptor;

/**
 * Defines a controller as being updatable. This allows for controllers to be alerted when they need to update
 * themselves, which is mostly likely to happen when a different controller makes changes to the data that they are
 * displaying.
 *
 * @author Damion Wilson
 * @version 1.0
 */
public interface UpdateController {

    /**
     * Update this controller
     */
    public void update();
}
