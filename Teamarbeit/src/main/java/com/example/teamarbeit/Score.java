package com.example.teamarbeit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Score {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int scorePlayer1;
    int scorePlayer2;

    Score(int GAME_WIDTH, int GAME_HEIGHT){
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Quicksand Bold",60)); //Zeichne Score mit Font "Quicksand Bold" und Größe 60

        gc.fillText(String.format("%02d", scorePlayer1), ((double) GAME_WIDTH / 2) - 120, 75);
        gc.fillText(String.format("%02d", scorePlayer2), ((double) GAME_WIDTH / 2) + 60, 75);
    }
}
