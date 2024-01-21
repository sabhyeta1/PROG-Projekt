package com.example.teamarbeit;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.InputStream;


// Inspired by youtube video https://youtu.be/aOcow70vqb4?si=hLKya8UCaQ4UxyV6

// Creates the class "GameMenu" which has the superclass "Application" and implements the interface "AvatarSelectionCompleteCallback"
public class GameMenu extends Application implements AvatarSelectionCompleteCallback { //implementation of callback method for the  for avatar selection

    // The instances of the class
    public static Stage primaryStage; //this object is also used in other classes (so it is static)
    //the window (scenes, stages) is always the same size so these two Instances are constant
    static final int WINDOW_WIDTH = 1000;
    static final double WINDOW_HEIGHT = 666;
    public static String menuMusicPath = "./Teamarbeit/src/main/resources/menu_music.mp3";

    public GameLogic game;
    StackPane root;  // StackPane to hold the content of the current scene

    // Constructor for the "GameMenu" class
    public GameMenu() {
        this.game = new GameLogic();  // Start the game logic
    }

    @Override
    public void start(Stage primaryStage) {
        GameMenu.primaryStage = primaryStage;  // Setting the primary stage for the GameMenu
        GameMenu.primaryStage.setTitle("Game Menu");  // Setting the title of the primary stage

        switchToMenu();  // Switching to the main menu
        primaryStage.show();  // Displaying the primary stage
        Music.playMenuMusic(GameMenu.menuMusicPath);  // Playing menu background music
    }

