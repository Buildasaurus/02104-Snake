package com.snake.Views;

import java.net.URL;

import com.snake.OurButton;
import com.snake.Settings;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class SelectionButton extends Button {

    private double scaler = 6.0;

    private URL urlnormal = SelectionButton.class.getResource("/com/snake/Graphics/smallplaybt.png");

    private URL urlpressed = SelectionButton.class.getResource("/com/snake/Graphics/smallplaybtpressed.png");

    public  SelectionButton(String buttonName)

    {
        super(buttonName);
        Image buttonbackgroundimgfile = new Image(urlnormal.toString(), Settings.windowWidth/scaler, 0, true, false);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
        setStyle("-fx-cursor: hand; -fx-font-size: 30");
        setPrefSize(Settings.windowWidth/scaler, Settings.windowHeight/scaler);
    }

    public void changeback(){
        Image buttonbackgroundimgfile = new Image(urlnormal.toString(), Settings.windowWidth/scaler, 0, true, false);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
    }

    public void pressed(){
        Image buttonbackgroundimgfile = new Image(urlpressed.toString(), Settings.windowWidth/scaler, 0, true, false);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        setBackground(buttonbackground);
    }
}
