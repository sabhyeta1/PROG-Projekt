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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.example.teamarbeit.GameMenu;
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
import static com.example.teamarbeit.HelloApplication.*;

import static com.example.teamarbeit.HelloApplication.WINDOW_HEIGHT;
import static com.example.teamarbeit.HelloApplication.WINDOW_WIDTH;
public class PauseScreen {

    public static Stage primaryStage;

    private int windowWidth;
    private int windowHeight;
    private Label headline;
    private static ExitPause exit;

    Image backgroundImage;

    ImageView backgroundImageView;

    StackPane root;

    StackPane stackPane;

    String imagePath = "C:\\Users\\marti\\IdeaProjects\\PROG-Projekt1\\Teamarbeit\\src\\main\\resources\\stage2.png";


    public PauseScreen(int windowWidth,int windowHeight,Stage primaryStage, ExitPause exit) {
        this.exit = exit;
        this.primaryStage = primaryStage;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        switchToPause();
    }

    public static void switchToPause() {
        VBox pauseLayout = createPauseLayout();
        String imagePath = "C:\\Users\\marti\\IdeaProjects\\PROG-Projekt1\\Teamarbeit\\src\\main\\resources\\stage2.png";
        StackPane root = createBackground(imagePath, pauseLayout);
        Scene selectionScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(selectionScene);
    }

    private static StackPane createBackground(String imagePath, VBox content) {
        Image backgroundImage = new Image(imagePath);
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(WINDOW_WIDTH);
        backgroundImageView.setFitHeight(WINDOW_HEIGHT);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundImageView, content);

        return stackPane;
    }

    private static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(60);
        return button;
    }


    private static VBox createPauseLayout() {
        Label titleLabel = new Label("Pause");
        titleLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: white;");
        Button continueButton = createStyledButton("Continue");
        Button menuButton = createStyledButton("Menu");
        Button quitButton = createStyledButton("Quit");


        continueButton.setOnAction(e -> {
            System.out.println("Continue Playing");
            if (exit != null){
                exit.endingPauseScreen();
            }
        });

        quitButton.setOnAction(e -> {
            System.out.println("Exiting the game");
            primaryStage.close();
        });

        menuButton.setOnAction(e -> {
            System.out.println("Return to Menu");
            GameMenu.mediaPlayer1.stop();
            GameMenu gameMenu = new GameMenu();
            gameMenu.start(primaryStage);
        });


        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(titleLabel, continueButton, menuButton, quitButton);
        layout.setPadding(new Insets(50, 0, 0, 0));

        continueButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        menuButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");
        quitButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 20px;");

        addGlowEffectOnHover(continueButton);
        addGlowEffectOnHover(menuButton);
        addGlowEffectOnHover(quitButton);


        return layout;
    }

    private static void addGlowEffectOnHover(Button button) {

        DropShadow glow = new DropShadow();
        glow.setColor(Color.RED);
        glow.setWidth(20);
        glow.setHeight(20);

        button.setOnMouseEntered(e -> button.setEffect(glow));
        button.setOnMouseExited(e -> button.setEffect(null));
    }

    public static void main(String[] args) {

    }

}
