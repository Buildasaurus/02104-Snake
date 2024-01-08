package com.snake;

import com.snake.Model.Resources;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonStyling {

    public static void transparentbackground(Button button){
        //also changes cursor to a hand when hovering over button
        button.setStyle("-fx-background-color: transparent;-fx-focus-color: transparent;-fx-cursor: hand");
    }

    public static void setImage(String path, Button button){
        Image buttonImg = Resources.getImageByName(path);
        button.setGraphic(new ImageView(buttonImg));
    }
    
}
