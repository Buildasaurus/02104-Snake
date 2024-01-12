package com.snake.Views;

import com.snake.Settings;
import com.snake.Controllers.GUIController;
import com.snake.Templates.OurButton;
import com.snake.Utils.Highscore;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

// Made by Marinus Juhl

public class GameOverView extends ButtonOverlayView
{
    private boolean buttonsVisible;

    public GameOverView(GUIController controller)
    {
        buttons = new OurButton[4];
        buttonsVisible = true;
        focusElementIndex = 0;

        initialize(controller);
    }


    public void initialize(GUIController controller)
    {
        setBackground(new Color(0.5, 0.0, 0.0, 0.3));

        int playerCount = Settings.getGameSettings().getPlayerCount();
        VBox scorebox = new VBox(6);

        for (int i = 0; i < playerCount; i++)
        {
            controller.getGameController().getCurrentScore(i);
            Text score = new Text(
                    "Player " + (i + 1) + ": " + controller.getGameController().getCurrentScore(i));
            score.setFill(Color.BLACK);
            score.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            score.setTextAlignment(TextAlignment.CENTER);
            scorebox.getChildren().add(score);
        }


        Text highscore = new Text("Highscore: " + Highscore.getHighscore());
        highscore.setFill(Color.BLACK);
        highscore.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        highscore.setTextAlignment(TextAlignment.CENTER);
        scorebox.getChildren().add(highscore);


        // Winning player- if any
        Text winner = new Text(controller.getGameController().getGameModel().getGameOverText());
        winner.setFill(Color.BLACK);
        winner.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        winner.setTextAlignment(TextAlignment.CENTER);
        scorebox.getChildren().add(winner);


        getChildren().add(scorebox);

        OurButton newGameButton = new OurButton("New Game");
        buttons[0] = newGameButton;

        OurButton loadGameButton = new OurButton("Load Game");
        buttons[1] = loadGameButton;

        OurButton backButton = new OurButton("Back to Main Menu");
        buttons[2] = backButton;
        OurButton hideButton = new OurButton("Hide Buttons");
        buttons[3] = hideButton;

        newGameButton.button.setOnAction(controller::handleNewGameButtonPressed);
        loadGameButton.button.setOnAction(controller::handleLoadGameButtonPressed);
        backButton.button.setOnAction(controller::handleBackButtonPressed);
        hideButton.button.setOnAction(controller::handleHideButtonPressed);

        setBasicFormatting();

        Platform.runLater(() -> newGameButton.requestFocus());
    }



    public void toggleGameOverButtonVisibility()
    {

        if (buttonsVisible)
        {
            for (int i = 0; i < buttons.length - 1; i++)
            {
                buttons[i].setVisible(false);
            }
            buttonsVisible = false;
        }
        else
        {
            for (int i = 0; i < buttons.length - 1; i++)
            {
                buttons[i].setVisible(true);
            }
            buttonsVisible = true;
        }
    }
}
