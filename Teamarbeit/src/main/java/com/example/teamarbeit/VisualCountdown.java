package com.example.teamarbeit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;




public class VisualCountdown {

    // Instances of the class
    public int countdownStartValue;
    public int currentCountdownValue;
    private Timeline tl;
    private GraphicsContext gc;
    private Ball ball;
    private double fontSize;
    private int fontXPos;
    private int fontYPos;
    public static boolean isCountdownStarted;



    // Constructor of the class "VisualCountdown"
    VisualCountdown(int duration, Ball ball, GraphicsContext gc, double fontSize, int fontXPos, int fontYPos) {

        // Initializing instances
        this.countdownStartValue = duration;
        this.currentCountdownValue = duration;
        this.gc = gc;
        this.fontSize = fontSize;
        this.fontXPos = fontXPos;
        this.fontYPos = fontYPos;
        this.ball = ball;
        isCountdownStarted = false;
        drawCountdown(gc);
    }



    void countdownLogic() {
        tl = new Timeline(); //create a timeline so the execution of the code can be repeated
        tl.setCycleCount(countdownStartValue); //timeline is executed as many times as the countdown needs to be finished (5s countdown --> Timeline is executed 5 times)
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> { //every second comes a keyframe/update
            currentCountdownValue--; //countdown decreases by one and is drawn
            drawCountdown(gc);
            if (currentCountdownValue <= 0 && tl != null) { //if a countdown exists, and it reaches the value 0, the timeline is topped
                tl.stop();

                ball.gameSceneIsRunning = true; //when countdown reached value 0 the ball can move
            }
        }));
        tl.play();
        isCountdownStarted = true;
    }



    // Function for drawing the countdown
    public void drawCountdown(GraphicsContext gc) {
        gc.setFill(Color.TRANSPARENT); //clear the countdown area with a background color
        gc.fillRect(fontXPos, fontYPos - fontSize, fontSize * 2, fontSize); //adjust the dimensions as needed

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(fontSize));
        gc.fillText(Integer.toString(currentCountdownValue), fontXPos, fontYPos);
    }



}
