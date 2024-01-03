package com.snake.Views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class GUIView extends StackPane {
    private Parent gameView;
    private Parent pauseView;
    private Parent gameOverView;

    private Label frameRateCounter;

    public GUIView(Parent gameView) {
        this.gameView = gameView;

        BorderPane gameGroup = new BorderPane();
        gameGroup.setCenter(this.gameView);
        gameGroup.setPadding(new Insets(5, 5, 5, 5));

        frameRateCounter = new Label("fps: 0.0");
        gameGroup.setTop(frameRateCounter);

        getChildren().add(gameGroup);
    }

    public void shiftFocus(KeyEvent key) {

    }

    public void updateFrameRate(double frameRate) {
        frameRateCounter.setText(String.format("fps: %.1f", frameRate));
    }
}
