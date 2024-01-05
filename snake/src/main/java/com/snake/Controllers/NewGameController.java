package com.snake.Controllers;

import java.util.Set;
import com.snake.App;
import com.snake.Settings;
import com.snake.Model.GameSettings;
import com.snake.Model.GameSettings.GameMode;
import com.snake.Model.GameSettings.Level;
import com.snake.Views.GameView;
import com.snake.Views.NewGameView;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

public class NewGameController implements IController
{
    NewGameView view;
    GameSettings gameSettings = new GameSettings();

    public NewGameController()
    {
        view = new NewGameView(this, gameSettings.getAllPossiblePlayerCounts());
    }

    public Parent getView()
    {
        return view;
    }

    public void setLevel(Level level)
    {
        gameSettings.setLevel(level);
    }

    public Level getLevel()
    {
        return gameSettings.getLevel();
    }

    public void setGameMode(GameMode gameMode)
    {
        gameSettings.setGameMode(gameMode);
    }

    public GameMode getGameMode()
    {
        return gameSettings.getGameMode();
    }

    public boolean setPlayerCount(int playerCount)
    {
        return gameSettings.setPlayerCount(playerCount);
    }

    public int getPlayerCount()
    {
        return gameSettings.getPlayerCount();
    }

    /**
     * Sets the next active window to the {@link com.snake.Controllers.GUIController GUI Controller}
     * and updates the {@link #gameSettings game settings} stored.
     * @param action
     */
    public void handlePlayButtonPressed(ActionEvent action)
    {
        Settings.setGameSettings(gameSettings);
        GUIController newController = new GUIController();
        App.setRoot(newController);
    }
}
