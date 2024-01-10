package com.snake.Views;

import com.snake.OurButton;
import com.snake.Settings;
import com.snake.Controllers.MenuController;

import javafx.scene.image.Image;
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
        OurButton playButton = new OurButton("play");
        OurButton continueButton = new OurButton("continue");
        OurButton loadButton = new OurButton("load");
        OurButton quitButton = new OurButton("quit", 6.5);

        playButton.setOnAction(controller::handlePlayButtonPressed);
        continueButton.setOnAction(controller::handleContinueButtonPressed);
        loadButton.setOnAction(controller::handleLoadButtonPressed);
        quitButton.setOnAction(controller::handleExitButtonPressed);

        //TODO fix this in another way, ideally make button and background scale the same
        //playButton.setTranslateY(Settings.windowHeight*0.1);
        getChildren().add(playButton);

        continueButton.setTranslateY(Settings.windowHeight*0.2);
        getChildren().add(continueButton);

        loadButton.setTranslateY(Settings.windowHeight*0.4);
        getChildren().add(loadButton);

        quitButton.setTranslateX(Settings.windowWidth*0.5 - (quitButton.getPrefWidth()*0.6));
        quitButton.setTranslateY(Settings.windowHeight*0.5 - (quitButton.getPrefHeight()*0.5));
        getChildren().add(quitButton);
    }

}
