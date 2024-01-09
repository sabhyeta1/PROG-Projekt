package com.example.teamarbeit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import static com.example.teamarbeit.Avatars.selectedImagePlayer1;
import static com.example.teamarbeit.Avatars.selectedImagePlayer2;


public class gameAvatar {
    Image playerImage;
    double topAnchor;
    double leftAnchor;

    double ImageHeight;
    AnchorPane root;


    public gameAvatar(Image playerImage, double topAnchor, double leftAnchor, double ImageHeight, AnchorPane root) {
        this.playerImage = playerImage;
        this.topAnchor = topAnchor;
        this.leftAnchor = leftAnchor;
        this.ImageHeight = ImageHeight;
        this.root = root;


        ImageView selectedPlayerView = new ImageView(selectedImagePlayer1);
        ImageView selectedPlayer2View = new ImageView(selectedImagePlayer2);

        selectedPlayerView.setFitWidth(ImageHeight); // Breite des Bildes setzen
        selectedPlayerView.setPreserveRatio(true); // Seitenverhältnis beibehalten
        root.setTopAnchor(selectedPlayerView, topAnchor); // Abstand von oben setzen
        root.setLeftAnchor(selectedPlayerView, leftAnchor); // Abstand von links setzen
        root.getChildren().add(selectedPlayerView);

    }
}
