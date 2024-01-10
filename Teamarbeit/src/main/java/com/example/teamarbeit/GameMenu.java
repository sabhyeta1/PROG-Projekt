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




// Creates the class "GameMenu" which has the superclass "Application" and implements the interface "AvatarSelectionCompleteCallback"
public class GameMenu extends Application implements AvatarSelectionCompleteCallback { //implementation of callback method for the  for avatar selection

    // The instances of the class
    public static Stage primaryStage; //this object is also used in other classes (so it is static)
    //the window (scenes, stages) is always the same size so these two Instances are constant
    static final int WINDOW_WIDTH = 1000;
    static final double WINDOW_HEIGHT = 666;
    public static String mneuMusicPath = "./Teamarbeit/src/main/resources/com.example.teamarbeit/menu_music.mp3";

    public HelloApplication game;
    StackPane root;


    // Constructor for the "GameMenu" class
    public GameMenu() {
        this.game = new HelloApplication();
    }



    @Override
    public void start(Stage primaryStage) {
        GameMenu.primaryStage = primaryStage;
        GameMenu.primaryStage.setTitle("Game Menu");

        switchToMenu();
        primaryStage.show();
        Music.playMenuMusic(GameMenu.mneuMusicPath);
    }



    // Functions for going back to the game menu
    private void switchToMenu() {
        VBox menuLayout = createMenuLayout();
        Image image1 = loadImage("stage1.png");
        //add background stage1 for the avatar selection screen
        if (image1 != null) {
            StackPane root = createBackground(image1, menuLayout);
            primaryStage.setScene(new Scene (root, WINDOW_WIDTH, WINDOW_HEIGHT));
        } else {
            // Handling the case where the image isn't loaded correctly
            System.out.println("Failed to load stage1.png. Check if the file exists and is in the correct directory.");
            primaryStage.setScene(new Scene(new Label("Failed to load resources"), WINDOW_WIDTH, WINDOW_HEIGHT));
        }
    }


    private VBox createMenuLayout() {
        Label menuTitleLabel = new Label("PING PONG");
        menuTitleLabel.setStyle("-fx-font-size: 100px; -fx-text-fill: white;");
        Button startButton = createStyledButton("Start");
        Button settingsButton = createStyledButton("Settings");
        Button quitButton = createStyledButton("Quit");

        startButton.setOnAction(e -> {
            createAvatarSelection();
            System.out.println("Navigating to Avatar Selection Screen");
            Label labelGameScene = new Label("This is the Gamescene!");
            Button buttonGameScene = new Button("Gamescene");

            VBox layoutGameScene = new VBox();
            layoutGameScene.getChildren().addAll(labelGameScene, buttonGameScene);
            buttonGameScene.setOnAction(event -> GameMenu.primaryStage.setScene(new Scene(layoutGameScene, WINDOW_WIDTH, WINDOW_HEIGHT)));
            layoutGameScene.setStyle("-fx-background-color: black;");
        });

        //Adding events to the menu buttons
        settingsButton.setOnAction(e -> {
            System.out.println("Opening Settings");
            switchToSettings();
        });

        quitButton.setOnAction(e -> {
            System.out.println("Exiting the game");
            primaryStage.close();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(menuTitleLabel, startButton, settingsButton, quitButton);
        layout.setPadding(new Insets(50, 0, 0, 0));

        startButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        settingsButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        quitButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");

        return layout;
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

        Music.createSettingsSliders();

        //Create button for returning to menu
        Button backButton = createStyledButton("Back");
        backButton.setOnAction(e -> {
            switchToMenu();
            Music.setVolume();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT.desaturate(), CornerRadii.EMPTY, Insets.EMPTY)));
        layout.getChildren().addAll(settingsTitleLabel, gameSoundLabel, Music.gameSoundSlider, musicLabel, Music.musicSlider, backButton);

        backButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        gameSoundLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; -fx-text-fill: white;");
        musicLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; -fx-text-fill: white;");

        return layout;
    }



    // Functions to go to the avatar selection screen and start the game
    private void createAvatarSelection(){ //selecting of the avatar
        Avatars avatarSelection = new Avatars(WINDOW_WIDTH, (int) WINDOW_HEIGHT, primaryStage, this);
    }


    // Function that is invoked in the "Avatars" class
    @Override
    public void onSelectionComplete() {
        Button startGameButton = new Button("Start Game");
        startGameButton.setPrefWidth(100);
        startGameButton.setPrefHeight(30);
        startGameButton.setOnAction(event -> switchToGame());

        // Accessing the current scene's root (assuming it's a VBox)
        VBox root = (VBox) primaryStage.getScene().getRoot();

        StackPane startGameButtonPosition = new StackPane();
        startGameButtonPosition.getChildren().add(startGameButton);
        startGameButtonPosition.setAlignment(Pos.BASELINE_CENTER);

        // Adding the button to the "VBox"
        root.getChildren().add(startGameButtonPosition);
        // Avatar selection is complete, start the game
    }


    // Function for starting the game
    private void switchToGame() {
        try {
            game.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error starting the game.");
        }
    }



    // General functions that are used often
    public static void addGlowEffectOnHover(Button button) {
        DropShadow glow = new DropShadow();
        glow.setColor(Color.RED);
        glow.setWidth(20);
        glow.setHeight(20);

        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(null));
    }


    // Create functions
    private StackPane createBackground(Image backgroundImage, VBox content) {
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(WINDOW_WIDTH);
        backgroundImageView.setFitHeight(WINDOW_HEIGHT);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, content);

        return stackPane;
    }


    private static Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        return label;
    }


    public static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(60);
        addGlowEffectOnHover(button);
        return button;
    }


    public static Image loadImage(String resourceName) {
        InputStream stream = GameMenu.class.getResourceAsStream("/" + resourceName); //note the slash at the beginning
        if (stream == null) {
            System.out.println("Resource not found: " + resourceName);
            return null;
        }
        return new Image(stream);
    }



}