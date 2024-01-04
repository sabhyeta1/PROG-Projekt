package com.example.teamarbeit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import java.util.HashSet;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


import java.io.IOException;
import java.util.Set;


public class HelloApplication extends Application {
    Stage currentStage;
    Scene scene1, gameScene;
    Button button;
    static final int WINDOW_WIDTH = 1000;
    static final double WINDOW_HEIGHT = WINDOW_WIDTH * (0.666666666);

    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    static final int BALL_DIAMETER = 20;
    static int ballYSpeed = 1;
    static int ballXSpeed = 1;

    int scorePlayer1 = 0;
    int scorePlayer2 = 0;

    int xPosPlayer1 = 0;
    double yPosPlayer1 = WINDOW_HEIGHT / 2;
    int xPosPlayer2 = WINDOW_WIDTH - PADDLE_WIDTH;
    double yPosPlayer2 = WINDOW_HEIGHT / 2;
    boolean gameStarted;

    Thread thread;
    Image image;
    GraphicsContext graphics;
    Random random;
    Paddle player1;
    Paddle player2;
    Ball ball;
    Score score;
    GraphicsContext gc;
    Canvas gameCanvas;
    private final Set<KeyCode> keysPressedP1 = new HashSet<>(); //HashSet für Input Player 1
    private final Set<KeyCode> keysPressedP2 = new HashSet<>(); //HashSet für Input Player 2
    private static MediaPlayer mediaPlayer1 ,mediaPlayer2;




