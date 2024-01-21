package com.example.teamarbeit;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.HashSet;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Set;

import static com.example.teamarbeit.Avatars.*;


public class GameLogik extends Application implements ExitPause{

    // The instances of the class
    static Stage currentStage;
    Scene scene1, gameScene;
    static final int WINDOW_WIDTH = 1000;
    static final double WINDOW_HEIGHT = WINDOW_WIDTH * (0.666666666);

    static final int PADDLE_WIDTH = 25;
    static final int MAX_SCORE = 3;
    static final int PADDLE_HEIGHT = 100;
    static final int BALL_DIAMETER = 20;
    static final double AVATAR_HEIGHT = 150;
    Random random;
    Paddle player1;
    Paddle player2;
    Ball ball;
    Score score;
    static VisualCountdown countdown;
    static String mediaPlayer2Path;
    GraphicsContext gc;
    Canvas gameCanvas;
    public static AnchorPane root, secondRoot;

    Timeline tl;

    private final Set<KeyCode> keysPressedP1 = new HashSet<>(); //"HashSet" (list) für Input Player 1
    private final Set<KeyCode> keysPressedP2 = new HashSet<>(); //"HashSet" (list) für Input Player 2

    // Placeholders:
    Button button;
    static int ballYSpeed = 1;
    static int ballXSpeed = 1;

    int scorePlayer1 = 0;
    int scorePlayer2 = 0;

    int xPosPlayer1 = 0;
    double yPosPlayer1 = WINDOW_HEIGHT / 2;
    int xPosPlayer2 = WINDOW_WIDTH - PADDLE_WIDTH;
    double yPosPlayer2 = WINDOW_HEIGHT / 2;

    public GameMenu switchToGameMenu;
    boolean gameStarted;
    String backgroundImagePath;
    Image backgroundImage;
    ImageView backgroundView;

    Thread thread;
    Image image;
    GraphicsContext graphics;



    @Override
    public void start(Stage primaryStage) throws IOException {

        currentStage = primaryStage; //stage is taken over
        currentStage.setTitle("Play"); //stage gets title Pong Project (you can see it up on the left side)
        mediaPlayer2Path = "./Teamarbeit/src/main/resources/com.example.teamarbeit/game_music.mp3";

        Music.mediaPlayer1.stop();

        // Create the canvas "gameCanvas "to draw our Game (a canvas is like a scene, but you can draw objects here like rectangles and circles)
        score = new Score(WINDOW_WIDTH,(int) WINDOW_HEIGHT); //score is created

        gameCanvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT); //a canvas is created
        gc = gameCanvas.getGraphicsContext2D(); //the objects we have drawn are saved as "gc", they can execute functions for the objects (change colors, sizes and so on)

        Image backgroundImage = GameMenu.loadImage("Background_FINALE.jpg");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(WINDOW_WIDTH);
        backgroundView.setFitHeight(WINDOW_HEIGHT);

        root = new AnchorPane();
        createAvatars();
        StackPane gcRoot = new StackPane(backgroundView, GameLogik.root, gameCanvas); //another layout is created with the class "StackPane"
        gameScene = new Scene(gcRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //a new scene is created with the "StackPane" "gcRoot" in the size "WINDOW_WIDTH" X "WINDOW_HEIGHT"
        gcRoot.setStyle("-fx-background-color: black;"); //setting the background color
        createPaddles();
        createBall();
        currentStage.setOnCloseRequest(windowEvent -> { //as soon as the window closes the background music stops (if there already is a background music)
            if (Music.mediaPlayer2 != null) {
                Music.mediaPlayer2.stop();
            }
        });

        currentStage.setResizable(false); //window size stays the same - it can not be changed by the end user
        currentStage.setScene(scene1); //set "gameScene" as the initial scene
        currentStage.show(); //stage is shown
        currentStage.setScene(gameScene); //when this button is pressed, the scene is changed to "gameScene" and "gameSceneIsRunning" is set to true

        tl = new Timeline(new KeyFrame(Duration.millis(10), e -> { //timeline updates these methods constantly every 10 ms --> 100FPS
            paddleMovement();
            allMovement();
            updateCanvas();
            updateScore();
            if (countdown.currentCountdownValue >0 && countdown != null){
                countdown.drawCountdown(gc);
            }
            gameOver();
            if (gameOver()) {
                if (score.scorePlayer1 == MAX_SCORE) {
                    showVictoryScreen("Player 1 Wins!", selectedImagePlayer1, selectedImagePlayer2);
                    Music.mediaPlayer2.stop();
                }
                else {
                    showVictoryScreen("Player 2 Wins!", selectedImagePlayer2, selectedImagePlayer1);
                    Music.mediaPlayer2.stop();
                    }
            }
        }));

        tl.setCycleCount(Timeline.INDEFINITE); //timeline is executed indefinite times - runs forever, except it is interrupted
        createPaddles(); //for methods for row 79 to 84 (see more below)
        createBall();
        startCountdown(5, ball, gc, 100, WINDOW_WIDTH / 2, (int)WINDOW_HEIGHT / 2);
        updateCanvas();
        updateScore();
        Music.playGameMusic(mediaPlayer2Path);
        tl.play(); //starts timeline

        gameCanvas.requestFocus(); //safety precaution so "gameCanvas" receive our keyboard inputs - canvas is focused now
    }


