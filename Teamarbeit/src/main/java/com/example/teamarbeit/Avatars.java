package com.example.teamarbeit;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import static com.example.teamarbeit.HelloApplication.WINDOW_HEIGHT;
import static com.example.teamarbeit.HelloApplication.WINDOW_WIDTH;

public class Avatars {

    private Stage window;
    private int playerSelecting = 1; // 1 for player one, 2 for player two
    private Image selectedImagePlayer1;
    private Image selectedImagePlayer2;
    private Label instructions;
    private int windowWidth;
    private int windowHeight;
    private Stage currentStage;
    private AvatarSelectionCompleteCallback callback;


    public Avatars(int windowWidth, int windowHeight, Stage currentStage, AvatarSelectionCompleteCallback callback){
        this.callback = callback;
        this.window = currentStage;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        instructions = new Label("Player 1: Select your character");

        // Avatar Selection Screen

        //image1 = avatar1
        Image image1 = new Image(getClass().getResourceAsStream("/avatar1.png"));
        ImageView view1 = new ImageView(image1);
        view1.setFitHeight(250); // Sets the height of the avatar image
        view1.setPreserveRatio(true);
        Button avatar1 = new Button("", view1);

        //image2 = avatar2
        Image image2 = new Image(getClass().getResourceAsStream("/avatar2.png"));
        ImageView view2 = new ImageView(image2);
        view2.setFitHeight(250); // Set the height of the avatar image
        view2.setPreserveRatio(true);
        Button avatar2 = new Button("", view2);

        //image3 = avatar3
        Image image3 = new Image(getClass().getResourceAsStream("/avatar3.png"));
        ImageView view3 = new ImageView(image3);
        view3.setFitHeight(250); // Set the height of the avatar image
        view3.setPreserveRatio(true);
        Button avatar3 = new Button("", view3);

        //image4 = avatar4
        Image image4 = new Image(getClass().getResourceAsStream("/avatar4.png"));
        ImageView view4 = new ImageView(image4);
        view4.setFitHeight(250); // Set the height of the avatar image
        view4.setPreserveRatio(true);
        Button avatar4 = new Button("", view4);

        avatar1.setOnAction(e -> selectCharacter(image1));
        avatar2.setOnAction(e -> selectCharacter(image2));
        avatar3.setOnAction(e -> selectCharacter(image3));
        avatar4.setOnAction(e -> selectCharacter(image4));


        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(20);
        grid.setAlignment(Pos.CENTER); // Center the content of the GridPane


        // Parameters: (Node, columnIndex, rowIndex)
        grid.add(avatar1, 0, 0); // Top-left
        grid.add(avatar2, 1, 0); // Top-right
        grid.add(avatar3, 0, 1); // Bottom-left
        grid.add(avatar4, 1, 1); // Bottom-right


        VBox selectionLayout = new VBox(20);
        selectionLayout.setAlignment(Pos.CENTER); // Center the children of VBox
        selectionLayout.getChildren().addAll(instructions, grid);
        Scene selectionScene = new Scene(selectionLayout, this.windowWidth, this.windowHeight);

        currentStage.setScene(selectionScene);

    }

    public static void main(String[] args) {

    }




    private void selectCharacter(Image selectedImage) {
        if (playerSelecting == 1) {
            selectedImagePlayer1 = selectedImage;
            instructions.setText("Player 2: Select your character");
            playerSelecting = 2;
        } else {
            selectedImagePlayer2 = selectedImage;
            if (callback != null) {
                callback.onSelectionComplete();
                // Both players have selected, start the game
            }
        }
    }

    private void startGame() {
        // Game Screen
        Label player1Label = new Label("Player 1 character selected!");
        Label player2Label = new Label("Player 2 character selected!");
        Button startGameButton = new Button("Start Game");
        startGameButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 50px; -fx-pref-width: 400px; -fx-pref-height: 80px;");
        startGameButton.setOnAction(e -> {
            if (callback != null) {
                callback.onSelectionComplete();
            }
        });

        VBox gameLayout = new VBox(10);
        gameLayout.getChildren().addAll(startGameButton, player1Label, player2Label);
        gameLayout.setAlignment(Pos.CENTER);
        Scene gameScene = new Scene(gameLayout, WINDOW_WIDTH, WINDOW_HEIGHT);

        window.setScene(gameScene);
    }
}