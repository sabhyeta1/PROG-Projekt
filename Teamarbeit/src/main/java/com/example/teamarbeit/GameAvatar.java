package com.example.teamarbeit;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import static com.example.teamarbeit.Avatars.*;




public class GameAvatar {

    // Instances of the class
    Image playerImage;
    double topAnchor;
    double sideAnchor;
    double ImageHeight;
    ImageView selectedPlayerView;
    int timeSleep;



    // Constructor of the class "GameAvatar"
    public GameAvatar(Image playerImage, double topAnchor, double sideAnchor, double ImageHeight, AnchorPane temporaryRoot) {

        // Initializing instances
        this.playerImage = playerImage;
        this.topAnchor = topAnchor;
        this.sideAnchor = sideAnchor;
        this.ImageHeight = ImageHeight;

        selectedPlayerView = new ImageView(playerImage);
        selectedPlayerView.setFitWidth(ImageHeight); //set the width of the image
        selectedPlayerView.setPreserveRatio(true); //keeping the side ratio of the image

        AnchorPane.setTopAnchor(selectedPlayerView, topAnchor); //sets the anchor at the top
        if (playerImage == selectedImagePlayer1) {
            AnchorPane.setLeftAnchor(selectedPlayerView, sideAnchor); //sets the anchor on the left
        }
        if (playerImage == selectedImagePlayer2) {
            AnchorPane.setRightAnchor(selectedPlayerView, sideAnchor); //sets the anchor of the right
        }

        avatarMovement();


        if (temporaryRoot == GameLogik.root) {
            GameLogik.root.getChildren().add(selectedPlayerView);
        }
        if (temporaryRoot == GameLogik.secondRoot) {
            GameLogik.secondRoot.getChildren().add(selectedPlayerView);
        }
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