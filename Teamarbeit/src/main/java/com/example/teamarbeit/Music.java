package com.example.teamarbeit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;




public class Music {

    // The instances of the class
    static Slider musicSlider;
    static Slider gameSoundSlider; //static because the function getGameSoundSlider is static
    // the default Music volume has a value of 50
    public static double gameSoundSliderValue = 50.0;
    public static double musicSliderValue = 50.0;

    public static MediaPlayer mediaPlayer1, mediaPlayer2;



    // Functions for sliders
    public static void createSettingsSliders(){
        gameSoundSlider = createSlider();
        musicSlider = createSlider();

        //Row 173 - 179: for saving the values of the sliders when the settings screen is closed
        gameSoundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameSoundSliderValue = newValue.doubleValue();
            setVolume(); //call the function "setVolume()" when gameSoundSlider's value changes
        });

        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            musicSliderValue = newValue.doubleValue();
            setVolume(); //call the function "setVolume()" when musicSlider's value changes
        });
    }



    public static Slider createSlider() {
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(50);
        slider.setOrientation(Orientation.HORIZONTAL);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        return slider;
    }


    // Getter and setter functions (for sliders)
    public static Slider getGameSoundSlider() {
        return gameSoundSlider;
    }


    public static Slider getMusicSlider() {
        return musicSlider;
    }


    public static double getSliderValue(Slider inputSlider){
        if(inputSlider != null) {
            return inputSlider.getValue() /100;
        }
        else return 0.5;
    }



    // Function for setting the volume of the music to the chosen value
    public static void setVolume() {
        if (mediaPlayer1 != null) {
            mediaPlayer1.setVolume(getSliderValue(getMusicSlider()));
            System.out.println("Ändert menuMusic Läutstärke");
        }
        if (mediaPlayer2 != null) {
            mediaPlayer2.setVolume(getSliderValue(getMusicSlider()));
            System.out.println("Ändert gameMusic Läutstärke");
        }
        if (Ball.mediaPlayer3 != null) {
            System.out.println("Ändert bounceSound Lautstärke");
            Ball.mediaPlayer3.setVolume(getSliderValue(getGameSoundSlider()));
        }
    }



    // Functions for starting the background musics on the updated volume
    public static void playGameMusic(String filePath) { //function for game music
        Media backgroundMusic = new Media(new File(filePath).toURI().toString());
        mediaPlayer2 = new MediaPlayer(backgroundMusic);
        mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE); //music should be repeated in an indefinite loop
        mediaPlayer2.setVolume(getSliderValue(getMusicSlider())); //50% of the volume
        mediaPlayer2.play();
        mediaPlayer2.setOnError(() -> {
            System.out.println("Media error occurred: " + mediaPlayer1.getError());
            //Error code, falls iwann einer kommt
        });
        Media ballMusic = new Media(new File(Ball.mediaPlayer3Path).toURI().toString());
        Ball.mediaPlayer3 = new MediaPlayer(ballMusic);
        Ball.mediaPlayer3.setVolume(getSliderValue(getGameSoundSlider())); //50% of the volume
    }


    public static void playMenuMusic(String filePath) { //function for menu music
        Media backgroundMusic = new Media(new File(filePath).toURI().toString());
        mediaPlayer1 = new MediaPlayer(backgroundMusic);
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE); //music should be repeated in an indefinite loop
        if(mediaPlayer1 != null){
            mediaPlayer1.setVolume(getSliderValue(musicSlider));
        }
        else {
            mediaPlayer1.setVolume(0.50); //50% of the volume
        }
        mediaPlayer1.play();
        mediaPlayer1.setOnError(() -> {
            System.out.println("Media error occurred: " + mediaPlayer1.getError());
            //Error code, if an error appears
        });
    }



    // Placeholders
    public static void reduceVolumeGradually() { //Fade away von menu music (when "Start The Game" button is pressed)
        Timeline volumeReductionTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0.1), event -> {

                    if (mediaPlayer1.getVolume() > 0) {
                        double currentVolume = mediaPlayer1.getVolume();
                        mediaPlayer1.setVolume(currentVolume - 0.05); // Adjust decrement value as needed
                    } else {
                        mediaPlayer1.stop();
                    }

                })
        );
        volumeReductionTimeline.setCycleCount(Timeline.INDEFINITE);
        volumeReductionTimeline.play();
    }



}
