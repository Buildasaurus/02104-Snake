package com.snake.Views;

import com.snake.OurButton;
import com.snake.Controllers.MenuController;
import com.snake.Model.Resources;
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

import java.io.File;
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
        OurButton quitButton = new OurButton("quit");


        playButton.setOnAction(controller::handlePlayButtonPressed);
        quitButton.setOnAction(controller::handleExitButtonPressed);

        playButton.setTranslateY(50);
        getChildren().add(playButton);

        quitButton.setTranslateX(270);
        quitButton.setTranslateY(205);
        getChildren().add(quitButton);
    }

}
