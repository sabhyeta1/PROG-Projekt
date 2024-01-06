package com.example.teamarbeit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.example.teamarbeit.HelloApplication.WINDOW_HEIGHT;
import static com.example.teamarbeit.HelloApplication.WINDOW_WIDTH;

public class VisualCountdown {
    private int countdownStartValue;
    private int currentCountdownValue;
    private Label countdownLabel;
    private Timeline tl;
    private GraphicsContext gc;
    private Ball ball;
    private double fontSize;
    private int fontXPos;
    private int fontYPos;



    VisualCountdown(int duration, Ball ball, GraphicsContext gc, double fontSize, int fontXPos, int fontYPos){
        this.countdownStartValue = duration;
        this.currentCountdownValue = duration;
        this.gc = gc;
        this.fontSize = fontSize;
        this.fontXPos = fontXPos;
        this.fontYPos = fontYPos;
        this.ball = ball;
        drawCountdown();
    }
    void countdownLogic() {
        tl = new Timeline(); // Timeline, damit Code wiederholt ausgeführt wird
        tl.setCycleCount(countdownStartValue); //Timeline wird so oft ausgeführt, wie die Dauer des Countdowns (5s countdown --> Timeline wird 5x ausgeführt)
        tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> { //Jede Sekunde kommt ein Keyframe/Update
            currentCountdownValue--; //Countdown wird um eins niedriger und gezeichnet
            drawCountdown();
            if (currentCountdownValue <= 0 && tl != null) { //Wenn ein Countdown existiert und dieser 0 erreicht, wird die timeline gestoppt
                tl.stop();

                ball.gameSceneIsRunning = true; //Wenn Countdown 0 erreicht, darf der Ball sich bewegen
            }
        }));
        tl.play();
    }
    private void drawCountdown(){
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(fontSize)); // Übernehme die übergebene Font Größe
        gc.fillText(Integer.toString(currentCountdownValue), fontXPos, fontYPos);
    }

}
