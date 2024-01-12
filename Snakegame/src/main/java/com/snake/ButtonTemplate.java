package com.snake;

import com.snake.Model.Resources;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ButtonTemplate extends StackPane {
    public Button button;

    private double scaler = 4.5;

    public ButtonTemplate(String buttonName){
        super();
       this.button = new Button(buttonName);
       this.getChildren().add(this.button);
       setBackground();
    }


    public ButtonTemplate(String buttonName, double scaler){
        super();
        this.scaler = scaler;
       this.button = new Button(buttonName);
       this.getChildren().add(this.button);
       setBackground();
    }

    private void setBackground(){

        this.button.setBackground(new Background(new BackgroundFill(new Color(0, 0, 0, 0), null, null)));
        Image buttonbackgroundimgfile = Resources.getImageByNamesetWidth("smallplaybt", scaler);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(1, 1, true, true, false, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
        this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        this.button.setStyle("-fx-cursor: hand; -fx-font-size: 20");
        this.button.setPrefSize(Settings.windowWidth/scaler, Settings.windowHeight/scaler);
//transperant till knappen
//set size på button ikke our button
//benjamins hjælp
        this.paddingProperty().bind(Bindings.createObjectBinding(() -> new Insets(
            this.button.getHeight() / 100 * 20, 
            this.button.getWidth() / 100 * 10, 
            this.button.getHeight() / 100 * 35, 
            this.button.getWidth() / 100 * 14), 
            this.button.widthProperty(), this.button.heightProperty()));
    }
    
}

    