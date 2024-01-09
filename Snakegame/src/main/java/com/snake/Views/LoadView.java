package com.snake.Views;

import com.snake.Controllers.LoadController;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class LoadView extends ButtonOverlayView {
    public LoadView(LoadController controller) {
        buttons = new Button[4];
        focusElementIndex = 0;

        initialize(controller);
    }

    public void initialize(LoadController controller) {
        setBackground(new Color(1.0, 1.0, 1.0, 1.0));

        //TODO take String[] in constructor with save names and give them to the buttons

        for (int i = 0; i < 3; i++) {
            Button saveButton = new Button("Save " + (i + 1));
            buttons[i] = saveButton;
            saveButton.setOnAction(controller::handleLoadSaveButtonPressed);
        }
        Button backButton = new Button("Back to Main Menu");
        buttons[3] = backButton;
        backButton.setOnAction(controller::handleBackButtonPressed);

        setBasicFormatting();

        Platform.runLater(() -> this.requestFocus());

        
    }
}
