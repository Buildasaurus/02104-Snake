package com.snake.Views;

import com.snake.Controllers.GUIController;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class GameOverView extends ButtonOverlayView {   
    public GameOverView(GUIController controller) {
        setBackground(new Color(0.5, 0.0, 0.0, 0.3));

        buttons = new Button[3];

        Button newGameButton = new Button("New Game");
        buttons[0] = newGameButton;
        Button loadGameButton = new Button("Load Game");
        buttons[1] = loadGameButton;
        Button backButton = new Button("Back to Main Menu");
        buttons[2] = backButton;

        newGameButton.setOnAction(controller::handleNewGameButtonPressed);
        loadGameButton.setOnAction(controller::handleLoadGameButtonPressed);
        backButton.setOnAction(controller::handleBackButtonPressed);

        focusElementIndex = 0;

        setBasicFormatting();

        Platform.runLater(() -> newGameButton.requestFocus());
    }
}
