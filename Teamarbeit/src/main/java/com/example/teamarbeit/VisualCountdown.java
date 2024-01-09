package com.example.teamarbeit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class VisualCountdown {
    public int countdownStartValue;
    public int currentCountdownValue;
    private Label countdownLabel;
    private Timeline tl;
    private GraphicsContext gc;
    private Ball ball;
    private double fontSize;
    private int fontXPos;
    private int fontYPos;
    public static boolean isCountdownStarted;



    VisualCountdown(int duration, Ball ball, GraphicsContext gc, double fontSize, int fontXPos, int fontYPos){
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
        tl = new Timeline(); // Timeline, damit Code wiederholt ausgeführt wird
        tl.setCycleCount(countdownStartValue); //Timeline wird so oft ausgeführt, wie die Dauer des Countdowns (5s countdown --> Timeline wird 5x ausgeführt)
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> { //Jede Sekunde kommt ein Keyframe/Update
            currentCountdownValue--; //Countdown wird um eins niedriger und gezeichnet
            drawCountdown(gc);
            if (currentCountdownValue <= 0 && tl != null) { //Wenn ein Countdown existiert und dieser 0 erreicht, wird die timeline gestoppt
                tl.stop();

                ball.gameSceneIsRunning = true; //Wenn Countdown 0 erreicht, darf der Ball sich bewegen
            }
        }));
        tl.play();
        isCountdownStarted = true;
    }
    public void drawCountdown(GraphicsContext gc) {
        gc.setFill(Color.TRANSPARENT); // Clear the countdown area with a background color
        gc.fillRect(fontXPos, fontYPos - fontSize, fontSize * 2, fontSize); // Adjust the dimensions as needed

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(fontSize));
        gc.fillText(Integer.toString(currentCountdownValue), fontXPos, fontYPos);

    }

}
