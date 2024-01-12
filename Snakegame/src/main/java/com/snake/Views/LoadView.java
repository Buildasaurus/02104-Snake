package com.snake.Views;

import com.snake.ButtonTemplate;
import com.snake.Controllers.LoadController;
import com.snake.Model.Resources;

import javafx.application.Platform;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class LoadView extends ButtonOverlayView {
    public LoadView(LoadController controller, String[] saveNames) {
        buttons = new ButtonTemplate[4];
        focusElementIndex = 0;

        initialize(controller, saveNames);
    }

    public void initialize(LoadController controller, String[] saveNames) {
        BackgroundImage backgroundImage = new BackgroundImage(Resources.getImageByName("baggrund"), BackgroundRepeat.NO_REPEAT, 
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        Background background = new Background(backgroundImage);
        setBackground(background);

        for (int i = 0; i < 3; i++) {
            ButtonTemplate saveButton = new ButtonTemplate("");
            buttons[i] = saveButton;
            saveButton.button.setOnAction(controller::handleLoadSaveButtonPressed);
        }
        updateButtonNames(saveNames);

        ButtonTemplate backButton = new ButtonTemplate("Back to Main Menu");
        buttons[3] = backButton;
        backButton.button.setOnAction(controller::handleBackButtonPressed);

        setBasicFormatting();

        Platform.runLater(() -> this.requestFocus());
    }

    public void updateButtonNames(String[] newNames) {
        for (int i = 0; i < 3; i++) {
            String buttonName = "" + (i + 1) + " ";
            buttonName += newNames[i] != null ? newNames[i] : "Empty Save";
            buttons[i].button.setText(buttonName);
        }
    }
}
