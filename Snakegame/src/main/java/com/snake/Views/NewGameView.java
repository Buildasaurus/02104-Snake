package com.snake.Views;


import com.snake.Settings;
import com.snake.Controllers.NewGameController;
import com.snake.Model.GameSettings.GameMode;
import com.snake.Model.GameSettings.Level;
import com.snake.Templates.CustomHBox;
import com.snake.Templates.StyledButton;
import com.snake.Templates.SelectionButton;
import com.snake.Templates.StandardBackground;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

// Made by Kajsa Berlstedt & Lucia Little

/**
 * Represents the graphical user interface. Extends StandardBackground to incorporate a consistent
 * background style. The UI includes player selection, game mode, and map type customization.
 * Organized in a CustomHBox, user choices trigger corresponding actions through NewGameController.
 * The UI is made using JavaFX components.
 */

public class NewGameView extends StandardBackground
{
    NewGameController controller;
    public CustomHBox playerselection;
    public CustomHBox gamemode;
    public CustomHBox maptype;

    public NewGameView(NewGameController controller, int[] playerCounts)
    {
        this.controller = controller;
        initialize(playerCounts);
    }

    private void initialize(int[] playerCounts)
    {

        playerselection = new CustomHBox("Players");


        for (int i : playerCounts)
        {
            SelectionButton button = new SelectionButton(Integer.toString(i));
            if (Settings.getGameSettings().getPlayerCount() == i)
            {
                button.pressed();
                playerselection.defaultButton = button;
            }
            button.setOnAction(controller::handlePlayerCounts);
            playerselection.getChildren().add(button);
        }

        // gamemode hbox

        gamemode = new CustomHBox("Game Mode");

        for (GameMode mode : GameMode.values())
        {
            SelectionButton button = new SelectionButton(mode.toString());
            if (Settings.getGameSettings().getGameMode() == mode)
            {
                button.pressed();
                gamemode.defaultButton = button;
            }
            button.setOnAction(controller::handlegameMode);
            gamemode.getChildren().add(button);
        }

        // maptype hbox

        maptype = new CustomHBox("Map Type");

        for (Level level : Level.values())
        {
            SelectionButton button = new SelectionButton(level.toString());
            if (Settings.getGameSettings().getLevel() == level)
            {
                button.pressed();
                maptype.defaultButton = button;
            }
            button.setOnAction(controller::handlelevel);
            maptype.getChildren().add(button);
        }

        VBox vbox = new VBox(10);

        vbox.getChildren().addAll(playerselection, gamemode, maptype);

        // Start game
        StyledButton startGame = new StyledButton("Start game");
        startGame.button.setOnAction(controller::handlePlayButtonPressed);


        // setAlignment for good scaling
        setAlignment(startGame, Pos.BOTTOM_CENTER);
        vbox.setTranslateY(Settings.windowHeight / 5);


        getChildren().add(vbox);
        getChildren().add(startGame);


    }

}
