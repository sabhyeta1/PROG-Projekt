package com.example.teamarbeit;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import static com.example.teamarbeit.GameLogic.*;




public class PauseScreen {

    // The instances of the class
    public static Stage primaryStage;
    private static ExitPause exit;
    static String imagePath = "stage2.png";
    Image backgroundImage;
    ImageView backgroundImageView;


    // Constructor of the "PauseScreen" class
    public PauseScreen(Stage primaryStage, ExitPause exit, Ball ball, GraphicsContext gc) {

        // Initializing instances

        PauseScreen.exit = exit;
        PauseScreen.primaryStage = primaryStage;

        switchToPause(ball, gc); //function to open the pause screen
    }



    // Functions to create the pause screen
    public void switchToPause(Ball ball, GraphicsContext gc) { //function to open the pause screen
        VBox pauseLayout = createPauseLayout(ball, gc); //method to create the Layout in form of a "vbox"
        StackPane root = createBackground(imagePath, pauseLayout); //a background Picture is added to a "stackpane" named "root" and overlayed with the "pauseLayout"
        Scene selectionScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT); //the Scene for the pause screen is created with the elements root and the window size
        primaryStage.setScene(selectionScene); //the scene is set as the current scene
    }


    private VBox createPauseLayout(Ball ball, GraphicsContext gc) { //method to create the layout for the pause screen
        Label pauseTitleLabel = new Label("Pause"); //creates the label for the pause screen
        pauseTitleLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: white;"); //sets the format for the label
        // Creates button for the pause screen with the method "createStyledButton"
        Button continueButton = createStyledButton("Continue");
        Button settingsButton = createStyledButton("Settings");
        Button quitButton = createStyledButton("Quit");


        // Adding the events to the Buttons (to make them function)
        continueButton.setOnAction(e -> {
            System.out.println("Continue Playing"); //text that is displayed on the console
            switchToGame(ball, gc); //the method to go back to the game (and continue)
        });

        settingsButton.setOnAction(e -> {
            System.out.println("Switch to Settings"); //text that is displayed on the console
            switchToSettings(ball, gc); //the method the settings scene
        });

        quitButton.setOnAction(e -> {
            System.out.println("Return to Menu"); //text that is displayed on the console
            switchToMenu(); //the method to open the menu
        });


        // Creates a "vbox" where the label and the buttons are added (in the middle with padding)
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(pauseTitleLabel, continueButton, settingsButton, quitButton);
        layout.setPadding(new Insets(150, 0, 0, 0));

        // Sets format for all the buttons
        continueButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        settingsButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        quitButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");

        return layout;
    }



    // Functions for the buttons of the pause screen
    public static void switchToGame(Ball ball, GraphicsContext gc) { //a function to go back to the game (function for the "continueButton")
        if (exit != null) {
            exit.endingPauseScreen();
            GameLogic.startCountdown(3, ball, gc, 100, WINDOW_WIDTH / 2, (int) WINDOW_HEIGHT / 2);
        }
    }

    public void switchToSettings(Ball ball, GraphicsContext gc) { //opens the scene to change the settings (function for the "settingsButton")
        VBox settingsLayout = createSettingsLayout(ball, gc); //method to create the Layout in form of a "vbox"
        if (imagePath != null) { //function to throw an exception if the load of the background picture doesn't work
            StackPane root = createBackground(PauseScreen.imagePath, settingsLayout); //a background Picture is added to a "stackpane" named "root" and overlayed with the "settingsLayout"
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT)); //a new scene is created (with the elements root and the window size) and set

            Music.gameSoundSlider.setValue(Music.gameSoundSliderValue); //a slider is created to regulate the volume of the music during the game with a method from the class "Music.java"
            Music.musicSlider.setValue(Music.musicSliderValue); //a slider is created to regulate the volume of the background music of the menu and the pause screen with a method from the class "Music.java"
        } else { //the excption is thrown if the load of the background picture doesn't work
            System.out.println("Failed to load stage2.png. Check if the file exists and is in the correct directory."); //text that is printed out in the console
            primaryStage.setScene(new Scene(new Label("Failed to load resources"), WINDOW_WIDTH, WINDOW_HEIGHT)); //scene at text that is presented on the opened window
        }
    }

    public void switchToMenu() { //function to open the menu (function for the "menuButton")
        Music.mediaPlayer1.stop(); //stops the paused music from the game
        Music.mediaPlayer2.stop(); //stops the background music of the pause screen

        GameMenu gameMenu = new GameMenu(); //a new Instance of the class "GameMenu" is created
        gameMenu.start(primaryStage); //with this instance the game menu is started
    }



    // Functions to create the settings screen
    public VBox createSettingsLayout(Ball ball, GraphicsContext gc) { //method to create the layout for the settings scene
        Label settingsTitleLabel = new Label("SETTINGS"); //creates the label for the settings scene
        settingsTitleLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: white;"); //sets the format for the label

        Label gameSoundLabel = createStyledLabel("Game Sound"); //label for the slider of the music during the game
        Label musicLabel = createStyledLabel("Music"); //label for the slider of the background music of the menu and the pause screen

        Music.createSettingsSliders(); //the sliders are created with a method of the class "Music.java"

        Button backButton = GameMenu.createStyledButton("Back"); //creates a button to go back to the pause screen
        backButton.setOnAction(e -> { //adds an event to the button
            switchToPause(ball, gc); //function to open the pause screen
        });

        // Creates a "vbox" where the labels the sliders and the buttons are added
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER); //centers added objects in the middle
        layout.setPadding(new Insets(50)); //sets padding of the objects
        layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT.desaturate(), CornerRadii.EMPTY, Insets.EMPTY))); //a background picture is added to the "vbox"
        layout.getChildren().addAll(settingsTitleLabel, gameSoundLabel, Music.gameSoundSlider, musicLabel, Music.musicSlider, backButton);

        // Sets format for the button and the labels
        backButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        gameSoundLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; -fx-text-fill: white;");
        musicLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; -fx-text-fill: white;");

        return layout;
    }



    //general Functions who are used often
    private StackPane createBackground(String imagePath, VBox content) { //creates a background picture (with an "image path" parameter that is added in a "stackpane" before the "vbox" parameter
        backgroundImage = GameMenu.loadImage(imagePath); //an image is loaded with a method of the class "GameMenu.java"
        backgroundImageView = new ImageView(backgroundImage);
        // Sets the size of the picture
        backgroundImageView.setFitWidth(WINDOW_WIDTH);
        backgroundImageView.setFitHeight(WINDOW_HEIGHT);

        StackPane stackPane = new StackPane(); //a "stackpane" is created
        stackPane.getChildren().addAll(backgroundImageView, content); //parameters are added to the "stackpane"

        return stackPane;
    }


    private static Label createStyledLabel(String text) { //creates a label with a certain format (with a text parameter)
        Label label = new Label(text); //creates the object
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: white;"); //format
        return label;
    }


    public static Button createStyledButton(String text) { //creates the style of a button (parameter)
        Button button = new Button(text); //creates this object
        // Creates the size of the button
        button.setPrefWidth(200);
        button.setPrefHeight(60);
        GameMenu.addGlowEffectOnHover(button); //method to add a glow effect for the button
        return button;
    }







    public static void main(String[] args) {
    }



}
