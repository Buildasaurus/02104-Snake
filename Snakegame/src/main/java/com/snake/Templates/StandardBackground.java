package com.snake.Templates;

import com.snake.Utils.Resources;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;

// Made by Kajsa Berlstedt & Lucia Little

public class StandardBackground extends StackPane{
    public StandardBackground(){
        BackgroundImage backgroundImage = new BackgroundImage(Resources.getImageByName("baggrund"), BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Background background = new Background(backgroundImage);
        setBackground(background);
    }
}
