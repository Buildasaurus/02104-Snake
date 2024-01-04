package com.snake.Views;


import com.snake.Controllers.MenuController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
        /*background image
        URL url = getClass().getResource("/com/snake/Graphics/main.png");
        BackgroundImage BI = new BackgroundImage(new Image(url.toString()), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.setBackground(new Background(BI));*/

        //background color
        this.setStyle("-fx-background-color: #D8BFD8");

        //title
        Label titleLabel = new Label("Snake");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 56));
        setAlignment(titleLabel, Pos.TOP_CENTER);
        setMargin(titleLabel, new Insets(100,0,0,0));
        getChildren().add(titleLabel);

        //buttons
        Button playButton = new Button("Play");
        Button exitButton = new Button("Quit");

        playButton.setFont(new Font(30));
        exitButton.setFont(new Font(30));

        playButton.setOnAction(controller::handlePlayButtonPressed);
        exitButton.setOnAction(controller::handleExitButtonPressed);

        buttonstyling(playButton);
        buttonstyling(exitButton);

        HBox buttonspacing = new HBox(20);
        buttonspacing.getChildren().addAll(playButton, exitButton);
        getChildren().add(buttonspacing);
        setAlignment(buttonspacing, Pos.BOTTOM_RIGHT);
    }

    private void buttonstyling(Button button){
        button.setStyle("-fx-focus-color: transparent;-fx-background-color: transparent;-fx-cursor: hand; -fx-font-family: 'Arial'; -fx-font-size: 32");
    }
}
