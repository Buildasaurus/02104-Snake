package com.snake.Controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.snake.App;
import com.snake.Settings;
import com.snake.Model.GameSettings;
import com.snake.Model.GameState;
import com.snake.Model.Save;
import com.snake.Utils.SaveHandler;
import com.snake.Views.GUIView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;

// Made by Marinus Juhl

/**
 * Keeps track of an {@link javafx.animation.AnimationTimer AnimationTimer} used to compute game
 * logic via {@link GameController}. Main controller of the main view {@link com.snake.Views.GUIView
 * GUIView}. Handles {@link #handleKeyPressed(KeyEvent) keypresses} of the view. Handles button
 * actions of the {@link com.snake.Views.GUIView GUIView} subviews: {@link com.snake.Views.PauseView
 * PauseView}, {@link com.snake.Views.GameOverView GameOverView}, and
 * {@link com.snake.Views.SaveView SaveView}, as well as switching to those views.
 */
public class GUIController implements IController
{
    private GameController gameController;

    private GUIView view;

    private Timer gameTimer;
    private int[] playerProgress;
    private ArrayList<Integer> updateList;

    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    private boolean isGameOver = false;
    private boolean isPaused = false;
    private boolean isSaving = false;

    private int playerCount;

    int margin = 175;



    public GUIController()
    {

        int height = Settings.windowHeight - margin;
        int width = Settings.windowWidth - margin;
        double potentialBoxHeight =
                ((double) height) / (Settings.getGameSettings().getExtendedRowCount());
        double potentialBoxWidth =
                ((double) width) / (Settings.getGameSettings().getExtendedColumnCount());
        double boxDimension = Math.min(potentialBoxHeight, potentialBoxWidth);
        int gameHeight =
                (int) Math.round(boxDimension * Settings.getGameSettings().getExtendedRowCount());
        int gameWidth = (int) Math
                .round(boxDimension * Settings.getGameSettings().getExtendedColumnCount());
        System.out.println(gameHeight + " " + gameWidth);
        this.gameController = new GameController(gameWidth, gameHeight);

        initialize();
    }

    /**
     * Constructor used for loading a save state.
     *
     * @param gameState
     */
    public GUIController(GameState gameState)
    {

        int height = Settings.windowHeight - margin;
        int width = Settings.windowWidth - margin;
        double potentialBoxHeight =
                ((double) height) / (Settings.getGameSettings().getExtendedRowCount());
        double potentialBoxWidth =
                ((double) width) / (Settings.getGameSettings().getExtendedColumnCount());
        double boxDimension = Math.min(potentialBoxHeight, potentialBoxWidth);
        int gameHeight =
                (int) Math.round(boxDimension * Settings.getGameSettings().getExtendedRowCount());
        int gameWidth = (int) Math
                .round(boxDimension * Settings.getGameSettings().getExtendedColumnCount());
        System.out.println(gameHeight + " " + gameWidth);
        this.gameController = new GameController(gameWidth, gameHeight, gameState);

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

        TimerTask timeLoop = new TimerTask()
        {
            public void run()
            {
                // DON'T TRUST THE FRAMERATE!!!
                Platform.runLater(() -> {
                    long oldFrameTime = frameTimes[frameTimeIndex];
                    frameTimes[frameTimeIndex] = System.currentTimeMillis();
                    frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                    if (frameTimeIndex == 0)
                    {
                        arrayFilled = true;
                    }
                    if (arrayFilled)
                    {
                        long elapsedMillis = System.currentTimeMillis() - oldFrameTime;
                        long elapsedNanosPerFrame = elapsedMillis / frameTimes.length;
                        double frameRate = 1_000.0 / elapsedNanosPerFrame;
                        view.updateFrameRate(frameRate);
                    }
                    if (!isGameOver && !isPaused && !isSaving)
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
                });
            }
        };

        gameTimer = new Timer();
        App.setTimer(gameTimer);
        gameTimer.scheduleAtFixedRate(timeLoop, 300, 16);
    }

