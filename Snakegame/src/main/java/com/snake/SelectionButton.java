package com.snake;

import com.snake.Model.Resources;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

public class SelectionButton extends Button {

    private double scaler = 6.0;

    public  SelectionButton(String buttonName)

    {
        super(buttonName);
        Image buttonbackgroundimgfile = Resources.getImageByNamesetWidth("smallplaybt", scaler);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(1,1, true, true, false, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
        setStyle("-fx-cursor: hand");
        Font font = Resources.getFontByName("crimes", 30);
        setFont(font);
        setPrefSize(Settings.windowWidth/scaler, Settings.windowHeight/scaler);
    }

    public void changeback(){
        Image buttonbackgroundimgfile = Resources.getImageByNamesetWidth("smallplaybt", scaler);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(1, 1, true, true, false, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
    }

    public void pressed(){
        Image buttonbackgroundimgfile = Resources.getImageByNamesetWidth("smallplaybtpressed", scaler);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(1, 1, true, true, false, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
    }
}
