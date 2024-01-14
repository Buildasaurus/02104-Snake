package com.snake.Views;

import com.snake.Settings;
import com.snake.Controllers.GUIController;
import com.snake.Utils.Resources;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// Made by Marinus Juhl

public class GUIView extends StackPane
{
    private GameView gameView;
    private PauseView pauseView;
    private GameOverView gameOverView;
    private SaveView saveView;

    private Label frameRateCounter;
    private Label[] currentScores;
    private Label[] currentSpeeds;
    Font font = Resources.getFontByName("Adventure", 15);

    public GUIView(Parent gameView, int playerCount, double[] speedArray)
    {
        this.gameView = (GameView) gameView;
        frameRateCounter = new Label("fps: 0.0");
        currentScores = new Label[playerCount];
        currentSpeeds = new Label[playerCount];
        BackgroundImage backgroundImage =
                new BackgroundImage(Resources.getImageByNamesetWidth("baggrund", 3),
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO,
                                BackgroundSize.AUTO, false, false, true, false));
        Background background = new Background(backgroundImage);
        this.setBackground(background);

        initialize(playerCount, speedArray);
    }

    public void initialize(int playerCount, double[] speedArray)
    {
        BorderPane gameGroup = new BorderPane();
        gameGroup.setCenter(this.gameView);
        gameGroup.setPadding(new Insets(5, 5, 5, 5));
        gameView.setAlignment(Pos.TOP_CENTER);

        HBox topBox = new HBox();
        double heightOfHBOX = (Settings.windowHeight - gameView.height) * 0.5;
        topBox.setSpacing(50.0);
        topBox.setPrefHeight(heightOfHBOX);
        topBox.setAlignment(Pos.CENTER);

        VBox leftBox = new VBox();
        double widthOfVBOX = (Settings.windowWidth - gameView.width) * 0.5;
        leftBox.setSpacing(15.0);
        leftBox.setPrefWidth(widthOfVBOX);
        leftBox.setAlignment(Pos.CENTER);

        frameRateCounter.setFont(font);
        topBox.getChildren().add(frameRateCounter);

        for (int i = 0; i < playerCount; i++)
        {
            VBox playerInfo = new VBox(2);
            playerInfo.setAlignment(Pos.CENTER);
            playerInfo.setMaxWidth(widthOfVBOX/1.2);
            playerInfo.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);");

            Label player = new Label("Player " + (i + 1));
            player.setFont(font);
            playerInfo.getChildren().add(player);

            Label currentScore = new Label("Score " + (i + 1));
            currentScores[i] = currentScore;
            currentScore.setFont(font);
            playerInfo.getChildren().add(currentScore);

            Label currentSpeed = new Label("speed: " + speedArray[i]);
            currentSpeeds[i] = currentSpeed;
            currentSpeed.setFont(font);
            playerInfo.getChildren().add(currentSpeed);

            leftBox.getChildren().add(playerInfo);

        }


        // extra vision text
        Text extraVisionText = new Text("Extra vision: ");
        extraVisionText.setFont(font);

        topBox.getChildren().add(extraVisionText);

        CheckBox toggleExtraVision = new CheckBox();
        toggleExtraVision.setSelected(Settings.getGameSettings().getExtraVision());
        toggleExtraVision.setOnAction((s) -> {
            Settings.getGameSettings().setExtraVision(toggleExtraVision.isSelected());
        });
        toggleExtraVision.setFocusTraversable(false);
        topBox.getChildren().add(toggleExtraVision);

        // Transparent background



        gameGroup.setLeft(leftBox);
        gameGroup.setTop(topBox);

        getChildren().add(gameGroup);
    }

    public void updateFrameRate(double frameRate)
    {
        frameRateCounter.setText(String.format("fps: %.1f", frameRate));
        frameRateCounter.setFont(font);
    }

    public void updateCurrentScore(int score, int player)
    {
        currentScores[player].setText("score: " + score);
        frameRateCounter.setFont(font);
    }

    public void updateCurrentSpeed(double speed, int player)
    {
        currentSpeeds[player].setText("speed: " + speed);
        frameRateCounter.setFont(font);
    }

    public void setPauseView(GUIController controller)
    {
        pauseView = new PauseView(controller);
        getChildren().add(this.pauseView);
    }

    public void removePauseView()
    {
        getChildren().remove(pauseView);
        pauseView = null;
    }

    public void setGameOverView(GUIController controller)
    {
        gameOverView = new GameOverView(controller);
        getChildren().add(this.gameOverView);
    }

    public void toggleGameOverButtonVisibility()
    {
        gameOverView.toggleGameOverButtonVisibility();
    }

    public void setSaveView(GUIController controller, String[] saveNames)
    {
        removePauseView();
        saveView = new SaveView(controller, saveNames);
        getChildren().add(this.saveView);
    }

    public void removeSaveView(GUIController controller)
    {
        getChildren().remove(saveView);
        saveView = null;
        setPauseView(controller);
    }

    public void updateSaveNames(String[] saveNames)
    {
        saveView.updateButtonNames(saveNames);
    }

    public void showAlert()
    {
        saveView.showAlert();
    }
}
