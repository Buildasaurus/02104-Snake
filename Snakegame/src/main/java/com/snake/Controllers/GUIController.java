package com.snake.Controllers;

import java.util.ArrayList;
import java.util.Set;
import com.snake.App;
import com.snake.Settings;
import com.snake.Model.GameState;
import com.snake.Views.GUIView;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

public class GUIController implements IController
{
    private GameController gameController;

    private GUIView view;

    private AnimationTimer gameTimer;
    private int[] playerProgress;
    private ArrayList<Integer> updateList;

    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    private boolean isGameOver = false;
    private boolean isPaused = false;

    private int playerCount;



    /**
     * Keeps track of an {@link javafx.animation.AnimationTimer AnimationTimer} used to compute game
     * logic via {@link GameController}. Main controller of the main view
     * {@link com.snake.Views.GUIView GUIView}. Handles {@link #handleKeyPressed(KeyEvent)
     * keypresses} of the view. Handles button actions of the {@link com.snake.Views.GUIView
     * GUIView} subviews: {@link com.snake.Views.PauseView PauseView} and
     * {@link com.snake.Views.GameOverView GameOverView}, as well as switching to those views.
     */
    public GUIController()
    {
        int margin = 50; // should fit with the GUIView margins.

        int height = Settings.windowHeight - margin;
        int width = Settings.windowWidth - margin;
        int potentialBoxHeight = height / Settings.rowCount;
        int potentialBoxWidth = width / Settings.columnCount;
        int boxDimension = Math.min(potentialBoxHeight, potentialBoxWidth);
        int gameHeight = boxDimension * Settings.rowCount;
        int gameWidth = boxDimension * Settings.columnCount;
        this.gameController = new GameController(gameHeight, gameWidth);

        initialize();
    }

    /**
     * Constructor used for loading a save state.
     *
     * @param gameState
     */
    public GUIController(GameState gameState)
    {
        int margin = 50; // should fit with the GUIView margins.

        int height = Settings.windowHeight - margin;
        int width = Settings.windowWidth - margin;
        int potentialBoxHeight = height / Settings.rowCount;
        int potentialBoxWidth = width / Settings.columnCount;
        int boxDimension = Math.min(potentialBoxHeight, potentialBoxWidth);
        int gameHeight = boxDimension * Settings.rowCount;
        int gameWidth = boxDimension * Settings.columnCount;
        this.gameController = new GameController(gameHeight, gameWidth, gameState);

        initialize();
    }

    /**
     * Handles generic set-up not affected by different constructors.
     */
    public void initialize()
    {
        this.playerCount = Settings.getGameSettings().getPlayerCount();
        double[] speedArray = new double[playerCount];
        for (int i = 0; i < playerCount; i++)
        {
            speedArray[i] = gameController.getSpeed(i);
        }
        this.view = new GUIView(gameController.getView(), this.playerCount, speedArray);

        view.setOnKeyPressed(this::handleKeyPressed);
        Platform.runLater(() -> view.requestFocus());

        playerProgress = new int[playerCount];
        updateList = new ArrayList<Integer>();

        gameTimer = new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                // DON'T TRUST THE FRAMERATE!!!
                long oldFrameTime = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = now;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0)
                {
                    arrayFilled = true;
                }
                if (arrayFilled)
                {
                    long elapsedNanos = now - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
                    view.updateFrameRate(frameRate);
                }
                if (!isGameOver && !isPaused)
                {
                    for (int i = 0; i < playerProgress.length; i++)
                    {
                        playerProgress[i] += gameController.getSpeed(i);
                        if (playerProgress[i] > 100)
                        {
                            updateList.add(i);
                            playerProgress[i] = 0;
                        }
                    }
                    if (!updateList.isEmpty())
                    {
                        isGameOver = gameController.executeNextStep(updateList);
                        if (isGameOver)
                        {
                            setGameOverView();
                        }
                        for (int i : updateList)
                        {
                            view.updateCurrentScore(gameController.getCurrentScore(i), i);
                            view.updateCurrentSpeed(gameController.getSpeed(i), i);
                        }
                        updateList.clear();
                    }
                }
            }
        };
        gameTimer.start();
    }

    /**
     * @return {@link com.snake.Views.GUIView GUIView} handled by the controller.
     */
    public Parent getView()
    {
        return view;
    }

    /**
     * Handles keypresses in {@link com.snake.Views.GUIView GUIView}. WASD and arrow keys used to
     * navigate in game or in menus. Escape pauses game or returns to main menu if paused or in game
     * over. Backspace forces game over, only used for debugging purposes.
     *
     * @param key a {@link javafx.scene.input.KeyEvent KeyEvent}, uses the
     *        {@link javafx.scene.input.KeyEvent#getCode() code} of the KeyEvent.
     */
    private void handleKeyPressed(KeyEvent key)
    {
        switch (key.getCode())
        {
            case Y:
            case G:
            case H:
            case J:
            case W:
            case S:
            case A:
            case D:
            case LEFT:
            case RIGHT:
            case UP:
            case DOWN:
                if (!isGameOver && !isPaused)
                {
                    gameController.handleKeyPressed(key);
                }
                else
                {
                    view.shiftFocus(key);
                }
                break;

            case ESCAPE:
                if (isPaused || isGameOver)
                {
                    handleBackButtonPressed(null);
                }
                isPaused = true;
                view.setPauseView(this);
                break;

            // debugging keybind
            case BACK_SPACE:
                if (!isGameOver && !isPaused)
                {
                    isGameOver = true;
                    setGameOverView();
                }

            default:
                break;
        }
    }

    /**
     * Leaves the pause menu and starts the game clock again.
     *
     * @param action generic button parameter, unused and null safe.
     */
    public void handleResumeButtonPressed(ActionEvent action)
    {
        isPaused = false;
        view.removePauseView();
        Platform.runLater(() -> view.requestFocus());
    }

    /**
     * Stops the animation timer to kill thread activity and goes back to the main menu.
     *
     * @param action generic button parameter, unused and null safe.
     */
    public void handleBackButtonPressed(ActionEvent action)
    {
        gameTimer.stop();
        MenuController newController = new MenuController();
        App.setRoot(newController);
    }

    /**
     * Calls the {@link com.snake.Views.GUIView#setGameOverView() setGameOverView} function in
     * GUIView.
     */
    public void setGameOverView()
    {
        view.setGameOverView(this);
    }

    /**
     * Stops the animation timer to kill thread activity and goes to the new game menu.
     *
     * @param action generic button parameter, unused and null safe.
     */
    public void handleNewGameButtonPressed(ActionEvent action)
    {
        gameTimer.stop();
        NewGameController newController = new NewGameController();
        App.setRoot(newController);
    }

    /**
     * Stops the animation timer to kill thread activity and goes to the load menu.
     *
     * @param action generic button parameter, unused and null safe.
     */
    public void handleLoadGameButtonPressed(ActionEvent action)
    {
        // WARNING: loadview and loadcontroller are still in progress, only basic framework is
        // present with no functionality
        gameTimer.stop();
        LoadController newController = new LoadController();
        App.setRoot(newController);
    }

    /**
     * Toggles game over button visibility. Calls
     * {@link com.snake.Views.GUIView#toggleGameOverButtonVisibility()
     * toggleGameOverButtonVisibility} in {@link com.snake.Views.GUIView GUIView}.
     *
     * @param action generic button parameter, unused and null safe.
     */
    public void handleHideButtonPressed(ActionEvent action)
    {
        view.toggleGameOverButtonVisibility();
    }

    public void handleSaveButtonPressed(ActionEvent action)
    {
        System.out.println("pressed save");
    }
}
