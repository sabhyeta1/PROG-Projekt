package com.example.teamarbeit;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import static com.example.teamarbeit.Avatars.selectedImagePlayer1;
import static com.example.teamarbeit.Avatars.selectedImagePlayer2;




import java.io.IOException;
import java.util.Set;


public class HelloApplication extends Application implements ExitPause{
    static Stage currentStage;
    Scene scene1, gameScene;
    Button button;
    static final int WINDOW_WIDTH = 1000;
    static final double WINDOW_HEIGHT = WINDOW_WIDTH * (0.666666666);

    static final int PADDLE_WIDTH = 25;
    static final int MAX_SCORE = 2;
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
    public GameMenu switchToGameMenu;
    boolean gameStarted;

    Thread thread;
    Image image;
    GraphicsContext graphics;
    Random random;
    Paddle player1;
    Paddle player2;
    Ball ball;
    Score score;
    static VisualCountdown countdown;
    GraphicsContext gc;
    Canvas gameCanvas;
    Timeline tl;
    private final Set<KeyCode> keysPressedP1 = new HashSet<>(); //HashSet für Input Player 1
    private final Set<KeyCode> keysPressedP2 = new HashSet<>(); //HashSet für Input Player 2
    public static MediaPlayer mediaPlayer1 ,mediaPlayer2, mediaPlayer3;




    @Override
    public void start(Stage primaryStage) throws IOException {


        currentStage = primaryStage; //Stage wird übernommen
        currentStage.setTitle("Play"); //Stage (Fenster) bekommt Titel Pong Projekt (Ist oben links zu sehen)

        GameMenu.mediaPlayer1.stop();
        //Create gameCanvas to draw our Game

        score = new Score(WINDOW_WIDTH,(int) WINDOW_HEIGHT); //Punkteanzahl wird erstellt

        gameCanvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT); //Ein Canvas wird erstellt (Ein Canvas ist wie eine Szene, nur können dort Objekte wie Rechtecke oder Kreise gezeichnet werden)
        gc = gameCanvas.getGraphicsContext2D(); //unser Zeichenobjekt wird als gc gespeichert, so können wir Funktionen ausführen, die das Objekt betreffen (z.B. Farbe, Größe etc. ändern).




        StackPane gcRoot = new StackPane(gameCanvas); //noch ein Layout wird erstellt mit StackPane
        gameScene = new Scene(gcRoot, WINDOW_WIDTH, WINDOW_HEIGHT); //neue Szene wird erstellt mit gcRoot und größe WINDOW_WIDTH X WINDOW_HEIGHT
        gcRoot.setStyle("-fx-background-color: black;"); // Set the background color
        createPaddles();
        createBall();
        currentStage.setOnCloseRequest(windowEvent -> { // Sobald Fenster geschlossen wird, stoppe die Hintergrundmusik, falls schon vorhanden
            if (mediaPlayer2 != null) {
                mediaPlayer2.stop();
            }
        });




        currentStage.setResizable(false); //Fenstergröße bleibt fix, kann nicht verändert werden vom Endbenutzer
        currentStage.setScene(scene1); // Set gameScene as the initial scene
        currentStage.show(); //Stage wird angezeigt
        currentStage.setScene(gameScene); //Wenn Knopf gedrückt wird, wechsel auf gameScene und stelle gameSceneIsRunning auf true




