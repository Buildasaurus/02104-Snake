package com.snake.Views;

import com.snake.Controllers.GUIController;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class GameOverView extends ButtonOverlayView {  
    private boolean buttonsVisible;
    
    public GameOverView(GUIController controller) {
        buttons = new Button[4];
        buttonsVisible = true;

        initialize(controller);
    }

    public void initialize(GUIController controller) {
        setBackground(new Color(0.5, 0.0, 0.0, 0.3));

        Button newGameButton = new Button("New Game");
        buttons[0] = newGameButton;
        Button loadGameButton = new Button("Load Game");
        buttons[1] = loadGameButton;
        Button backButton = new Button("Back to Main Menu");
        buttons[2] = backButton;
        Button hideButton = new Button("Hide Buttons");
        buttons[3] = hideButton;

        newGameButton.setOnAction(controller::handleNewGameButtonPressed);
        loadGameButton.setOnAction(controller::handleLoadGameButtonPressed);
        backButton.setOnAction(controller::handleBackButtonPressed);
        hideButton.setOnAction(controller::handleHideButtonPressed);

        focusElementIndex = 0;

        setBasicFormatting();

        Platform.runLater(() -> newGameButton.requestFocus());
    }

    public void toggleGameOverButtonVisibility() {
        if (buttonsVisible) {
            for (int i = 0; i < buttons.length - 1; i++) {
                buttons[i].setVisible(false);
            }
            buttonsVisible = false;
        } else {
            for (int i = 0; i < buttons.length - 1; i++) {
                buttons[i].setVisible(true);
            }
            buttonsVisible = true;
        }
    }
}
