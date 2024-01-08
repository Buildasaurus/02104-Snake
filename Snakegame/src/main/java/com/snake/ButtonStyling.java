package com.snake;

import java.io.File;
import java.net.URL;
import com.snake.Model.Resources;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonStyling {

    public static void transparentbackgroundandcursor(Button button){
        button.setStyle("-fx-background-color: transparent;-fx-focus-color: transparent;-fx-cursor: hand");
    }

    public void setImage(String name, Button button){
        button.setGraphic(new ImageView(Resources.getImageByName(name)));
    }
}
