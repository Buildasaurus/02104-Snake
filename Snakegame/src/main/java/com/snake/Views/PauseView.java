package com.snake.Views;

import com.snake.Controllers.GUIController;
import com.snake.Templates.StyledButton;

import javafx.application.Platform;
import javafx.scene.paint.Color;


// Made by Marinus Juhl

public class PauseView extends ButtonOverlayView {
    public PauseView(GUIController controller) {
        buttons = new StyledButton[3];
        focusElementIndex = 0;

        initialize(controller);
    }

    public void initialize(GUIController controller) {
        setBackground(new Color(0.0, 0.0, 0.0, 0.3));

        StyledButton resumeButton = new StyledButton("Resume");
        buttons[0] = resumeButton;


        StyledButton saveButton = new StyledButton("Save Menu");
        buttons[1] = saveButton;

        StyledButton backButton = new StyledButton("Back to\nMain Menu");
        //backButton.setStyle("-fx-text-align: center");
        buttons[2] = backButton;


        resumeButton.button.setOnAction(controller::handleResumeButtonPressed);
        saveButton.button.setOnAction(controller::handleSaveMenuButtonPressed);
        backButton.button.setOnAction(controller::handleBackButtonPressed);

        setBasicFormatting();

        Platform.runLater(() -> resumeButton.requestFocus());
    }
}
