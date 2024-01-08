package com.snake.Views;

import com.snake.OurButton;
import com.snake.Controllers.GUIController;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class PauseView extends ButtonOverlayView {
    public PauseView(GUIController controller) {
        buttons = new Button[3];
        focusElementIndex = 0;

        initialize(controller);
    }

    public void initialize(GUIController controller) {
        setBackground(new Color(0.0, 0.0, 0.0, 0.3));
        
        OurButton resumeButton = new OurButton("Resume");
        buttons[0] = resumeButton;

        OurButton saveButton = new OurButton("Save");
        buttons[1] = saveButton;

        OurButton backButton = new OurButton("Back to Main Menu");
        buttons[2] = backButton;


        resumeButton.setOnAction(controller::handleResumeButtonPressed);
        saveButton.setOnAction(controller::handleSaveButtonPressed);
        backButton.setOnAction(controller::handleBackButtonPressed);

        setBasicFormatting();

        Platform.runLater(() -> resumeButton.requestFocus());
    }
}
