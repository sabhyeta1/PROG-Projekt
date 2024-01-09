package com.example.teamarbeit;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.example.teamarbeit.HelloApplication.*;

public class Avatars {

    private Stage window;
    private int playerSelecting = 1; // 1 for player one, 2 for player two
    public static Image selectedImagePlayer1;
    public static Image selectedImagePlayer2;
    private Label instructions;
    private int windowWidth;
    private int windowHeight;
    private Stage currentStage;
    private boolean bothPlayersSelected = false;
    private AvatarSelectionCompleteCallback callback;
    Image screenImage1 = new Image(getClass().getResourceAsStream("/avatar1-removebg-preview.png"));
    Image screenImage2 = new Image (getClass().getResourceAsStream("/avatar2-removebg-preview.png"));
    Image screenImage3 = new Image (getClass().getResourceAsStream("/avatar3-removebg-preview.png"));
    Image screenImage4 = new Image (getClass().getResourceAsStream("/avatar4-removebg-preview.png"));



    //contructor that sets up avatar selection screen, dimensions and stage where avatars will be displayed
    //callback = eine instanz, wird verwendet um bekannt zu geben, wenn avatar selection gemacht wurde
    public Avatars(int windowWidth, int windowHeight, Stage currentStage, AvatarSelectionCompleteCallback callback){
        this.callback = callback;
        this.window = currentStage;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        instructions = new Label("Player 1: Select your character");
        instructions.setTextFill(Color.WHITE);
        instructions.setFont(new Font(40));
        // Avatar Selection Screen - images layout

        //image1 = avatar1
        Image image1 = new Image(getClass().getResourceAsStream("/avatar1.png"));
        ImageView view1 = new ImageView(image1);
        view1.setFitHeight(230); // Sets the height of the avatar image
        view1.setPreserveRatio(true);
        Button avatar1 = new Button("", view1); //erlaubt player auf avatar zu klicken, falls er selektieren will

        //image2 = avatar2
        Image image2 = new Image(getClass().getResourceAsStream("/avatar2.png"));
        ImageView view2 = new ImageView(image2);
        view2.setFitHeight(230); // Set the height of the avatar image
        view2.setPreserveRatio(true);
        Button avatar2 = new Button("", view2);

        //image3 = avatar3
        Image image3 = new Image(getClass().getResourceAsStream("/avatar3.png"));
        ImageView view3 = new ImageView(image3);
        view3.setFitHeight(230); // Set the height of the avatar image
        view3.setPreserveRatio(true);
        Button avatar3 = new Button("", view3);

        //image4 = avatar4
        Image image4 = new Image(getClass().getResourceAsStream("/avatar4.png"));
        ImageView view4 = new ImageView(image4);
        view4.setFitHeight(230); // Set the height of the avatar image
        view4.setPreserveRatio(true);
        Button avatar4 = new Button("", view4);

        avatar1.setOnAction(e -> selectCharacter(screenImage1));
        avatar2.setOnAction(e -> selectCharacter(screenImage2));
        avatar3.setOnAction(e -> selectCharacter(screenImage3));
        avatar4.setOnAction(e -> selectCharacter(screenImage4));

        Button backButton = new Button("Back");
        backButton.setPrefWidth(100);
        backButton.setPrefHeight(30);

        backButton.setOnAction(event -> {
            GameMenu.mediaPlayer1.stop();
            switchToGameMenu();
        });


        GridPane grid = new GridPane(); // arranges the avatars into a grid view
        grid.setVgap(20); //vertical size
        grid.setHgap(20); //horizontal size
        grid.setAlignment(Pos.CENTER); // Center the content of the GridPane


        // Parameters: (Node, columnIndex, rowIndex)
        grid.add(avatar1, 0, 0); // Top-left
        grid.add(avatar2, 1, 0); // Top-right
        grid.add(avatar3, 0, 1); // Bottom-left
        grid.add(avatar4, 1, 1); // Bottom-right
        grid.add(backButton,0,2);



        VBox selectionLayout = new VBox(10); //stacks instructions and grid of avatars vertically
        selectionLayout.setAlignment(Pos.CENTER); // Center the children of VBox
        selectionLayout.getChildren().addAll(instructions, grid, backButton);
         //displays avatars screen

        String backgroundImage = "-fx-background-image: url('/stage1.png');";
        String backgroundSize = "-fx-background-size: 1000px 666px;";
        String backgroundOpacity = "-fx-background-opacity: 0.3;";
        selectionLayout.setStyle(backgroundImage + backgroundSize + backgroundOpacity);


        Scene selectionScene = new Scene(selectionLayout, this.windowWidth, this.windowHeight);

        currentStage.setScene(selectionScene);

    }

    public static void main(String[] args) {

    }




    private void selectCharacter(Image selectedImage) { //player selection
        if (playerSelecting == 1) {
            selectedImagePlayer1 = selectedImage;
            instructions.setText("Player 2: Select your character");
            playerSelecting = 2;
        } else {
            selectedImagePlayer2 = selectedImage;
            if (!bothPlayersSelected) {
                bothPlayersSelected = true;
            if (callback != null) {
                callback.onSelectionComplete(); //callback saying both player have been selected
                // Both players have selected, start the game
                }
            }
        }
    }

    public void switchToGameMenu() {
        // Instantiate GameMenu class and switch back to its scene
        GameMenu gameMenu = new GameMenu();
        gameMenu.start(window);
    }

    private void startGame() { //placeholder
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