package com.snake.Views;

import java.net.URL;

import com.snake.OurButton;

import javafx.scene.control.Button;

public class SelectionButton extends Button {
    
    public  SelectionButton(String buttonName) 
    {
        super(buttonName);
       URL url = OurButton.class.getResource("/com/snake/Graphics/smallplaybt.png");
       setStyle("-fx-cursor: hand; -fx-background-image: url('" + url.toExternalForm() + "'); -fx-background-color: transparent; -fx-font-size: 20; -fx-background-attachment: fixed; -fx-background-position: 50% 50%; -fx-background-repeat: no-repeat");
       setPrefSize(300.0, 130.0);
    }
}
