package com.example.teamarbeit;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;




public class Score {

    // Instances of the class
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int scorePlayer1;
    int scorePlayer2;



    // Constructor of the class "Score"
    Score(int GAME_WIDTH, int GAME_HEIGHT){

        // Initializing instances
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }



    // Function for drawing the score
    public void draw(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Quicksand Bold",60)); //font is "Quicksand Bold" and size is 60

        gc.fillText(String.format("%02d", scorePlayer1), ((double) GAME_WIDTH / 2) - 120, 75);
        gc.fillText(String.format("%02d", scorePlayer2), ((double) GAME_WIDTH / 2) + 60, 75);
    }
}
