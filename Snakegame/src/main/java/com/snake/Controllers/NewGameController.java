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
import javafx.scene.control.Button;

public class NewGameController implements IController
{
    NewGameView view;
    GameSettings gameSettings;

    public NewGameController()
    {
        gameSettings = Settings.getGameSettings();
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
     *
     * @param action
     */
    public void handlePlayButtonPressed(ActionEvent action)
    {
        Settings.setGameSettings(gameSettings);
        GUIController newController = new GUIController();
        App.setRoot(newController);
    }

<<<<<<< Updated upstream
    public void handlesinglePlayerButtonPressed(ActionEvent action){
        gameSettings.setPlayerCount(1);
    }

    public void handletwoPlayerButtonPressed(ActionEvent action){
        gameSettings.setPlayerCount(2);
    }

    public void handlethreePlayerButtonPressed(ActionEvent action){
        gameSettings.setPlayerCount(3);
    }

    public void handleeasyButtonPressed(ActionEvent action){
        gameSettings.setGameMode(GameMode.Easy);
    }

    public void handlenormalButtonPressed(ActionEvent action){
        gameSettings.setGameMode(GameMode.Normal);
    }

    public void handlehardButtonPressed(ActionEvent action){
        gameSettings.setGameMode(GameMode.Hard);
    }

    public void handleinsaneButtonPressed(ActionEvent action){
        gameSettings.setGameMode(GameMode.Insane);
    }

    public void handleemptyButtonPressed(ActionEvent action){
        gameSettings.setLevel(Level.Empty);
    }

    public void handlerandomButtonPressed(ActionEvent action){
        gameSettings.setLevel(Level.Random);
    }

=======
    public void handlePlayerCounts(ActionEvent action){
        Button actionOrigin = (Button) action.getSource();
        gameSettings.setPlayerCount(Integer.valueOf(actionOrigin.getText()));
    }

    public void handlegameMode(ActionEvent action){
        Button actionorigin = (Button) action.getSource();
        gameSettings.setGameMode(GameMode.valueOf(actionorigin.getText()));
    }

    public void handlelevel(ActionEvent action){
        Button actionorigin = (Button) action.getSource();
        gameSettings.setLevel(Level.valueOf(actionorigin.getText()));
    }
>>>>>>> Stashed changes
}
