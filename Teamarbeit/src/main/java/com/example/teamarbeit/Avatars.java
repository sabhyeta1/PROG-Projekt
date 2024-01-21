package com.example.teamarbeit;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//static import of the class GameLogik, allows direct use of static members from the GameLogik class




public class Avatars { //selection of avatars function in this class

    // The instances of the class
    private final Label instructions;
    //label = displays the instructions (Player XYZ select your character) on the screen

    private final Stage window; //settings for the stage

    private int playerSelecting = 1;
    //selection for which avatar the player will choose
    //"1" for player 1, "2" for player 2
    private boolean bothPlayersSelected = false;

    public static Image selectedImagePlayer1;
    public static int player1_ID;
    public static Image selectedImagePlayer2;
    public static int player2_ID;
    Image gameImage1;
    Image gameImage2;
    Image gameImage3;
    Image gameImage4;
    Button backButton;

    AvatarSelectionCompleteCallback callback;



    // Constructor that sets up avatar selection screen dimensions and stage where avatars will be displayed
    // Callback = an instance that is used to declare when the avatar selection is over
    public Avatars(int windowWidth, int windowHeight, Stage currentStage, AvatarSelectionCompleteCallback callback){

        // Initializing instances
        this.callback = callback;
        this.window = currentStage;


        instructions = new Label("Player 1: Select your character");
        instructions.setTextFill(Color.WHITE);
        instructions.setFont(new Font(40));

        gameImage1 = GameMenu.loadImage("game_avatar1.png");
        gameImage2 = GameMenu.loadImage("game_avatar2.png");
        gameImage3 = GameMenu.loadImage("game_avatar3.png");
        gameImage4 = GameMenu.loadImage("game_avatar4.png");


        // Avatar selection screen - images layout
        // "image1" = "avatar1"
        Image image1 = GameMenu.loadImage("avatar1.png");
        ImageView view1 = new ImageView(image1);
        view1.setFitHeight(230); //sets the height of the avatar image
        view1.setPreserveRatio(true);
        Button avatar1 = new Button("", view1); //creates buttons of the pictures so the avatar can be selected

        // "image2" = "avatar2"
        Image image2 = GameMenu.loadImage("avatar2.png");
        ImageView view2 = new ImageView(image2);
        view2.setFitHeight(230); //sets the height of the avatar image
        view2.setPreserveRatio(true);
        Button avatar2 = new Button("", view2);

        // "image3" = "avatar3"
        Image image3 = GameMenu.loadImage("avatar3.png");
        ImageView view3 = new ImageView(image3);
        view3.setFitHeight(230); //sets the height of the avatar image
        view3.setPreserveRatio(true);
        Button avatar3 = new Button("", view3);

        // "image4" = "avatar4"
        Image image4 = GameMenu.loadImage("avatar4.png");
        ImageView view4 = new ImageView(image4);
        view4.setFitHeight(230); //sets the height of the avatar image
        view4.setPreserveRatio(true);
        Button avatar4 = new Button("", view4);


        avatar1.setOnAction(e -> selectCharacter(gameImage1,1));
        addGlowEffectOnButtonHover(avatar1, 1);

        avatar2.setOnAction(e -> selectCharacter(gameImage2,2));
        addGlowEffectOnButtonHover(avatar2, 2);

        avatar3.setOnAction(e -> selectCharacter(gameImage3,3));
        addGlowEffectOnButtonHover(avatar3, 3);

        avatar4.setOnAction(e -> selectCharacter(gameImage4,4));
        addGlowEffectOnButtonHover(avatar4, 4);


        // Creating the back button
        backButton = new Button("Back");
        backButton.setPrefWidth(100);
        backButton.setPrefHeight(30);

        backButton.setOnAction(event -> {
            Music.mediaPlayer1.stop();
            switchToGameMenu();
        });


        // Code for creating and showing the scene
        GridPane grid = new GridPane(); //arranges the avatars into a grid view
        grid.setVgap(20); //vertical size
        grid.setHgap(20); //horizontal size
        grid.setAlignment(Pos.CENTER); //center the content of the GridPane

        // Parameters ("Node", "columnIndex", "rowIndex")
        grid.add(avatar1, 0, 0); //top-left
        grid.add(avatar2, 1, 0); //top-right
        grid.add(avatar3, 0, 1); //bottom-left
        grid.add(avatar4, 1, 1); //bottom-right
        grid.add(backButton,0,2);


        VBox selectionLayout = new VBox(10); //stacks instructions and grid of avatars vertically
        selectionLayout.setAlignment(Pos.CENTER); //center the children of "VBox"
        selectionLayout.getChildren().addAll(instructions, grid, backButton); //displays avatar selection screen


        String backgroundImage = "-fx-background-image: url('/stage1.png');";
        String backgroundSize = "-fx-background-size: 1000px 666px;";
        String backgroundOpacity = "-fx-background-opacity: 0.3;";
        selectionLayout.setStyle(backgroundImage + backgroundSize + backgroundOpacity);


        Scene selectionScene = new Scene(selectionLayout, windowWidth, windowHeight);

        currentStage.setScene(selectionScene);
    }



    // Player selection
    private void selectCharacter(Image selectedImage, int playerID) {
        if (playerSelecting == 1) {

            selectedImagePlayer1 = selectedImage;
            player1_ID = playerID;

            instructions.setText("Player 2: Select your character");
            playerSelecting = 2;
        } else {
            selectedImagePlayer2 = selectedImage;
            player2_ID = playerID;
            if (!bothPlayersSelected) {
                bothPlayersSelected = true;
            if (callback != null) {
                callback.onSelectionComplete(backButton); //callback expressing that both players have been selected
                // Both players have been selected, start the game
                }
            }
        }
    }



    // Return to menu (code for the back button)
    public void switchToGameMenu() {
        // Instantiate "GameMenu" class and switch back to its scene
        GameMenu gameMenu = new GameMenu();
        gameMenu.start(window);
    }

    //Glow Effect when mouse is hovering over the button
    //color of glow depends on the value int of the method aka the avatar which is picked
    public void addGlowEffectOnButtonHover(Button button, int value) {
        DropShadow glow = new DropShadow();

        //setting certain colors for each of the value options
        //for avatar 1 = palegreen
        if (value == 1){
            button.setStyle("-fx-background-color: palegreen;");
        glow.setColor(Color.PALEGREEN);
        }
        //avatar 2 = hotpink
        if (value == 2){
            button.setStyle("-fx-background-color: hotpink;");
            glow.setColor(Color.HOTPINK);
        }
        //avatar 3 = deepskyblue
        if (value == 3){
            button.setStyle("-fx-background-color: deepskyblue;");
            glow.setColor(Color.DEEPSKYBLUE);
        }
        //avatar 4 = darkorchid
        else if (value == 4){
            button.setStyle("-fx-background-color: darkorchid;");
            glow.setColor(Color.DARKORCHID);
        }
        glow.setWidth(20);
        glow.setHeight(20);

        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(null));
    }




    public static void main(String[] args) {
    }

}