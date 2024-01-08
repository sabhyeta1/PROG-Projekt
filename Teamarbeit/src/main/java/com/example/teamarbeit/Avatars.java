package com.example.teamarbeit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Avatars extends Application {

    private Stage window;
    private int playerSelecting = 1; // 1 for player one, 2 for player two
    private Image selectedImagePlayer1;
    private Image selectedImagePlayer2;
    private Label instructions;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("My Game");

        // Start Screen
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> showAvatarSelectionScreen());

        VBox startLayout = new VBox(10);
        startLayout.getChildren().addAll(startButton);
        Scene startScene = new Scene(startLayout, 300, 250);

        window.setScene(startScene);
        window.show();
    }

    private void showAvatarSelectionScreen() {
        // Instructions label
        instructions = new Label("Player 1: Select your character");

        // Avatar Selection Screen

        //image1 = avatar1
        Image image1 = new Image(getClass().getResourceAsStream("/avatar1.png"));
        ImageView view1 = new ImageView(image1);
        view1.setFitHeight(100); // Sets the height of the avatar image
        view1.setPreserveRatio(true);
        Button avatar1 = new Button("", view1);

        //image2 = avatar2
        Image image2 = new Image(getClass().getResourceAsStream("/avatar2.png"));
        ImageView view2 = new ImageView(image2);
        view2.setFitHeight(100); // Set the height of the avatar image
        view2.setPreserveRatio(true);
        Button avatar2 = new Button("", view2);

        //image3 = avatar3
        Image image3 = new Image(getClass().getResourceAsStream("/avatar3.png"));
        ImageView view3 = new ImageView(image3);
        view3.setFitHeight(100); // Set the height of the avatar image
        view3.setPreserveRatio(true);
        Button avatar3 = new Button("", view3);

        //image4 = avatar4
        Image image4 = new Image(getClass().getResourceAsStream("/avatar4.png"));
        ImageView view4 = new ImageView(image4);
        view4.setFitHeight(100); // Set the height of the avatar image
        view4.setPreserveRatio(true);
        Button avatar4 = new Button("", view4);

        avatar1.setOnAction(e -> selectCharacter(image1));
        avatar2.setOnAction(e -> selectCharacter(image2));
        avatar3.setOnAction(e -> selectCharacter(image3));
        avatar4.setOnAction(e -> selectCharacter(image4));


        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        // Parameters: (Node, columnIndex, rowIndex)
        grid.add(avatar1, 0, 0); // Top-left
        grid.add(avatar2, 1, 0); // Top-right
        grid.add(avatar3, 0, 1); // Bottom-left
        grid.add(avatar4, 1, 1); // Bottom-right


        VBox selectionLayout = new VBox(10);
        selectionLayout.getChildren().addAll(instructions, grid);
        Scene selectionScene = new Scene(selectionLayout, 300, 400);

        window.setScene(selectionScene);
    }


    private void selectCharacter(Image selectedImage) {
        if (playerSelecting == 1) {
            selectedImagePlayer1 = selectedImage;
            instructions.setText("Player 2: Select your character");
            playerSelecting = 2;
        } else {
            selectedImagePlayer2 = selectedImage;
            startGame(); // Both players have selected, start the game
        }
    }

    private void startGame() {
        // Game Screen
        Label player1Label = new Label("Player 1 character selected!");
        Label player2Label = new Label("Player 2 character selected!");
        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> {
            //  the game start logic belongs here
        });

        VBox gameLayout = new VBox(10);
        gameLayout.getChildren().addAll(startGameButton);
        Scene gameScene = new Scene(gameLayout, 300, 250);

        window.setScene(gameScene);
    }
}