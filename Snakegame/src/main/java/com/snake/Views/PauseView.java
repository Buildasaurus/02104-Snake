package com.snake.Views;

import com.snake.Controllers.GUIController;
import com.snake.Templates.OurButton;
import com.snake.Utils.Resources;
import javafx.application.Platform;
import javafx.scene.paint.Color;


// Made by Marinus Juhl

public class PauseView extends ButtonOverlayView {
    public PauseView(GUIController controller) {
        buttons = new OurButton[3];
        focusElementIndex = 0;

        initialize(controller);
    }

    public void initialize(GUIController controller) {
        setBackground(new Color(0.0, 0.0, 0.0, 0.3));

        OurButton resumeButton = new OurButton("Resume");
        buttons[0] = resumeButton;


        OurButton saveButton = new OurButton("Save Menu");
        buttons[1] = saveButton;

        OurButton backButton = new OurButton("Back to\nMain Menu");
        //backButton.setStyle("-fx-text-align: center");
        buttons[2] = backButton;


        resumeButton.button.setOnAction(controller::handleResumeButtonPressed);
        saveButton.button.setOnAction(controller::handleSaveMenuButtonPressed);
        backButton.button.setOnAction(controller::handleBackButtonPressed);

        setBasicFormatting();

        Platform.runLater(() -> resumeButton.requestFocus());
    }
}
