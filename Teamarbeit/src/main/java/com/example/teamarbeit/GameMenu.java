package com.example.teamarbeit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.InputStream;

import static com.example.teamarbeit.HelloApplication.mediaPlayer3;

public class GameMenu extends Application implements AvatarSelectionCompleteCallback {

    public Stage primaryStage;
    static final int WINDOW_WIDTH = 1000;
    static final double WINDOW_HEIGHT = 666;
    static Slider gameSoundSlider;
    static Slider musicSlider;
    private static double gameSoundSliderValue = 50.0;
    private static double musicSliderValue = 50.0;
    public HelloApplication game;
    public static MediaPlayer mediaPlayer1;

    //public static void main(String[] args) {
    //launch(args);
    //}


    public GameMenu() {
        this.game = new HelloApplication();
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Game Menu");

        switchToMenu();
        primaryStage.show();
        playMenuMusic("Teamarbeit/src/main/resources/com.example.teamarbeit/happy-rock-165132.mp3");

    }

    // Use "file://" protocol to specify a local file path
    private void switchToMenu() {
        VBox menuLayout = createMenuLayout();
        Image image1 = loadImage("stage1.png");
        if (image1 != null) {
            StackPane root = createBackground(image1, menuLayout);
            primaryStage.setScene(new Scene (root, WINDOW_WIDTH, WINDOW_HEIGHT));
        } else {
            // Handle the case where the image isn't loaded correctly
            System.out.println("Failed to load stage1.png. Check if the file exists and is in the correct directory.");
            primaryStage.setScene(new Scene(new Label("Failed to load resources"), WINDOW_WIDTH, WINDOW_HEIGHT));
        }
    }

    private void switchToSettings() {
        VBox settingsLayout = createSettingsLayout();
        Image image2 = loadImage("stage2.png"); // Modified to use loadImage method
        if (image2 != null) {
            StackPane root = createBackground(image2, settingsLayout);
            primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));