    @Override
    public void start(Stage primaryStage) throws IOException {


        currentStage = primaryStage;
        currentStage.setTitle("Pong Project");
        //Create Button
        button = new Button("Welcome to Pong");
        button.setOnAction(event -> {
            currentStage.setScene(gameScene); //Wenn Knopf gedrückt wird, wechsel auf gameScene und stelle gameSceneIsRunning auf true

                createPaddles();
                createBall();
                startCountdown(5, ball, gc, 100, WINDOW_WIDTH / 2, (int)WINDOW_HEIGHT / 2);
                updateCanvas();
                updateScore();
                playBackgroundMusic("E:/Program Files/JetBrains/IntelliJ IDEA Community Edition 2023.2.1/Projects/PROG-Projekt/Teamarbeit/src/main/resources/com.example.teamarbeit/children-electro-swing-2_medium-178290.mp3");


            });

        //Create Label/Text
        Label welcomeText = new Label("Welcome to the game!");
        welcomeText.setLayoutX((double) WINDOW_WIDTH /2);
        welcomeText.setLayoutY(WINDOW_HEIGHT/2 + 100);


        StackPane layout = new StackPane();
        layout.getChildren().addAll(welcomeText,button);


        Scene scene1 = new Scene(layout, WINDOW_WIDTH, WINDOW_HEIGHT);




        //Create gameCanvas to draw our Game

        score = new Score(WINDOW_WIDTH,(int) WINDOW_HEIGHT);

        gameCanvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        gc = gameCanvas.getGraphicsContext2D();




        StackPane gcRoot = new StackPane(gameCanvas);
        gameScene = new Scene(gcRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //neue Szene wird erstellt mit gcRoot
        gcRoot.setStyle("-fx-background-color: black;"); // Set the background color
        createPaddles();
        createBall();
        currentStage.setOnCloseRequest(windowEvent -> {
            if (mediaPlayer1 != null) {
                mediaPlayer1.stop();
            }
        });




        currentStage.setResizable(false);
        currentStage.setScene(scene1); // Set gameScene as the initial scene
        currentStage.show();





        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> paddleMovement())); //Timeline updatet das Game konstant alle 10 ms --> 100FPS
        tl.setCycleCount(Timeline.INDEFINITE); //Timeline wird für immer laufen bzw. wird indefinite Mal ausgeführt
        tl.play();
        gameCanvas.requestFocus(); //Sicherheitsvorkehrung damit gameCanvas unsere Keyboard Inputs annehmen kann, weil es jetzt in Fokus ist
    }
    private void createPaddles() {
        player1 = new Paddle(0, (double) ((WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2)), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        player2 = new Paddle(WINDOW_WIDTH - PADDLE_WIDTH, (double) ((WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2)), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }
    private void paddleMovement() {
        gameScene.setOnKeyPressed(event -> {
            KeyCode keyPressed = event.getCode(); //Gedrückte Taste wird als keyPressed gespeichert
            if (keyPressed == KeyCode.W || keyPressed == KeyCode.S) { //Wenn keyPressed W oder S ist, dann füge es ins HashSet von Player 1 hinzu
                keysPressedP1.add(keyPressed);
            } else if (keyPressed == KeyCode.UP || keyPressed == KeyCode.DOWN) { //Wenn keyPressed UP oder down ist, dann füge es ins HashSet von Player 2 hinzu
                keysPressedP2.add(keyPressed);
            }
        });

        gameScene.setOnKeyReleased(event -> { //losgelassene Tasten werden aus HashSet entfernt
            KeyCode keyReleased = event.getCode();
            if (keyReleased == KeyCode.W || keyReleased == KeyCode.S) {
                keysPressedP1.remove(keyReleased);
            } else if (keyReleased == KeyCode.UP || keyReleased == KeyCode.DOWN) {
                keysPressedP2.remove(keyReleased);
            }
        });

        //boolean werte bestimmen ob ein Paddle sich bewegt
        boolean moveP1Up = keysPressedP1.contains(KeyCode.W);
        boolean moveP1Down = keysPressedP1.contains(KeyCode.S);
        boolean moveP2Up = keysPressedP2.contains(KeyCode.UP);
        boolean moveP2Down = keysPressedP2.contains(KeyCode.DOWN);

        if (moveP1Up && player1.getYPaddlePosition() >= 0) { //Wenn W gedrückt, nicht losgelassen UND wenn Paddle nicht ganz oben ist, bewege Paddle nach oben
            player1.setYDirection(-player1.getPaddleSpeed());

        } else if (moveP1Down && player1.getYPaddlePosition() <= WINDOW_HEIGHT - PADDLE_HEIGHT) { //Wenn S gedrückt, nicht losgelassen UND wenn Paddle nicht ganz unten ist, bewege Paddle nach unten
            player1.setYDirection(player1.getPaddleSpeed());

        } else {
            player1.setYDirection(0);
        }

        if (moveP2Up && player2.getYPaddlePosition() >= 0) { //Kopie der Zeilen 145-153 aber für Player 2
            player2.setYDirection(-player2.getPaddleSpeed());
        } else if (moveP2Down && player2.getYPaddlePosition() <= WINDOW_HEIGHT - PADDLE_HEIGHT) {
            player2.setYDirection(player2.getPaddleSpeed());
        } else {
            player2.setYDirection(0);
        }

        allMovement();
        updateCanvas();
        updateScore();
    }
    private void createBall() {

        random = new Random();
        ball = new Ball(WINDOW_WIDTH / 2, (int)WINDOW_HEIGHT / 2, BALL_DIAMETER,random.nextInt(2) * 2 - 1, random.nextInt(2) * 2 - 1, player1, player2 );

    }

    private void updateCanvas() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); // Clear the canvas
        player1.draw(gc); // Redraw player 1 paddle
        player2.draw(gc); // Redraw player 2 paddle
        ball.draw(gc);
        score.draw(gc);
    }
    private void updateScore() {
        if (ball.getXPosBall() <= BALL_DIAMETER) {
            score.scorePlayer2++;
            System.out.println("Player 2: " + score.scorePlayer2);
            createBall();
            startCountdown(3, ball, gc, 40, WINDOW_WIDTH / 2, (int) WINDOW_HEIGHT / 2);
        }
        else if (ball.getXPosBall() >= WINDOW_WIDTH - BALL_DIAMETER) {
            score.scorePlayer1++;
            System.out.println("Player 1: " + score.scorePlayer1);
            createBall();
            startCountdown(3, ball, gc, 40, WINDOW_WIDTH / 2, (int)WINDOW_HEIGHT / 2);
        }
    }

    private void startCountdown(int duration, Ball ball, GraphicsContext gc, int fontSize, int fontXPos, int fontYPos) {
        VisualCountdown countdown = new VisualCountdown(duration, ball, gc, fontSize, fontXPos, fontYPos);
        countdown.countdownLogic();
    }

    private void allMovement(){
        player1.move();
        player2.move();
        ball.move();
    }
    private void playBackgroundMusic(String filePath) {
        Media backgroundMusic = new Media(new File(filePath).toURI().toString());
        mediaPlayer1 = new MediaPlayer(backgroundMusic);
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer1.setVolume(0.2);
        mediaPlayer1.play();
        mediaPlayer1.setOnError(() -> {
            System.out.println("Media error occurred: " + mediaPlayer1.getError());
            // You can perform error handling or logging here
        });
    }
    public static void playBounceSound(String filePath) {
        Media bounceSound = new Media(new File(filePath).toURI().toString());
        mediaPlayer2 = new MediaPlayer(bounceSound);
        mediaPlayer2.setCycleCount(1);
        mediaPlayer2.play();
    }

    private void run (GraphicsContext gc) {

    }




    public static void main(String[] args) {
        launch();
    }


}