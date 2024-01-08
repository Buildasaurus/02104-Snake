package com.snake.Views;

import com.snake.Controllers.GUIController;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class SaveView extends ButtonOverlayView {
    public SaveView(GUIController controller) {
        buttons = new Button[4];
        focusElementIndex = 0;

        initialize(controller);
    }

    public void initialize(GUIController controller) {
        setBackground(new Color(0.0, 0.0, 0.0, 0.3));

        //TODO take String[] in constructor with save names and give them to the buttons

        for (int i = 0; i < 3; i++) {
            Button saveButton = new Button("Save " + (i + 1));
            buttons[i] = saveButton;
            saveButton.setOnAction(controller::handleSaveButtonPressed);
        }
        Button backButton = new Button("Back to Main Menu");
        buttons[3] = backButton;

        setBasicFormatting();

        Platform.runLater(() -> this.requestFocus());
    } 
}
