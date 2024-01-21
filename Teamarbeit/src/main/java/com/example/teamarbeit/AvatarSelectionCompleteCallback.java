package com.example.teamarbeit;

/**
 * The following 4 lines of Code have been inspired by the Internet| https://stackoverflow.com/questions/19405421/what-is-a-callback-method-in-java-term-seems-to-be-used-loosely,last visit: 20.01.2024
 */

import javafx.scene.control.Button;
// imports Button class

public interface AvatarSelectionCompleteCallback {
    void onSelectionComplete(Button backButton);
    //onSelectionComplete = method that does not return any value --> void
    //called after avatar has been selected
    //backButton as parameter; able to switch back to main Menu after the avatar selection is complete.
}