    // Create functions (and function for exiting/ending)
    private void createPaddles() { //creates two paddles with the "Paddle" constructor --> see row 17 - 23 (class "Paddle")
        player1 = new Paddle(0, (double) ((WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2)), PADDLE_WIDTH, PADDLE_HEIGHT, player1_ID);
        player2 = new Paddle(WINDOW_WIDTH - PADDLE_WIDTH, (double) ((WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2)), PADDLE_WIDTH, PADDLE_HEIGHT, player2_ID);
    }


    private void createBall() { //ball is created with "Ball" constructor (see ball class)

        random = new Random(); //the "Random" class is used so the ball starts in a random direction in the beginning
        ball = new Ball(WINDOW_WIDTH / 2, (int)WINDOW_HEIGHT / 2, BALL_DIAMETER,random.nextInt(2) * 2 - 1, random.nextInt(2) * 2 - 1, player1, player2 );

    }

    private void createAvatars() {
        GameAvatar selectedPlayer1 = new GameAvatar(selectedImagePlayer1, 20.0, 20.0, AVATAR_HEIGHT, root);
        GameAvatar selectedPlayer2 = new GameAvatar(selectedImagePlayer2, 20.0, 20.0, AVATAR_HEIGHT, root);
    }


    private static void startCountdown(int duration, Ball ball, GraphicsContext gc, int fontSize, int fontXPos, int fontYPos) { //siehe Visual Countdown Klasse
        countdown = new VisualCountdown(duration, ball, gc, fontSize, fontXPos, fontYPos);
        countdown.countdownLogic();
    }


    private void createPauseScreen() {
        Music.mediaPlayer2.pause(); //pausing the game music so -->
        Music.mediaPlayer1.play(); //--> the menu music can start again
        tl.pause(); //pausing the timeline so the game can be continued exactly where it was left when the pause screen is opened
        PauseScreen pauseScreen = new PauseScreen(WINDOW_WIDTH, (int) WINDOW_HEIGHT, currentStage, this) { //pause screen is created with the constructor of the "PauseScreen" class
        };
    }


    @Override
    public void endingPauseScreen() { //method that is executed when the switch from the pause screen back to the game happens
        System.out.println("Ending PauseScreen");
        Music.mediaPlayer1.stop(); //stop the menu music -->
        Music.mediaPlayer2.play(); //--> so the game music can continue
        tl.play(); //timeline continues (the indefinite loop)
        currentStage.show(); //showing the stage
        currentStage.setScene(gameScene); // setting the scene for the game

    }



    // Functions for the victory screen, when the game is over
    private void showVictoryScreen(String winnerText, Image winnerImage, Image loserImage) {
        Label victoryLabel = new Label(winnerText);
        victoryLabel.setStyle("-fx-background-color: transparent; -fx-padding: 100px; -fx-font-size: 48px; -fx-text-fill: white;"); //set the font size to 24px
        Image winnerBackground = GameMenu.loadImage("Background_FINALE.jpg");
        ImageView winnerBackgroundView = new ImageView(winnerBackground);

        Button backButton = new Button("Back to Game Menu");
        backButton.setOnAction(event -> goToGameMenu());
        GameMenu.addGlowEffectOnHover(backButton);
        backButton.setStyle("-fx-font-size: 18px; -fx-background-color: white"); //set the font size to 18px

        root.getChildren().clear();
        secondRoot = new AnchorPane();
        GameAvatar winnerAvatar = new GameAvatar(winnerImage, 20.0, 20.0, AVATAR_HEIGHT, secondRoot);
        GameAvatar loserAvatar = new GameAvatar(loserImage, 20.0, 20.0, AVATAR_HEIGHT, root);


        VBox victoryContent = new VBox(); //increased spacing between nodes
        victoryContent.setAlignment(Pos.CENTER);
        victoryContent.getChildren().addAll(victoryLabel, backButton);

        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); //semi-transparent black background

        // StackPane to overlay on top of the gameCanvas
        StackPane victoryRoot = new StackPane();
        victoryRoot.getChildren().addAll(winnerBackgroundView, gameCanvas, root, overlay, secondRoot, victoryContent);

        Scene victoryScene = new Scene(victoryRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        currentStage.setScene(victoryScene);
        currentStage.show();
    }


    private boolean gameOver() {
        if (score.scorePlayer1 == MAX_SCORE || score.scorePlayer2 == MAX_SCORE) {
            updateCanvas();
            tl.stop();

            if (score.scorePlayer1 == MAX_SCORE) {
                System.out.println("Player 1 Wins");
            } else {
                System.out.println("Player 2 Wins");
            }

            return true;
        } else {
            return false;
        }
    }


