package com.snake;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CustomHBox extends HBox{
    private Button previousButtonPressed;
    String boxlabel;

    public CustomHBox(String boxlabel){

        super(15);
        this.boxlabel = boxlabel;

        Text text = new Text(boxlabel);
        getChildren().add(text);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));

    }

    public void buttonPressed(Button action){
        action.setVisible(false);

        if (previousButtonPressed != null) {
            previousButtonPressed.setVisible(true); 
        }   
    }  
}
