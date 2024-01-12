package com.snake.Views;

import com.snake.ButtonTemplate;
import com.snake.Controllers.GUIController;

import javafx.application.Platform;
import javafx.scene.paint.Color;

public class PauseView extends ButtonOverlayView {
    public PauseView(GUIController controller) {
        buttons = new ButtonTemplate[3];
        focusElementIndex = 0;

        initialize(controller);
    }

    public void initialize(GUIController controller) {
        setBackground(new Color(0.0, 0.0, 0.0, 0.3));
        
        ButtonTemplate resumeButton = new ButtonTemplate("Resume");
        buttons[0] = resumeButton;

        ButtonTemplate saveButton = new ButtonTemplate("Save Menu");
        buttons[1] = saveButton;

        ButtonTemplate backButton = new ButtonTemplate("Back to\nMain Menu");
        //backButton.setStyle("-fx-text-align: center");
        buttons[2] = backButton;


        resumeButton.button.setOnAction(controller::handleResumeButtonPressed);
        saveButton.button.setOnAction(controller::handleSaveMenuButtonPressed);
        backButton.button.setOnAction(controller::handleBackButtonPressed);

        setBasicFormatting();

        Platform.runLater(() -> resumeButton.requestFocus());
    }
}