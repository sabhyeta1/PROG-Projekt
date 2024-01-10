package com.example.teamarbeit;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;




public class GameAvatar {

    // Instances of the class
    Image playerImage;
    double topAnchor;
    double sideAnchor;
    double ImageHeight;
    ImageView selectedPlayerView;



    // Constructor of the class "GameAvatar"
    public GameAvatar(Image playerImage, double topAnchor, double sideAnchor, double ImageHeight, int playerNumber, AnchorPane root) {

        // Initializing instances
        this.playerImage = playerImage;
        this.topAnchor = topAnchor;
        this.sideAnchor = sideAnchor;
        this.ImageHeight = ImageHeight;


        selectedPlayerView = new ImageView(playerImage);
        selectedPlayerView.setFitWidth(ImageHeight); //set the width of the image
        selectedPlayerView.setPreserveRatio(true); //keeping the side ratio of the image

        AnchorPane.setTopAnchor(selectedPlayerView, topAnchor); //sets the anchor at the top
        if (playerNumber == 1) {
            AnchorPane.setLeftAnchor(selectedPlayerView, sideAnchor); //sets the anchor on the left
        }
        if (playerNumber == 2) {
            AnchorPane.setRightAnchor(selectedPlayerView, sideAnchor); //sets the anchor of the right
        }

        avatarMovement();


        root.getChildren().addAll(selectedPlayerView);
        HelloApplication.root = root;
    }



    // Function for moving the avatars
    public void avatarMovement () {
        TranslateTransition avatarTransition = new TranslateTransition(Duration.seconds(1), selectedPlayerView);
        avatarTransition.setByY(10); //avatar moves 10 px up and down
        avatarTransition.setCycleCount(TranslateTransition.INDEFINITE); //movement repeats indefinitely
        avatarTransition.setAutoReverse(true); //automatically reversing the movement
        avatarTransition.play(); // start the animation
    }
}