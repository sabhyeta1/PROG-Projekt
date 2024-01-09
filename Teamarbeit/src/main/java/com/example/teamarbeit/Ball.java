package com.example.teamarbeit;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static com.example.teamarbeit.HelloApplication.*;

public class Ball {
    private final Paddle player1;
    private final Paddle player2;
    Random random;
    int xBallVelocity;
    int yBallVelocity;
    private int xPosBall;
    private int yPosBall;
    private final int BALL_DIAMETER;
    private double ballSpeed = 2;
    public boolean gameSceneIsRunning = false;

    Ball(int xPosBall, int yPosBall, int ballDiameter, int initialXVelocity, int initialYVelocity, Paddle player1, Paddle player2){ //player1 und player2 werden übergeben, damit der Ball mit Paddle interagieren kann
        this.xPosBall = xPosBall;
        this.yPosBall = yPosBall;
        this.BALL_DIAMETER = ballDiameter;
        this.player1 = player1;
        this.player2 = player2;



        setXDirection(initialXVelocity);
        setYDirection(initialYVelocity);
    }
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
    public void move () {
        if (gameSceneIsRunning) { //ball bewegt sich erst, wenn gameScene läuft

            xPosBall += (int) (xBallVelocity * ballSpeed); //Gib zur Position die "Velocity multipliziert mit Geschwindigkeit" dazu
            yPosBall += (int) (yBallVelocity * ballSpeed);

            //Ballrichtung wird an y-Achse gespiegelt, wenn er obere oder untere Wand trifft
            if (getYPosBall() <= 0 || getYPosBall() >= WINDOW_HEIGHT - BALL_DIAMETER) {
                yBallVelocity = -yBallVelocity;
            }

            // Check for collisions with player 1
            if (xPosBall <= PADDLE_WIDTH && yPosBall >= player1.getYPaddlePosition() && yPosBall <= player1.getYPaddlePosition() + PADDLE_HEIGHT && xBallVelocity < 0) {
                xBallVelocity = -xBallVelocity;// Change horizontal direction
                // Wenn Ball an Paddle abprallt, spiele Sound ab
                playBounceSound("C:\\Users\\marti\\IdeaProjects\\PROG-Projekt1\\Teamarbeit\\src\\main\\resources\\com.example.teamarbeit\\single_bounce.mp3");
                if (ballSpeed <= 7) { // Geschwindigkeit wird nur erhöht bis 7
                    ballSpeed = ballSpeed + 0.5;
                }

            }

            // Check for collisions with player 2
            if (xPosBall >= WINDOW_WIDTH - PADDLE_WIDTH - BALL_DIAMETER && yPosBall >= player2.getYPaddlePosition() && yPosBall <= player2.getYPaddlePosition() + PADDLE_HEIGHT && xBallVelocity > 0) {
                xBallVelocity = -xBallVelocity;// Change horizontal direction
                playBounceSound("C:\\Users\\marti\\IdeaProjects\\PROG-Projekt1\\Teamarbeit\\src\\main\\resources\\com.example.teamarbeit\\single_bounce.mp3");
                if (ballSpeed <= 7) {
                    ballSpeed = ballSpeed + 0.5;
                }
            }



        }
    }
    public void draw (GraphicsContext gc){
        gc.setFill(Color.WHITE);
        gc.fillOval(xPosBall, yPosBall, BALL_DIAMETER, BALL_DIAMETER);
    }
}