        tl = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            paddleMovement(); //Timeline updatet diese Methoden konstant alle 10 ms --> 100FPS
            allMovement();
            updateCanvas();
            updateScore();
            if (countdown.currentCountdownValue >0 && countdown != null){
                countdown.drawCountdown(gc);
            }
            gameOver();
            if (gameOver()) { //Code für Victory Screen und zurück ins GameMenu einfügen
                if (score.scorePlayer1 == MAX_SCORE) {
                    showVictoryScreen("Player 1 Wins!");
                    mediaPlayer2.stop();
                    /*Label victoryPlayer1 = new Label("Player 1 Wins!");
                    victoryPlayer1.setStyle("-fx-background-color: white; -fx-padding: 10px;");
                    StackPane victoryLabel = new StackPane();
                    victoryLabel.setStyle("-fx-background-color: black;");
                    victoryLabel.getChildren().addAll(gameCanvas, victoryPlayer1);
                    Scene victoryScene = new Scene(victoryLabel, WINDOW_WIDTH, WINDOW_HEIGHT);
                    currentStage.setScene(victoryScene);
                    currentStage.show();
                    */
                }
                else {
                    showVictoryScreen("Player 2 Wins!");
                    mediaPlayer2.stop();
                    /*Label victoryPlayer2 = new Label("Player 2 Wins!");
                    victoryPlayer2.setStyle("-fx-background-color: white; -fx-padding: 10px;");
                    StackPane victoryLabel = new StackPane();
                    victoryLabel.setStyle("-fx-background-color: black;");
                    victoryLabel.getChildren().addAll(gameCanvas, victoryPlayer2);
                    Scene victoryScene = new Scene(victoryLabel, WINDOW_WIDTH, WINDOW_HEIGHT);
                    currentStage.setScene(victoryScene);
                    currentStage.show();

                */}

            }
        }));


        tl.setCycleCount(Timeline.INDEFINITE); //Timeline wird für immer laufen bzw. wird indefinite Mal ausgeführt
        createPaddles(); //Für Methoden für Zeile 79 bis 84 siehe Unten
        createBall();
        startCountdown(5, ball, gc, 100, WINDOW_WIDTH / 2, (int)WINDOW_HEIGHT / 2);
        updateCanvas();
        updateScore();
        playGameMusic("C:\\Users\\lenovo\\IdeaProjects5\\PROG-Projekt\\Teamarbeit\\src\\main\\resources\\com.example.teamarbeit\\children-electro-swing-2_medium-178290.mp3");
        tl.play(); //Starte Timeline

        gameCanvas.requestFocus(); //Sicherheitsvorkehrung damit gameCanvas unsere Keyboard Inputs annehmen kann, weil es jetzt in Fokus ist
    }
    private void createPaddles() { //Erstelle 2 Paddles mit Konstruktor "Paddle" --> siehe Zeile 16 - 23 von Klasse Paddle
        player1 = new Paddle(0, (double) ((WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2)), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        player2 = new Paddle(WINDOW_WIDTH - PADDLE_WIDTH, (double) ((WINDOW_HEIGHT / 2) - (PADDLE_HEIGHT / 2)), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }
    private void paddleMovement() {
        gameScene.setOnKeyPressed(event -> {
            KeyCode keyPressed = event.getCode(); //Gedrückte Taste wird als keyPressed gespeichert
            if (keyPressed == KeyCode.W || keyPressed == KeyCode.S|| keyPressed == KeyCode.ESCAPE) { //Wenn keyPressed W oder S ist, dann füge es ins HashSet von Player 1 hinzu
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
        boolean gamePaused = keysPressedP1.contains(KeyCode.ESCAPE);

        if (gamePaused){
            keysPressedP1.remove(KeyCode.ESCAPE);
            createPauseScreen();
        }

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

    }

    private void createBall() { //Ball wird erstellt mit "Ball" Konstruktor, siehe Ball Klasse

        random = new Random(); //Random damit der Ball in eine zufällige Anfangsrichtung geht
        ball = new Ball(WINDOW_WIDTH / 2, (int)WINDOW_HEIGHT / 2, BALL_DIAMETER,random.nextInt(2) * 2 - 1, random.nextInt(2) * 2 - 1, player1, player2 );

    }

    private void updateCanvas() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); // Clear the canvas
        player1.draw(gc); // Redraw player 1 paddle
        player2.draw(gc); // Redraw player 2 paddle
        ball.draw(gc); // Draw Ball
        score.draw(gc); // Draw Score
    }
    private void updateScore() { // Score Bedingungen
        if (ball.getXPosBall() <= 0) { // Wenn Ball ganz links, dann bekommt Spiele 2 einen Punkt
            score.scorePlayer2++;
            if (score.scorePlayer1 < MAX_SCORE && score.scorePlayer2 < MAX_SCORE) {
                createBall(); //Ball soll wieder in der Mitte erstellt werden und Countdown von 3 Sek wird gestartet
                startCountdown(3, ball, gc, 100, WINDOW_WIDTH / 2, (int) WINDOW_HEIGHT / 2);
            }
        }
        else if (ball.getXPosBall() >= WINDOW_WIDTH ) { //Wenn Ball ganz rechts, bekommt Spieler 1 einen Punkt
            score.scorePlayer1++;


            if (score.scorePlayer1 < MAX_SCORE && score.scorePlayer2 < MAX_SCORE) {
                createBall();
                startCountdown(3, ball, gc, 100, WINDOW_WIDTH / 2, (int) WINDOW_HEIGHT / 2);
            }
        }
    }

    private static void startCountdown(int duration, Ball ball, GraphicsContext gc, int fontSize, int fontXPos, int fontYPos) { //siehe Visual Countdown Klasse
        countdown = new VisualCountdown(duration, ball, gc, fontSize, fontXPos, fontYPos);
        countdown.countdownLogic();
    }

    private void allMovement(){
        player1.move(); //siehe Klasse Paddle Zeile 87
        player2.move();
        ball.move(); //siehe Klasse Ball Zeile 62 - 95
    }

    public static void playGameMusic(String filePath) {
        Media backgroundMusic = new Media(new File(filePath).toURI().toString());
        mediaPlayer2 = new MediaPlayer(backgroundMusic);
        mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE); //Musik soll unendlich lang geloopt werden
        mediaPlayer2.setVolume(GameMenu.getSliderValue(GameMenu.getMusicSlider())); //50% Lautstärke
        mediaPlayer2.play();
        mediaPlayer2.setOnError(() -> {
            System.out.println("Media error occurred: " + mediaPlayer1.getError());
            //Error code, falls iwann einer kommt
        });
    }
    public static void playBounceSound(String filePath) { //Methode für Ballsound, wenn er gegen Paddle abprallt
        Media bounceSound = new Media(new File(filePath).toURI().toString());
        mediaPlayer3 = new MediaPlayer(bounceSound);
        mediaPlayer3.setCycleCount(1); //Sound wird nur einmal abgespielt
        mediaPlayer3.play();
    }
    private void showVictoryScreen(String winnerText) {
        Label victoryLabel = new Label(winnerText);
        victoryLabel.setStyle("-fx-background-color: white; -fx-padding: 10px;");

        Button backButton = new Button("Back to Game Menu");
        backButton.setOnAction(event -> {
            goToGameMenu();
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(victoryLabel, backButton, gameCanvas);

        StackPane victoryLayout = new StackPane();
        victoryLayout.setStyle("-fx-background-color: black;");
        victoryLayout.getChildren().add(vbox); // Add VBox to StackPane

        Scene victoryScene = new Scene(victoryLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
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

    private void createPauseScreen() {
        mediaPlayer2.stop();
        GameMenu.mediaPlayer1.play();
        tl.pause();
        PauseScreen pauseScreen = new PauseScreen(WINDOW_WIDTH, (int) WINDOW_HEIGHT, currentStage, this) {
        };
    }

    private void goToGameMenu(){
        GameMenu gameMenu = new GameMenu();
        gameMenu.start(currentStage);
    }


    private void run (GraphicsContext gc) {

    }




    public static void main(String[] args) {
        launch();
    }

    @Override
    public void endingPauseScreen() {
        System.out.println("Ending PauseScreen");
        GameMenu.mediaPlayer1.stop();
        mediaPlayer2.play();
        tl.play();
        currentStage.show();
        currentStage.setScene(gameScene);

    }


}