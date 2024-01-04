package com.snake.Views;

import com.snake.Controllers.GUIController;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class GUIView extends StackPane {
    private Parent gameView;
    private PauseView pauseView;
    private GameOverView gameOverView;

    private Label frameRateCounter;
    private Label currentScore;

    public GUIView(Parent gameView) {
        this.gameView = gameView;

        BorderPane gameGroup = new BorderPane();
        gameGroup.setCenter(this.gameView);
        gameGroup.setPadding(new Insets(5, 5, 5, 5));

        HBox topBox = new HBox(50.0);
        frameRateCounter = new Label("fps: 0.0");
        currentScore = new Label("Current Score: 0");
        topBox.getChildren().addAll(frameRateCounter, currentScore);
        gameGroup.setTop(topBox);

        getChildren().add(gameGroup);
    }

    public void shiftFocus(KeyEvent key) {
        if (pauseView != null) {
            pauseView.shiftFocus(key);
        } else if (gameOverView != null) {
            gameOverView.shiftFocus(key);
        }
    }

    public void updateFrameRate(double frameRate) {
        frameRateCounter.setText(String.format("fps: %.1f", frameRate));
    }

    public void updateCurrentScore(int score) {
        currentScore.setText("Current score: " + score);
    }

    public void setPauseView(GUIController controller) {
        pauseView = new PauseView(controller);
        getChildren().add(this.pauseView);
    }

    public void removePauseView() {
        getChildren().remove(pauseView);
        pauseView = null;
    }

    public void setGameOverView(GUIController controller) {
        gameOverView = new GameOverView(controller);
        getChildren().add(this.gameOverView);
    }
}
