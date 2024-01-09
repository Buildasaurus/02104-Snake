package com.snake;

import java.net.URL;

import javafx.scene.control.Button;

public class OurButton extends Button
{
    public OurButton(String buttonName) 
    {
        super(buttonName);
       URL url = OurButton.class.getResource("/com/snake/Graphics/smallplaybt.png");
       System.out.println(url);
       setStyle("-fx-cursor: hand; -fx-background-image: url('" + url.toExternalForm() + "'); -fx-background-color: transparent; -fx-font-size: 20; -fx-background-attachment: fixed; -fx-background-position: 50% 50%; -fx-background-repeat: no-repeat");
       setPrefSize(300.0, 160.0);
    }
    
}