package com.example.teamarbeit;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static com.example.teamarbeit.Avatars.selectedImagePlayer1;
import static com.example.teamarbeit.Avatars.selectedImagePlayer2;


public class gameAvatar extends Node {
    Image playerImage;
    double topAnchor;
    double sideAnchor;
    double ImageHeight;


    public gameAvatar(Image playerImage, double topAnchor, double sideAnchor, double ImageHeight, int playerNumber) {
        this.playerImage = playerImage;
        this.topAnchor = topAnchor;
        this.sideAnchor = sideAnchor;
        this.ImageHeight = ImageHeight;


        ImageView selectedPlayerView = new ImageView(playerImage);

        selectedPlayerView.setFitWidth(ImageHeight); // Breite des Bildes setzen
        selectedPlayerView.setPreserveRatio(true); // Seitenverhältnis beibehalten
        AnchorPane.setTopAnchor(selectedPlayerView, topAnchor); // Abstand von oben setzen
        if (playerNumber == 1) {AnchorPane.setLeftAnchor(selectedPlayerView, sideAnchor);} // Abstand von links setzen
        if (playerNumber == 2) {AnchorPane.setRightAnchor(selectedPlayerView, sideAnchor);} // Abstand von rechts setzen
        HelloApplication.root.getChildren().addAll(selectedPlayerView);
    }
}
