package com.snake;

import com.snake.Model.Resources;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OurButton extends Button
{
    public OurButton(String buttonName) 
    {
        setText(buttonName);
        setGraphic(new ImageView(Resources.getImageByName("smallplaybt")));
        setStyle("-fx-background-color: transparent;-fx-focus-color: transparent;-fx-cursor: hand");
    }
    
}
