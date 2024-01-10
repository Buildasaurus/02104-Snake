package com.snake.Views;

import java.net.URL;

import com.snake.OurButton;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SelectionButton extends Button {

    private URL url = OurButton.class.getResource("/com/snake/Graphics/smallplaybt.png");
    
    public  SelectionButton(String buttonName) 
    {
        super(buttonName);
       setStyle("-fx-cursor: hand; -fx-background-image: url('" + url.toExternalForm() + "'); -fx-background-color: transparent; -fx-font-size: 20; -fx-background-attachment: fixed; -fx-background-position: 50% 50%; -fx-background-repeat: no-repeat");
       setPrefSize(300.0, 130.0);
  
    }
    
    public void changeback(){
        setStyle("-fx-cursor: hand; -fx-background-image: url('" + url.toExternalForm() + "'); -fx-background-color: transparent; -fx-font-size: 20; -fx-background-attachment: fixed; -fx-background-position: 50% 50%; -fx-background-repeat: no-repeat");
    }

    public void pressed(){
        setStyle("-fx-color: red");
    }
}
