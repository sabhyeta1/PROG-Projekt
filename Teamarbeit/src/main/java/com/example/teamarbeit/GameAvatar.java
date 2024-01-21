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


        if (temporaryRoot == GameLogic.root) {
            GameLogic.root.getChildren().add(selectedPlayerView);
        }
        if (temporaryRoot == GameLogic.secondRoot) {
            GameLogic.secondRoot.getChildren().add(selectedPlayerView);
        }
    }



    /**
     * The following 7 lines (row 66 - 72) of Code have been inspired by the Internet: https://stackoverflow.com/questions/56203717/java-fx-translatetransition-duration-change
     */


    // Function for moving the avatars
    public void avatarMovement () {
        TranslateTransition floating = new TranslateTransition(Duration.seconds(1), selectedPlayerView);
        floating.setByY(10); //set th movement 10px up and down
        floating.setCycleCount(TranslateTransition.INDEFINITE); //the movement repeats in an infinite loop
        floating.setAutoReverse(true); //movement is automatically reversed
        floating.play(); // starts the movement
    }
}