    // Functions for going back to the game menu
    private void switchToMenu() {
        VBox menuLayout = createMenuLayout();  // Creating the layout for the main menu
        Image image1 = loadImage("stage1.png");  // Loading background image for the main menu

        if (image1 != null) {
            StackPane root = createBackground(image1, menuLayout);  // Creating a StackPane with background and menu layout
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));  // Setting the scene
        } else {
            // Handling the case where the image isn't loaded correctly
            System.out.println("Failed to load stage1.png. Check if the file exists and is in the correct directory.");
            primaryStage.setScene(new Scene(new Label("Failed to load resources"), WINDOW_WIDTH, WINDOW_HEIGHT));
        }
    }

    private VBox createMenuLayout() {
        Label menuTitleLabel = new Label("PING PONG");  // Creating a label for the menu title
        menuTitleLabel.setStyle("-fx-font-size: 100px; -fx-text-fill: white;");  // Styling the menu title label

        // Creating buttons for Start, Settings, and Quit
        Button startButton = createStyledButton("Start");
        Button settingsButton = createStyledButton("Settings");
        Button quitButton = createStyledButton("Quit");

        // Setting actions for the menu buttons
        startButton.setOnAction(e -> {
            createAvatarSelection();  // Creating avatar selection screen
            System.out.println("Navigating to Avatar Selection Screen");
            // Creating GameScene layout
            Label labelGameScene = new Label("This is the Gamescene!");
            Button buttonGameScene = new Button("Gamescene");
            VBox layoutGameScene = new VBox();
            layoutGameScene.getChildren().addAll(labelGameScene, buttonGameScene);
            buttonGameScene.setOnAction(event -> GameMenu.primaryStage.setScene(new Scene(layoutGameScene, WINDOW_WIDTH, WINDOW_HEIGHT)));
            layoutGameScene.setStyle("-fx-background-color: black;");
        });

        // Adding events to the menu buttons
        settingsButton.setOnAction(e -> {
            System.out.println("Opening Settings");
            switchToSettings();  // Switching to the settings screen
        });

        quitButton.setOnAction(e -> {
            System.out.println("Exiting the game");
            primaryStage.close();  // Closing the game
        });

        // Creating a VBox layout for the main menu
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(menuTitleLabel, startButton, settingsButton, quitButton);  // Adding elements to the layout
        layout.setPadding(new Insets(50, 0, 0, 0));  // Setting padding for layout

        // Styling the buttons
        startButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        settingsButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        quitButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");

        return layout;  // Returning the configured VBox layout for the main menu
    }




    // Functions to create the settings screen
    public void switchToSettings() {
        VBox settingsLayout = createSettingsLayout();
        Image image2 = loadImage("stage2.png"); //modified to use loadImage method
        if (image2 != null) {
            root = createBackground(image2, settingsLayout);
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));

            Music.gameSoundSlider.setValue(Music.gameSoundSliderValue);
            Music.musicSlider.setValue(Music.musicSliderValue);
        } else {
            System.out.println("Failed to load stage2.png. Check if the file exists and is in the correct directory.");
            primaryStage.setScene(new Scene(new Label("Failed to load resources"), WINDOW_WIDTH, WINDOW_HEIGHT));
        }
    }



    public VBox createSettingsLayout() {
        Label settingsTitleLabel = new Label("SETTINGS");
        settingsTitleLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: white;");

        Label gameSoundLabel = createStyledLabel("Game Sound");
        Label musicLabel = createStyledLabel("Music");

        Music.createSettingsSliders();  // Creating sliders for adjusting game sound and music

        Button backButton = createStyledButton("Back");

        // Setting the action to be performed when the backButton is clicked
        backButton.setOnAction(e -> {
            switchToMenu();  //
            Music.setVolume();  // Adjusting the volume using the setVolume() method from the Music class
        });

        VBox layout = new VBox(20);  // Creating a VBox layout with vertical spacing
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));  // Setting padding around the layout

        // Setting the background color
        layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT.desaturate(), CornerRadii.EMPTY, Insets.EMPTY)));

        // Adding the elements
        layout.getChildren().addAll(settingsTitleLabel, gameSoundLabel, Music.gameSoundSlider, musicLabel, Music.musicSlider, backButton);

        backButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        gameSoundLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; -fx-text-fill: white;");
        musicLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; -fx-text-fill: white;");

        return layout;  // Returning the VBox layout
    }




    // Functions to go to the avatar selection screen and start the game
    private void createAvatarSelection(){ //selecting of the avatar
        new Avatars(WINDOW_WIDTH, (int) WINDOW_HEIGHT, primaryStage, this);
    }


    // Function that is invoked in the "Avatars" class
    @Override
    public void onSelectionComplete(Button backButton) {
        Button startGameButton = new Button("Start Game");  // Creating a new Button for starting the game
        startGameButton.setPrefWidth(100);
        startGameButton.setPrefHeight(30);
        startGameButton.setOnAction(event -> switchToGame());  // Setting action for when startGameButton is clicked

        VBox root = (VBox) primaryStage.getScene().getRoot();  // Accessing the current scene's root

        StackPane startGameButtonPosition = new StackPane();  // Creating a StackPane to position the startGameButton
        startGameButtonPosition.getChildren().add(startGameButton);
        startGameButtonPosition.setAlignment(Pos.BASELINE_CENTER);

        int indexOfBackButton = root.getChildren().indexOf(backButton);  // Finding the index of the backButton in the root's children

        root.getChildren().add(indexOfBackButton, startGameButtonPosition);  // Adding the startGameButton at the same index as the backButton to the "VBox"
        // Avatar selection is complete, start the game
    }



    // Function for starting the game
    private void switchToGame() {
        try {
            game.start(primaryStage); // Attempt to start the game
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error starting the game.");  // issue running the game
        }
    }


    // Adds a glow effect to a Button when the mouse hovers over it.
    public static void addGlowEffectOnHover(Button button) {
        DropShadow glow = new DropShadow(); // Creates a new DropShadow effect for the glow.
        glow.setColor(Color.RED);
        glow.setWidth(20);
        glow.setHeight(20);

        button.setOnMouseEntered(e -> button.setEffect(glow)); // Attaches the glow effect
        button.setOnMouseExited(e -> button.setEffect(null));   // Removes the glow effect
    }


    // Creates a StackPane with a background image and additional content.
    private StackPane createBackground(Image backgroundImage, VBox content) {

        // Creates an ImageView to hold the background image.
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(WINDOW_WIDTH);
        backgroundImageView.setFitHeight(WINDOW_HEIGHT);
        StackPane stackPane = new StackPane(); // Creates a new StackPane to organize elements in a layered manner.
        stackPane.getChildren().addAll(backgroundImageView, content); // Adds the background image and additional content to the StackPane.

        // Returns the StackPane with the layered elements (background image and content).
        return stackPane;
    }




    private static Label createStyledLabel(String text) { //creates a label with format
        Label label = new Label(text); //creates the object
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");//format
        return label;
    }


    public static Button createStyledButton(String text) { //creates the style of a button (parameter)
        Button button = new Button(text); //creates this object
        // Creates the size of the button
        button.setPrefWidth(200);
        button.setPrefHeight(60);
        addGlowEffectOnHover(button); //method to add a glow effect
        return button;
    }


    public static Image loadImage(String resourceName) {  // Loads an image resource
        InputStream stream = GameMenu.class.getResourceAsStream("/" + resourceName); // Retrieves an InputStream for the resource
        if (stream == null) {
            System.out.println("Resource not found: " + resourceName);
            return null;
        }
        return new Image(stream);
    }



}