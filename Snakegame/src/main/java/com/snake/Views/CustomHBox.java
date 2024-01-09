package com.snake.Views;

import java.net.URL;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class CustomHBox extends HBox{
    
    public CustomHBox(){

        super(15);

        URL url = getClass().getResource("/com/snake/Graphics/menu.png");
       setStyle("-fx-background-image: url('" + url.toExternalForm() + "'); -fx-background-color: transparent; -fx-background-attachment: fixed; -fx-background-position: 50% 50%; -fx-background-repeat: no-repeat");
       //setPrefSize(300.0, 130.0);

        //marginaler för CustomHBox
        setPadding(new Insets(50,50,50,50)); //Insets-objekt med önskade marginaler

        //setPrefSize(2000.0, 100.0);
    }
    
}
