package com.example.teamarbeit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import static com.example.teamarbeit.GameLogik.*;




public class PauseScreen {

    // The instances of the class
    public static Stage primaryStage;
    private static ExitPause exit;
    static String imagePath = "stage2.png";
    Image backgroundImage;
    ImageView backgroundImageView;

    // Placeholders
    private int windowWidth;
    private int windowHeight;



    // Constructor of the "PauseScreen" class
    public PauseScreen(int windowWidth,int windowHeight,Stage primaryStage, ExitPause exit) {

        // Initializing instances

        PauseScreen.exit = exit;
        PauseScreen.primaryStage = primaryStage;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        switchToPause();
    }



    // Functions to create the pause screen
    public void switchToPause() {
        VBox pauseLayout = createPauseLayout();
        StackPane root = createBackground(imagePath, pauseLayout);
        Scene selectionScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(selectionScene);
    }


    private VBox createPauseLayout() {
        Label pauseTitleLabel = new Label("Pause");
        pauseTitleLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: white;");
        Button continueButton = createStyledButton("Continue");
        Button settingsButton = createStyledButton("Settings");
        Button quitButton = createStyledButton("Quit");


        continueButton.setOnAction(e -> {
            System.out.println("Continue Playing");
            switchToGame();
        });

        settingsButton.setOnAction(e -> {
            System.out.println("Switch to Settings");
            switchToSettings();
        });

        quitButton.setOnAction(e -> {
            System.out.println("Return to Menu");
            switchToMenu();
        });


        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(pauseTitleLabel, continueButton, settingsButton, quitButton);
        layout.setPadding(new Insets(50, 0, 0, 0));

        continueButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        settingsButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        quitButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");

        return layout;
    }



    // Functions for the buttons of the pause screen
    public static void switchToGame() {
        if (exit != null) {
            exit.endingPauseScreen();
        }
    }

    public void switchToSettings() {
        VBox settingsLayout = createSettingsLayout();
        //Image image2 = loadImage("stage2.png");// Modified to use loadImage method
        //add background stage2 for settings screen
        if (imagePath != null) {
            StackPane root = createBackground(PauseScreen.imagePath, settingsLayout);
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));

            Music.gameSoundSlider.setValue(Music.gameSoundSliderValue);
            Music.musicSlider.setValue(Music.musicSliderValue);
        } else {
            System.out.println("Failed to load stage2.png. Check if the file exists and is in the correct directory.");
            primaryStage.setScene(new Scene(new Label("Failed to load resources"), WINDOW_WIDTH, WINDOW_HEIGHT));
        }
    }

    public void switchToMenu() {
        Music.mediaPlayer1.stop();
        Music.mediaPlayer2.stop();

        GameMenu gameMenu = new GameMenu();
        gameMenu.start(primaryStage);
    }



    // Functions to create the settings screen
    public VBox createSettingsLayout() {
        Label settingsTitleLabel = new Label("SETTINGS");
        settingsTitleLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: white;");

        Label gameSoundLabel = createStyledLabel("Game Sound");
        Label musicLabel = createStyledLabel("Music");

        Music.createSettingsSliders();

        Button backButton = GameMenu.createStyledButton("Back");
        backButton.setOnAction(e -> {
            switchToPause();
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



    //general Functions who are used often
    private StackPane createBackground(String imagePath, VBox content) {
        backgroundImage = GameMenu.loadImage(imagePath);
        backgroundImageView = new ImageView(backgroundImage);
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
        GameMenu.addGlowEffectOnHover(button);
        return button;
    }



    //Placeholder
    public void reduceVolumeGradually() { //Fade away von Menu Music (Wenn man auf "Start The Game" drückt)
        Timeline volumeReductionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> {

                    if (Music.mediaPlayer1.getVolume() > 0) {
                        double currentVolume = Music.mediaPlayer1.getVolume();
                        Music.mediaPlayer1.setVolume(currentVolume - 0.05); // Adjust decrement value as needed
                    } else {
                        Music.mediaPlayer1.stop();
                    }

                })
        );
        volumeReductionTimeline.setCycleCount(Timeline.INDEFINITE);
        volumeReductionTimeline.play();
    }



    public static void main(String[] args) {
    }



}
