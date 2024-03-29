package com.snake.Views;

import java.util.Optional;
import com.snake.Controllers.GUIController;
import com.snake.Templates.StyledButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

// Made by Marinus Juhl

public class SaveView extends ButtonOverlayView
{
    Alert confirmationAlert;
    int saveIndex;
    GUIController controller;

    public SaveView(GUIController controller, String[] saveNames)
    {
        buttons = new StyledButton[4];
        focusElementIndex = 0;
        this.controller = controller;

        initialize(saveNames);
    }

    public void initialize(String[] saveNames)
    {
        setBackground(new Color(0.0, 0.0, 0.0, 0.3));

        for (int i = 0; i < 3; i++)
        {
            StyledButton saveButton = new StyledButton("");
            buttons[i] = saveButton;
            saveButton.button.setOnAction(this::handleSaveButtonPressed);
        }
        updateButtonNames(saveNames);

        StyledButton backButton = new StyledButton("Back to\nPause Menu");
        buttons[3] = backButton;
        backButton.button.setOnAction(controller::handlePauseButtonPressed);

        confirmationAlert =
                new Alert(AlertType.CONFIRMATION, "You are going to overwrite a save, continue?");

        setBasicFormatting();

        Platform.runLater(() -> this.requestFocus());
    }

    public void updateButtonNames(String[] newNames)
    {
        for (int i = 0; i < 3; i++)
        {
            String buttonName = "" + (i + 1) + " ";
            buttonName += newNames[i] != null ? newNames[i] : "Empty Save";

            buttons[i].button.setText(buttonName);
        }
    }

    public void handleSaveButtonPressed(ActionEvent action)
    {
        Button actionOrigin = (Button) action.getSource();
        String saveName = actionOrigin.getText();
        saveIndex = Integer.parseInt(saveName.substring(0, 1));
        controller.handleSaveButtonPressed(saveIndex);
    }

    public void showAlert()
    {
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            controller.handleSaving(saveIndex);
        }
    }
}
