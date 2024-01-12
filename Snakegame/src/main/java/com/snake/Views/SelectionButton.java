package com.snake.Views;

import com.snake.Settings;
import com.snake.Model.Resources;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;


public class SelectionButton extends StackPane {

    public Button button;
    private double scaler = 6.0;

    public  SelectionButton(String buttonName)

    {
        super();
        this.button = new Button(buttonName);
        Image buttonbackgroundimgfile = Resources.getImageByNamesetWidth("smallplaybt", scaler);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        button.setBackground(buttonbackground);
        button.setStyle("-fx-cursor: hand; -fx-font-size: 30");
        button.setPrefSize(Settings.windowWidth/scaler, Settings.windowHeight/scaler);
        getChildren().add(button);
        
        //setFont(new Font("Arial", Font.PLAIN, 2));
        //setPreferredSize(new Dimension(25,25));
        //setText("1");

    }

    public void changeback(){
        Image buttonbackgroundimgfile = Resources.getImageByNamesetWidth("smallplaybt", scaler);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        button.setBackground(buttonbackground);
    }

    public void pressed(){
        Image buttonbackgroundimgfile = Resources.getImageByNamesetWidth("smallplaybtpressed", scaler);
        BackgroundImage buttonbackgroundimg = new BackgroundImage(buttonbackgroundimgfile, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, false));
        Background buttonbackground = new Background(buttonbackgroundimg);
        button.setBackground(buttonbackground);
    }
}