package com.snake;

import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CustomHBox extends HBox{

    String boxlabel;

    public CustomHBox(String boxlabel){

        super(15);
        this.boxlabel = boxlabel;

        Text text = new Text(boxlabel);
        getChildren().add(text);

        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));

        //URL url = getClass().getResource("/com/snake/Graphics/menu.png");
       // setStyle("-fx-background-image: url('" + url.toExternalForm() + "'); -fx-background-position: fixed");

    }

}