    /**
     * @return {@link com.snake.Views.GUIView GUIView} handled by the controller.
     */
    public Parent getView()
    {
        return view;
    }

    /**
     * Handles keypresses in {@link com.snake.Views.GUIView GUIView}. WASD, arrow keys, and YGHJ are
     * sent to the {@link com.snake.Controllers.GameController#handleKeyPressed(KeyEvent)
     * handleKeyPressed} method in {@link com.snake.Controllers.GameController gamecontroller}.
     * Escape pauses game or returns to main menu if paused or in gameover. Backspace forces
     * gameover, only used for debugging purposes.
     *
     * @param key a {@link javafx.scene.input.KeyEvent KeyEvent}, uses the
     *        {@link javafx.scene.input.KeyEvent#getCode() code} of the KeyEvent.
     */
    private void handleKeyPressed(KeyEvent key)
    {
        switch (key.getCode())
        {
            case W:
            case S:
            case A:
            case D:
            case Y:
            case G:
            case H:
            case J:
            case LEFT:
            case RIGHT:
            case UP:
            case DOWN:
                gameController.handleKeyPressed(key);
                break;

            case ESCAPE:
                if (isPaused || isGameOver)
                {
                    handleBackButtonPressed(null);
                }
                else if (isSaving)
                {
                    handlePauseButtonPressed(null);
                }
                else
                {
                    isPaused = true;
                    view.setPauseView(this);
                }
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
        if (isPaused)
        {
            handleSaving(0);
        }
        gameTimer.cancel();
        MenuController newController = new MenuController();
        App.setRoot(newController);
    }

    /**
     * Calls the {@link com.snake.Views.GUIView#setGameOverView() setGameOverView} function in
     * {@link com.snake.Views.GUIView GUIView}.
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
        gameTimer.cancel();
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
        gameTimer.cancel();
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

    /**
     * Checks if the file index is empty or not. If empty calls
     * {@link com.snake.Views.GUIView#showAlert() showAlert}, else calls {@link #handleSaving(int)
     * handleSaving} and passes along the index.
     *
     * @param index
     */
    public void handleSaveButtonPressed(int index)
    {
        if (SaveHandler.isSavePresent(index))
        {
            view.showAlert();
        }
        else
        {
            handleSaving(index);
        }
    }

    /**
     * Creates Save object and calls {@link com.snake.Utils.SaveHandler#writeSave(Save, int)
     * writeSave}. Gets savename from current date. Gets the gamesettings from
     * {@link com.snake.Settings#getGameSettings() getGameSettings}. Gets the gamestate from
     * {@link com.snake.Controllers.GameController#getGameState() getGameState}.
     *
     * @param index
     */
    public void handleSaving(int index)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM\n  HH mm");
        LocalDateTime now = LocalDateTime.now();
        String name = dtf.format(now);

        GameSettings gameSettings = Settings.getGameSettings();
        GameState gameState = gameController.getGameState();

        Save save = new Save(name, gameSettings, gameState);
        SaveHandler.writeSave(save, index);

        if (0 < index)
        {
            view.updateSaveNames(SaveHandler.getSaveNames());
        }
    }

    /**
     * Calls the {@link com.snake.Views.GUIView#setSaveView(GUIController, String[]) setSaveView}
     * method in {@link com.snake.Views.GUIView GUIView}.
     *
     * @param action generic button parameter, unused and null safe.
     */
    public void handleSaveMenuButtonPressed(ActionEvent action)
    {
        isSaving = true;
        isPaused = false;
        view.setSaveView(this, SaveHandler.getSaveNames());
    }

    /**
     * Calls the {@link com.snake.Views.GUIView#removeSaveView(GUIController) removeSaveView} method
     * in {@link com.snake.Views.GUIView GUIView}.
     *
     * @param action generic button parameter, unused and null safe.
     */
    public void handlePauseButtonPressed(ActionEvent action)
    {
        isPaused = true;
        isSaving = false;
        view.removeSaveView(this);
    }

    public GameController getGameController()
    {
        return gameController;
    }
}
