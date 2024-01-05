package com.snake.Views;

import com.snake.Controllers.GUIController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class GUIView extends StackPane {
    private GameView gameView;
    private PauseView pauseView;
    private GameOverView gameOverView;

    private Label frameRateCounter;
    private Label[] currentScores;

    public GUIView(Parent gameView, int playerCount) {
        this.gameView = (GameView) gameView;

        BorderPane gameGroup = new BorderPane();
        gameGroup.setCenter(this.gameView);
        gameGroup.setPadding(new Insets(5, 5, 5, 5));
        this.gameView.setAlignment(Pos.TOP_CENTER);

        HBox topBox = new HBox(50.0);
        topBox.setPrefHeight(50.0);
        topBox.setAlignment(Pos.CENTER);

        frameRateCounter = new Label("fps: 0.0");
        frameRateCounter.setFont(new Font(15.0));
        topBox.getChildren().add(frameRateCounter);

        currentScores = new Label[playerCount];
        for (int i = 0; i < playerCount; i++) {
            Label currentScore = new Label();
            currentScore.setText("Player " + i + "'s score: 0");
            currentScores[i] = currentScore;
            currentScore.setFont(new Font(15.0));
            topBox.getChildren().add(currentScore);
        }

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

    public void updateCurrentScore(int score, int player) {
        currentScores[player].setText("Player " + player + "'s score: " + score);
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

    public void toggleGameOverButtonVisibility() {
        gameOverView.toggleGameOverButtonVisibility();
    }
}
