package com.snake;

import java.io.File;
import java.net.URL;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonStyling {

    public static void transparentbackgroundandcursor(Button button){
        button.setStyle("-fx-background-color: transparent;-fx-focus-color: transparent;-fx-cursor: hand");
    }

    public void setImage(File file, Button button){
        URL url = getClass().getResource(file.getAbsolutePath());
        button.setGraphic(new ImageView(new Image(url.toString())));
    }
}
