package com.snake.Views;

import com.snake.Controllers.MenuController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import java.net.URL;

public class MenuView extends StackPane
{
    MenuController controller;

    public MenuView(MenuController controller)
    {
        this.controller = controller;

        initialize();
    }

    private void initialize()
    {
        URL url = getClass().getResource("/com/snake/Graphics/titlescreen.png");
        BackgroundImage backgroundImg = new BackgroundImage(new Image(url.toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Background background = new Background(backgroundImg);
        setBackground(background);
        //background image scaling

        //buttons
        Button playButton = new Button();
        Button exitButton = new Button();

        playButton.setOnAction(controller::handlePlayButtonPressed);
        exitButton.setOnAction(controller::handleExitButtonPressed);


        buttonstyling(playButton);
        buttonstyling(exitButton);
        
        URL playUrl = getClass().getResource("/com/snake/Graphics/playbutton.png");
        playButton.setGraphic(new ImageView(new Image(playUrl.toString())));
        URL quitUrl = getClass().getResource("/com/snake/Graphics/quitbutton.png");
        exitButton.setGraphic(new ImageView(new Image(quitUrl.toString())));
        
        playButton.setTranslateY(50);
        getChildren().add(playButton);

        exitButton.setTranslateX(290);
        exitButton.setTranslateY(215);
        getChildren().add(exitButton);
    }

    private void buttonstyling(Button button){
        button.setStyle("-fx-background-color: transparent; -fx-focus-color: transparent;-fx-cursor: hand; -fx-font-family: 'Arial'; -fx-font-size: 32");
    }
    
}

