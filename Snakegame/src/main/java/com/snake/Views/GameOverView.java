package com.snake.Views;

import com.snake.OurButton;
import com.snake.Controllers.GUIController;
import com.snake.Model.Highscore;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameOverView extends ButtonOverlayView {  
    private boolean buttonsVisible;
    
    public GameOverView(GUIController controller) {
        buttons = new Button[4];
        buttonsVisible = true;
        focusElementIndex = 0;

        initialize(controller);
    }

    public void initialize(GUIController controller) {
        setBackground(new Color(0.5, 0.0, 0.0, 0.3));

        Highscoretext score = new Highscoretext("Your score: " + controller.getGameController().getCurrentScore(focusElementIndex));
        Highscoretext highscore = new Highscoretext("Highscore: " + Highscore.getHighscore());

        VBox scorebox = new VBox(6);
        
        scorebox.getChildren().addAll(score,highscore);
        getChildren().add(scorebox);

        OurButton newGameButton = new OurButton("New Game");
        buttons[0] = newGameButton;
        
        OurButton loadGameButton = new OurButton("Load Game");
        buttons[1] = loadGameButton;

        OurButton backButton = new OurButton("Back to Main Menu");
        buttons[2] = backButton;
        OurButton hideButton = new OurButton("Hide Buttons");
        buttons[3] = hideButton;

        newGameButton.setOnAction(controller::handleNewGameButtonPressed);
        loadGameButton.setOnAction(controller::handleLoadGameButtonPressed);
        backButton.setOnAction(controller::handleBackButtonPressed);
        hideButton.setOnAction(controller::handleHideButtonPressed);

        setBasicFormatting();


        Platform.runLater(() -> newGameButton.requestFocus());
    }

    public void toggleGameOverButtonVisibility() {
        if (buttonsVisible) {
            for (int i = 0; i < buttons.length - 1; i++) {
                buttons[i].setVisible(false);
            }
            buttonsVisible = false;
        } else {
            for (int i = 0; i < buttons.length - 1; i++) {
                buttons[i].setVisible(true);
            }
            buttonsVisible = true;
        }
    }
}