            gameSoundSlider.setValue(gameSoundSliderValue);
            musicSlider.setValue(musicSliderValue);
        } else {
            System.out.println("Failed to load stage2.png. Check if the file exists and is in the correct directory.");
            primaryStage.setScene(new Scene(new Label("Failed to load resources"), WINDOW_WIDTH, WINDOW_HEIGHT));
        }
    }

    private StackPane createBackground(Image backgroundImage, VBox content) {
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(WINDOW_WIDTH);
        backgroundImageView.setFitHeight(WINDOW_HEIGHT);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, content);

        return stackPane;
    }

    private void switchToGame() {
        try {
            game.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error starting the game.");
        }
    }
    private VBox createMenuLayout() {
        Label titleLabel = new Label("PING PONG");
        titleLabel.setStyle("-fx-font-size: 100px; -fx-text-fill: white;");

        Button startButton = createStyledButton("Start");
        Button settingsButton = createStyledButton("Settings");
        Button quitButton = createStyledButton("Quit");

        addGlowEffectOnHover(startButton);
        addGlowEffectOnHover(settingsButton);
        addGlowEffectOnHover(quitButton);

        startButton.setOnAction(e -> {
            createAvatarSelection();
            System.out.println("Navigating to Avatar Selection Screen");
            //switchToGame();
            Label labelGameScene = new Label("This is Gamescene!");
            Button buttonGameScene = new Button("Gamescene");

            VBox layoutGameScene = new VBox();
            layoutGameScene.getChildren().addAll(labelGameScene, buttonGameScene);
            buttonGameScene.setOnAction(event -> this.primaryStage.setScene(new Scene(layoutGameScene, WINDOW_WIDTH, WINDOW_HEIGHT)));


            layoutGameScene.setStyle("-fx-background-color: black;");
        });

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
        layout.getChildren().addAll(titleLabel, startButton, settingsButton, quitButton);
        layout.setPadding(new Insets(50, 0, 0, 0));

        startButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        settingsButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        quitButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");

        return layout;
    }
    public static void playMenuMusic(String filePath) { //Hintergrundmusik
        Media backgroundMusic = new Media(new File(filePath).toURI().toString());
        mediaPlayer1 = new MediaPlayer(backgroundMusic);
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE); //Musik soll unendlich lang geloopt werden
        mediaPlayer1.setVolume(0.5); //50% Lautstärke
        mediaPlayer1.play();
        mediaPlayer1.setOnError(() -> {
            System.out.println("Media error occurred: " + mediaPlayer1.getError());
            //Error code, falls iwann einer kommt
        });
    }
    private void createAvatarSelection(){
        Avatars avatarSelection = new Avatars(WINDOW_WIDTH, (int) WINDOW_HEIGHT, primaryStage, this);
    }

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


        // Adding the button to the VBox
        root.getChildren().add(startGameButtonPosition);
        // Avatar selection is complete, start the game

    }


    public VBox createSettingsLayout() {
        Label titleLabel = new Label("SETTINGS");
        titleLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: white;");

        Label gameSoundLabel = createStyledLabel("Game Sound");
        Label musicLabel = createStyledLabel("Music");

        gameSoundSlider = createSlider();
        musicSlider = createSlider();

        //Zeile 173-179: Damit sich die Slider merken welche Werte die hatten, falls man Settings schließt und neu öffnet
        gameSoundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameSoundSliderValue = newValue.doubleValue();
            setVolume(); // Call setVolume() when gameSoundSlider's value changes
        });

        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            musicSliderValue = newValue.doubleValue();
            setVolume(); // Call setVolume() when musicSlider's value changes
        });

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(e -> {
            switchToMenu();
            setVolume();
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT.desaturate(), CornerRadii.EMPTY, Insets.EMPTY)));
        layout.getChildren().addAll(titleLabel, gameSoundLabel, gameSoundSlider, musicLabel, musicSlider, backButton);

        backButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        addGlowEffectOnHover(backButton);
        gameSoundLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; -fx-text-fill: white;");
        musicLabel.setStyle("-fx-background-color: transparent; -fx-font-size: 24px; -fx-text-fill: white;");

        return layout;
    }

    public static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(60);
        return button;
    }

    private void addGlowEffectOnHover(Button button) {
        DropShadow glow = new DropShadow();
        glow.setColor(Color.RED);
        glow.setWidth(20);
        glow.setHeight(20);

        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(null));
    }

    public Slider createSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(50);
        slider.setOrientation(Orientation.HORIZONTAL);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        return slider;
    }
    public static Slider getGameSoundSlider() {
        return gameSoundSlider;
    }
    public static Slider getMusicSlider() {
        return musicSlider;
    }
    public static double getSliderValue(Slider inputSlider){
        if(inputSlider != null) {
            return inputSlider.getValue() /100;
        }
        else return 0.5;
    }
    public static void setVolume() {
        if (mediaPlayer1 != null) {
            mediaPlayer1.setVolume(GameMenu.getSliderValue(GameMenu.getMusicSlider()));
            System.out.println("Ändert menuMusic Läutstärke");
        }
        if (mediaPlayer3 != null) {
            System.out.println("Ändert bounceSound Lautstärke");
            mediaPlayer3.setVolume(GameMenu.getSliderValue(GameMenu.getGameSoundSlider()));
        }
    }
    public static void reduceVolumeGradually() { //Fade away von Menu Music (Wenn man auf "Start The Game" drückt)
        Timeline volumeReductionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> {
                    if (mediaPlayer1.getVolume() > 0) {
                        double currentVolume = mediaPlayer1.getVolume();
                        mediaPlayer1.setVolume(currentVolume - 0.05); // Adjust decrement value as needed
                    } else {
                        mediaPlayer1.stop();
                    }
                })
        );
        volumeReductionTimeline.setCycleCount(Timeline.INDEFINITE);
        volumeReductionTimeline.play();
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        return label;
    }

    private Image loadImage(String resourceName) {
        InputStream stream = getClass().getResourceAsStream("/" + resourceName); // note the slash at the beginning
        if (stream == null) {
            System.out.println("Resource not found: " + resourceName);
            return null;
        }
        return new Image(stream);
    }
}