    private void goToGameMenu(){ //function for the back button at the victory screen
        GameMenu gameMenu = new GameMenu();
        gameMenu.start(currentStage);
    }



    // Functions for updating (the game scene)
    private void updateCanvas() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); //clear the canvas
        player1.draw(gc); //redraw player 1 paddle
        player2.draw(gc); //redraw player 2 paddle
        ball.draw(gc); //draw Ball
        score.draw(gc); //draw Score
    }


    private void updateScore() { //score conditions
        if (ball.getXPosBall() <= 0) { //when the ball is all to the left, player 2 gets a score
            score.scorePlayer2++;
            if (score.scorePlayer1 < MAX_SCORE && score.scorePlayer2 < MAX_SCORE) {
                createBall(); //ball is recreated in the middle of the screen and a countdown of 3 seconds is started
                startCountdown(3, ball, gc, 100, WINDOW_WIDTH / 2, (int) WINDOW_HEIGHT / 2);
            }
        }
        else if (ball.getXPosBall() >= WINDOW_WIDTH ) { //when the ball is all to the right, player 1 gets a score
            score.scorePlayer1++;


            if (score.scorePlayer1 < MAX_SCORE && score.scorePlayer2 < MAX_SCORE) {
                createBall(); //ball is recreated in the middle of the screen and a countdown of 3 seconds is started
                startCountdown(3, ball, gc, 100, WINDOW_WIDTH / 2, (int) WINDOW_HEIGHT / 2);
            }
        }
    }


    // Movement functions
    private void paddleMovement() {

        // Adding the key (value) of the pressed key to the "HashSets" from the players
        gameScene.setOnKeyPressed(event -> {
            KeyCode keyPressed = event.getCode(); //the pressed key is saved as "keyPressed"
            if (keyPressed == KeyCode.W || keyPressed == KeyCode.S|| keyPressed == KeyCode.ESCAPE) { //when the "keyPressed" is "W" or "S" then it is added to the "HashSet" of player 1
                keysPressedP1.add(keyPressed);
            } else if (keyPressed == KeyCode.UP || keyPressed == KeyCode.DOWN) { //when the "keyPressed" is "UP" or "DOWN" then it is added to the "HashSet" of player 2
                keysPressedP2.add(keyPressed);
            }
        });
        // Removing the key (value) of the pressed key of the "HashSets" from the players
        gameScene.setOnKeyReleased(event -> { //the released key is saved as "keyReleased"
            KeyCode keyReleased = event.getCode();
            if (keyReleased == KeyCode.W || keyReleased == KeyCode.S) {//when the "keyReleased" is "W" or "S" then it is added to the "HashSet" of player 1
                keysPressedP1.remove(keyReleased);
            } else if (keyReleased == KeyCode.UP || keyReleased == KeyCode.DOWN) {//when the "keyReleased" is "UP" or "DOWN" then it is added to the "HashSet" of player 2
                keysPressedP2.remove(keyReleased);
            }
        });

        // Boolean values decide if a paddle moves or not (they are true if the named value are added to the "HashSets")
        boolean moveP1Up = keysPressedP1.contains(KeyCode.W);
        boolean moveP1Down = keysPressedP1.contains(KeyCode.S);
        boolean moveP2Up = keysPressedP2.contains(KeyCode.UP);
        boolean moveP2Down = keysPressedP2.contains(KeyCode.DOWN);
        boolean gamePaused = keysPressedP1.contains(KeyCode.ESCAPE);

        if (moveP1Up && player1.getYPaddlePosition() >= 0) { //if the "W" key is pressed (and not released) and the paddle is not on top of the screen - move paddle up
            player1.setYDirection(-player1.getPaddleSpeed());
        } else if (moveP1Down && player1.getYPaddlePosition() <= WINDOW_HEIGHT - PADDLE_HEIGHT) { //if the "S" key is pressed (and not released) and the paddle is not on top of the screen - move paddle up
            player1.setYDirection(player1.getPaddleSpeed());
        } else {
            player1.setYDirection(0);
        }

        if (moveP2Up && player2.getYPaddlePosition() >= 0) { //same code as rows 145 - 153 (above) but for player 2
            player2.setYDirection(-player2.getPaddleSpeed());
        } else if (moveP2Down && player2.getYPaddlePosition() <= WINDOW_HEIGHT - PADDLE_HEIGHT) {
            player2.setYDirection(player2.getPaddleSpeed());
        } else {
            player2.setYDirection(0);
        }

        if (gamePaused){
            keysPressedP1.remove(KeyCode.ESCAPE);
            createPauseScreen();
        }
    }


    private void allMovement(){
        player1.move(); //see row 87 of the "Paddle" class
        player2.move();
        ball.move(); ////see rows 62 -95 of the "Ball" class
    }



    public static void main(String[] args) {
        launch();
    }



    // Placeholder
    private void run (GraphicsContext gc) {
    }



}

