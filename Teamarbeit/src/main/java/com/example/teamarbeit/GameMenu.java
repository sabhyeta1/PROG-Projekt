package com.example.teamarbeit;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameMenu extends Application {

    private Stage primaryStage;
    static final int WINDOW_WIDTH = 800;
    static final double WINDOW_HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Game Menu");

        switchToMenu();

        primaryStage.show();
    }

    // Use "file://" protocol to specify a local file path
    private void switchToMenu() {
        VBox menuLayout = createMenuLayout();
        String imagePath = "file:///Users/mbilla19/IdeaProjects/GameMenu/images/WhatsApp Image 2023-12-24 at 23.43.20.jpeg";
        StackPane root = createBackground(imagePath, menuLayout);
        primaryStage.setScene(new Scene(root, 800, 600));
    }

    private void switchToSettings() {
        VBox settingsLayout = createSettingsLayout();
        String imagePath = "file:///Users/mbilla19/IdeaProjects/GameMenu/images/WhatsApp Image 2023-12-24 at 23.43.20.jpeg";
        StackPane root = createBackground(imagePath, settingsLayout);
        primaryStage.setScene(new Scene(root, 800, 600));
    }

    private StackPane createBackground(String imagePath, VBox content) {
        Image backgroundImage = new Image(imagePath);
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(800);
        backgroundImageView.setFitHeight(600);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, content);

        return stackPane;
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
            System.out.println("Navigating to Avatar Selection Screen");
            // Hier den Code einfügen um auf Avatar Auswahl zuzugreifen
            Label labelGameScene = new Label("This is Gamescene!");
            Button buttonGameScene = new Button("Gamescene");

            VBox layoutGameScene = new VBox();
            layoutGameScene.getChildren().addAll(labelGameScene, buttonGameScene);
            buttonGameScene.setOnAction(event -> this.primaryStage.setScene(new Scene(layoutGameScene, 800, 600)));


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

    private VBox createSettingsLayout() {
        Label titleLabel = new Label("SETTINGS");
        titleLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: white;");

        Label gameSoundLabel = createStyledLabel("Game Sound");
        Label musicLabel = createStyledLabel("Music");

        Slider gameSoundSlider = createSlider();
        Slider musicSlider = createSlider();

        Button backButton = createStyledButton("Back");
        backButton.setOnAction(e -> switchToMenu());

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

    private Button createStyledButton(String text) {
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

    private Slider createSlider() {
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

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        return label;
    }
}