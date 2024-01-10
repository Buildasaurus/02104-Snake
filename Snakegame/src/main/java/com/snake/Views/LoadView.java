package com.snake.Views;

import com.snake.Controllers.LoadController;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class LoadView extends ButtonOverlayView {
    public LoadView(LoadController controller, String[] saveNames) {
        buttons = new Button[4];
        focusElementIndex = 0;

        initialize(controller, saveNames);
    }

    public void initialize(LoadController controller, String[] saveNames) {
        setBackground(new Color(1.0, 1.0, 1.0, 1.0));

        for (int i = 0; i < 3; i++) {
            Button saveButton = new Button();
            buttons[i] = saveButton;
            saveButton.setOnAction(controller::handleLoadSaveButtonPressed);
        }
        updateButtonNames(saveNames);

        Button backButton = new Button("Back to Main Menu");
        buttons[3] = backButton;
        backButton.setOnAction(controller::handleBackButtonPressed);

        setBasicFormatting();

        Platform.runLater(() -> this.requestFocus());
    }

    public void updateButtonNames(String[] newNames) {
        for (int i = 0; i < 3; i++) {
            String buttonName = newNames[i] != null ? newNames[i] : "Empty Save";

            buttons[i].setText(buttonName);
        }
    }
}
