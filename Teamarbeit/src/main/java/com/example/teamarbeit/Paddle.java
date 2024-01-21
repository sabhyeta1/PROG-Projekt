package com.example.teamarbeit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Paddle {

    // Instances of the class
    private int playerID;
    private double yPaddleVelocity;
    private double xPaddlePosition;
    double yPaddlePosition;
    private int paddleWidth;
    private int paddleHeight;
    private double paddleSpeed = 2.5;



    // Constructor of the class "Paddle"
    public Paddle(double xPaddlePosition, double yPaddlePosition, int paddleWidth, int paddleHeight, int playerID) {

        // Initializing instances
        this.playerID = playerID;
        this.xPaddlePosition = xPaddlePosition;
        this.yPaddlePosition = yPaddlePosition;
        this.paddleWidth = paddleWidth;
        this.paddleHeight = paddleHeight;

        if (playerID == 2){
            this.paddleHeight = (int) (GameLogic.PADDLE_HEIGHT * 1.2);
            this.paddleSpeed = 0.9 * paddleSpeed;
        }
        if (playerID == 3){
            this.paddleHeight = (int) (GameLogic.PADDLE_HEIGHT * 1.5);
            this.paddleSpeed = 0.7 * paddleSpeed;
        }
        if (playerID == 4){
            this.paddleHeight = (int) (GameLogic.PADDLE_HEIGHT * 0.8);
            this.paddleSpeed = 1.3 * paddleSpeed;
        }
    }



    // Getters and Setters
    public double getPaddleSpeed() {
        return paddleSpeed;
    }


    public void setYDirection(double yDirection) {
        this.yPaddleVelocity = yDirection;
    }


    public double getYPaddlePosition() {
        return yPaddlePosition;
    }


    // Placeholders
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public double getYPaddleVelocity() {
        return yPaddleVelocity;
    }

    public void setYPaddleVelocity(int yPaddleVelocity) {
        this.yPaddleVelocity = yPaddleVelocity;
    }

    public double getXPaddlePosition() {
        return xPaddlePosition;
    }

    public void setXPaddlePosition(double xPaddlePosition) {
        this.xPaddlePosition = xPaddlePosition;
    }

    public void setYPaddlePosition(double yPaddlePosition) {
        this.yPaddlePosition = yPaddlePosition;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public void setPaddleWidth(int paddleWidth) {
        this.paddleWidth = paddleWidth;
    }

    public int getPaddleHeight() {
        return this.paddleHeight;
    }

    public void setPaddleHeight(int paddleHeight) {
        this.paddleHeight = paddleHeight;
    }

    public void setPaddleSpeed(int paddleSpeed) {
        this.paddleSpeed = paddleSpeed;
    }



    // Function to move the paddle
    public void move() {
        yPaddlePosition += (yPaddleVelocity * paddleSpeed);
    }



    // Function to draw the paddle
    public void draw (GraphicsContext gc) {
        if (playerID == 1){
            gc.setFill(Color.PALEGREEN);
        }
        else if (playerID == 2){
            gc.setFill(Color.HOTPINK);
        }
        else if (playerID == 3){
            gc.setFill(Color.DEEPSKYBLUE);
        }
        else if (playerID == 4){
            gc.setFill(Color.DARKORCHID);
        }
        else {
            gc.setFill(Color.YELLOW);
        }
        gc.fillRect(xPaddlePosition, yPaddlePosition, paddleWidth, paddleHeight);
    }



}
