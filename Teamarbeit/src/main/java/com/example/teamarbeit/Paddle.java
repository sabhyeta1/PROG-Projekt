package com.example.teamarbeit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;




public class Paddle {

    // Instances of the class
    private int playerID;
    private int yPaddleVelocity;
    private double xPaddlePosition;
    double yPaddlePosition;
    private int paddleWidth;
    private int paddleHeight;
    private int paddleSpeed;



    // Constructor of the class "Paddle"
    public Paddle(double xPaddlePosition, double yPaddlePosition, int paddleWidth, int paddleHeight, int playerID) {

        // Initializing instances
        this.xPaddlePosition = xPaddlePosition;
        this.yPaddlePosition = yPaddlePosition;
        this.paddleWidth = paddleWidth;
        this.paddleHeight = paddleHeight;
        this.playerID = playerID;
        this.paddleSpeed = 5;
    }



    // Getters and Setters
    public int getPaddleSpeed() {
        return paddleSpeed;
    }


    public void setYDirection(int yDirection) {
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

    public int getYPaddleVelocity() {
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
        return paddleHeight;
    }

    public void setPaddleHeight(int paddleHeight) {
        this.paddleHeight = paddleHeight;
    }

    public void setPaddleSpeed(int paddleSpeed) {
        this.paddleSpeed = paddleSpeed;
    }



    // Function to move the paddle
    public void move() {
        yPaddlePosition += yPaddleVelocity;
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
