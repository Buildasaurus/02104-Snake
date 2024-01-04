package com.snake.Views;

import com.snake.Controllers.GUIController;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class PauseView extends ButtonOverlayView {
    public PauseView(GUIController controller) {
        setBackground(new Color(0.0, 0.0, 0.0, 0.3));

        buttons = new Button[3];
        
        Button resumeButton = new Button("Resume");
        buttons[0] = resumeButton;
        Button saveButton = new Button("Save");
        buttons[1] = saveButton;
        Button backButton = new Button("Back to Main Menu");
        buttons[2] = backButton;

        resumeButton.setOnAction(controller::handleResumeButtonPressed);
        saveButton.setOnAction(controller::handleSaveButtonPressed);
        backButton.setOnAction(controller::handleBackButtonPressed);
        
        focusElementIndex = 0;

        setBasicFormatting();

        Platform.runLater(() -> resumeButton.requestFocus());
    }
}
