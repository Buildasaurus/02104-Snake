package com.snake.Views;

import com.snake.Controllers.GUIController;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class SaveView extends ButtonOverlayView {
    public SaveView(GUIController controller, String[] saveNames) {
        buttons = new Button[4];
        focusElementIndex = 0;

        initialize(controller, saveNames);
    }

    public void initialize(GUIController controller, String[] saveNames) {
        setBackground(new Color(0.0, 0.0, 0.0, 0.3));

        for (int i = 0; i < 3; i++) {
            Button saveButton = new Button();
            buttons[i] = saveButton;
            saveButton.setOnAction(controller::handleSaveButtonPressed);
        }
        updateButtonNames(saveNames);

        Button backButton = new Button("Back to Pause Menu");
        buttons[3] = backButton;
        backButton.setOnAction(controller::handlePauseButtonPressed);

        setBasicFormatting();

        Platform.runLater(() -> this.requestFocus());
    }

    public void updateButtonNames(String[] newNames) {
        for (int i = 0; i < 3; i++) {
            String buttonName = "" + (i + 1) + " ";
            buttonName += newNames[i] != null ? newNames[i] : "Empty Save";

            buttons[i].setText(buttonName);
        }
    }
}
