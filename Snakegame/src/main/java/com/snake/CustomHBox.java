package com.snake;

import com.snake.Views.SelectionButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CustomHBox extends HBox{
    public SelectionButton previousButtonPressed;
    String boxlabel;

    public CustomHBox(String boxlabel){

        super(15);
        this.boxlabel = boxlabel;

        Text text = new Text(boxlabel);
        getChildren().add(text);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));

    }

    public void buttonPressed(SelectionButton action){
        action.setVisible(false);
        if (previousButtonPressed != null) {
            previousButtonPressed.setVisible(true);
        }
        previousButtonPressed = action;
    }
}
