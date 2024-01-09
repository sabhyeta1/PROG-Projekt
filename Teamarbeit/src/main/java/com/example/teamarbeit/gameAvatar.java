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


import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class gameAvatar {
    Image playerImage;
    double topAnchor;
    double sideAnchor;
    double ImageHeight;
    ImageView selectedPlayerView;

    public gameAvatar(Image playerImage, double topAnchor, double sideAnchor, double ImageHeight, int playerNumber, AnchorPane root) {
        this.playerImage = playerImage;
        this.topAnchor = topAnchor;
        this.sideAnchor = sideAnchor;
        this.ImageHeight = ImageHeight;

        selectedPlayerView = new ImageView(playerImage);

        selectedPlayerView.setFitWidth(ImageHeight); // Breite des Bildes setzen
        selectedPlayerView.setPreserveRatio(true); // Seitenverhältnis beibehalten
        AnchorPane.setTopAnchor(selectedPlayerView, topAnchor); // Abstand von oben setzen
        if (playerNumber == 1) {
            AnchorPane.setLeftAnchor(selectedPlayerView, sideAnchor); // Abstand von links setzen
        }
        if (playerNumber == 2) {
            AnchorPane.setRightAnchor(selectedPlayerView, sideAnchor); // Abstand von rechts setzen
        }
        root.getChildren().addAll(selectedPlayerView);
        HelloApplication.root = root;

// Auf- und Abbewegung für den Avatar
        TranslateTransition avatarTransition = new TranslateTransition(Duration.seconds(1), selectedPlayerView);
        avatarTransition.setByY(10); // Bewegung um 10 Pixel nach oben und unten
        avatarTransition.setCycleCount(TranslateTransition.INDEFINITE); // Unendliche Wiederholung
        avatarTransition.setAutoReverse(true); // Automatische Umkehrung der Bewegung
        avatarTransition.play(); // Animation starten
        HelloApplication.root = root;
    }
}
