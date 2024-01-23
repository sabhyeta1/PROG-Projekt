package com.example.teamarbeit;


import javafx.geometry.Orientation;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

//learned how to use Mediaplayer at https://stackoverflow.com/questions/52024412/javafx-media-loop & https://www.javatpoint.com/javafx-playing-video




public class Music {

    // The instances of the class
    static Slider musicSlider;
    static Slider gameSoundSlider; //static because the function getGameSoundSlider is static
    // the default Music volume has a value of 50
    public static double gameSoundSliderValue = 50.0; //default value set at 50
    public static double musicSliderValue = 50.0; //default value set at 50
    public static MediaPlayer mediaPlayer1, mediaPlayer2, mediaPlayer3; //static so other class can easily access this instances



    // Functions for sliders
    public static void createSettingsSliders(){ //creates the sliders and sets their settings
        gameSoundSlider = createSlider(); //slider for the sound during the game
        musicSlider = createSlider(); //slider for sound when the menu or the pause screen is open

        //Row 34 - 42: for saving the values of the sliders when the settings screen is closed
        gameSoundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameSoundSliderValue = newValue.doubleValue();
            setVolume(); //call the function "setVolume()" when gameSoundSlider's value changes
        });

        musicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            musicSliderValue = newValue.doubleValue();
            setVolume(); //call the function "setVolume()" when musicSlider's value changes
        });
    }



    public static Slider createSlider() { //method to create sliders to regulate the volume of the music players
        Slider slider = new Slider(); //instance is created
        slider.setMin(0); //minimum value
        slider.setMax(100); //maximum value
        slider.setValue(50); //the default value that is set for the slider
        slider.setOrientation(Orientation.HORIZONTAL); //the slider is presented horizontal
        //settings for moving the sliders

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


    public static double getSliderValue(Slider inputSlider){ //gets the slider value and sets it to 0.5 if there is no value
        if(inputSlider != null) {
            return inputSlider.getValue() /100;
        }
        else return 0.5;
    }



    // Function for setting the volume of the music to the chosen value
    public static void setVolume() { //sets volume for each "mediaplayer" (every imported mp3 - file)
        if (mediaPlayer1 != null) { //only sets volume if "mediaPlayer1" is initialized
            mediaPlayer1.setVolume(getSliderValue(getMusicSlider())); //volume is set on the same value as its slider
            System.out.println("MenuMusic Läutstärke wird angepasst"); //text on console
        }
        if (mediaPlayer2 != null) { //only sets volume if "mediaPlayer2" is initialized
            mediaPlayer2.setVolume(getSliderValue(getMusicSlider())); //volume is set on the same value as its slider
            System.out.println("GameMusic Läutstärke wird angepasst"); //text on console
        }
        if (mediaPlayer3 != null) { //only sets volume if "mediaPlayer3" is initialized
            System.out.println("BounceSound Läutstärke wird angepasst"); //text on console
            mediaPlayer3.setVolume(getSliderValue(getGameSoundSlider())); //volume is set on the same value as its slider
        }
    }



    // Functions for starting the background musics on the updated volume
    public static void playGameMusic(String filePath) { //function for game music
        Media backgroundMusic = new Media(new File(filePath).toURI().toString()); //media is created using a file
        mediaPlayer2 = new MediaPlayer(backgroundMusic); //"Mediaplayer"-instance is created
        mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE); //music should be repeated in an indefinite loop
        mediaPlayer2.setVolume(getSliderValue(getMusicSlider())); //sets volume (by default to 50% of the volume)
        mediaPlayer2.play(); //music starts playing
        mediaPlayer2.setOnError(() -> {
            System.out.println("Media error occurred: " + mediaPlayer1.getError());
            //Error code, if an error appears
        });
        Ball.mediaPlayer3.setVolume(getSliderValue(getGameSoundSlider())); //sets volume (by default to 50% of the volume)
    }


    public static void playMenuMusic(String filePath) { //function for menu music
        Media backgroundMusic = new Media(new File(filePath).toURI().toString()); //media is created using a file
        mediaPlayer1 = new MediaPlayer(backgroundMusic); //"Mediaplayer"-instance is created
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE); //music should be repeated in an indefinite loop
        if(mediaPlayer1 != null){ //sets volume (by default to 50% of the volume)
            mediaPlayer1.setVolume(getSliderValue(musicSlider));
        }
        else {
            mediaPlayer1.setVolume(0.50); //50% of the volume
        }
        mediaPlayer1.play(); //music starts playing
        mediaPlayer1.setOnError(() -> {
            System.out.println("Media error occurred: " + mediaPlayer1.getError());
            //Error code, if an error appears
        });
    }


    // Setting the sound of the Ball
    public static void playBounceSound(MediaPlayer mediaPlayer) { //method plays the sound of the ball when he bounces off the paddle
        mediaPlayer.setVolume(getSliderValue(gameSoundSlider)); //50% of the volume
        mediaPlayer.play();
    }

}
