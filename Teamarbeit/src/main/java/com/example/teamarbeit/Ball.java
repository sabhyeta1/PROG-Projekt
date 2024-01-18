package com.example.teamarbeit;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static com.example.teamarbeit.GameLogik.*;




public class Ball {
    // Instances of class
    private final Paddle player1;
    private final Paddle player2;
    int xBallVelocity;
    int yBallVelocity;
    private int xPosBall;
    private int yPosBall;
    private final int BALL_DIAMETER;
    private double ballSpeed = 2;
    public boolean gameSceneIsRunning = false;
    public static String mediaPlayer3Path = "./Teamarbeit/src/main/resources/com.example.teamarbeit/single_bounce.mp3";
    public static Media bounceSound = new Media(new File(mediaPlayer3Path).toURI().toString());
    public static MediaPlayer mediaPlayer3 = new MediaPlayer(bounceSound);



    // Constructor of the class "Ball"
    Ball(int xPosBall, int yPosBall, int ballDiameter, int initialXVelocity, int initialYVelocity, Paddle player1, Paddle player2){ //player 1 and player 2 are handed over so the ball can interact with the paddle

        // Initializing instances
        this.xPosBall = xPosBall;
        this.yPosBall = yPosBall;
        this.BALL_DIAMETER = ballDiameter;
        this.player1 = player1;
        this.player2 = player2;


        setXDirection(initialXVelocity);
        setYDirection(initialYVelocity);
    }



    // Setter and getter functions
    public int getXPosBall () {
        return xPosBall;
    }
    public int getYPosBall (){
        return yPosBall;
    }

    public void setXDirection(int xDirection) {
        xBallVelocity = xDirection;
    }
    public void setYDirection(int yDirection) {
        yBallVelocity = yDirection;
    }



    // Function for the movement of the ball
    public void move () {
        if (gameSceneIsRunning) { //ball does not start moving until "gameScene" is running

            xPosBall += (int) (xBallVelocity * ballSpeed); //adds "Velocity" multiplied with the pace to the position
            yPosBall += (int) (yBallVelocity * ballSpeed);

            // Function for mirroring the direction of the ball on the y-axis when he bounces of the top or bottom wall
            if (getYPosBall() <= 0 || getYPosBall() >= WINDOW_HEIGHT - BALL_DIAMETER) {
                yBallVelocity = -yBallVelocity;
            }

            // Check for collisions with player 1
            if (xPosBall <= PADDLE_WIDTH && yPosBall >= player1.getYPaddlePosition() && yPosBall <= player1.getYPaddlePosition() + PADDLE_HEIGHT && xBallVelocity < 0) {
                // Function for when ball bounces of the paddle a sound is played
                mediaPlayer3.seek(mediaPlayer3.getStartTime());
                Music.playBounceSound(mediaPlayer3);
                xBallVelocity = -xBallVelocity; //change horizontal direction
                if (ballSpeed <= 7) { //speed is only increased to the value 7
                    ballSpeed = ballSpeed + 0.5;
                }

            }

            // Check for collisions with player 2
            if (xPosBall >= WINDOW_WIDTH - PADDLE_WIDTH - BALL_DIAMETER && yPosBall >= player2.getYPaddlePosition() && yPosBall <= player2.getYPaddlePosition() + PADDLE_HEIGHT && xBallVelocity > 0) {
                mediaPlayer3.seek(mediaPlayer3.getStartTime());
                Music.playBounceSound(mediaPlayer3);
                xBallVelocity = -xBallVelocity;//change horizontal direction
                if (ballSpeed <= 7) {
                    ballSpeed = ballSpeed + 0.5;
                }
            }
        }
    }



    // Function to "draw" the ball to the game scene (parameter) and give it a color
    public void draw (GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.fillOval(xPosBall, yPosBall, BALL_DIAMETER, BALL_DIAMETER);
    }
}
