package com.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;

import com.snake.Controllers.IController;
import com.snake.Controllers.MenuController;
import com.snake.Utils.Highscore;
import com.snake.Utils.Resources;
import com.snake.Utils.SaveHandler;

/**
 * JavaFX App
 */
public class App extends Application
{
    private static Scene scene;
    private static IController controller;
    private static Timer gameTimer;

    @Override
    public void start(Stage stage) throws IOException
    {
        Resources.playSoundByName("BackgroundMusic",0.03, true);
        controller = new MenuController();
        scene = new Scene(controller.getView(), Settings.windowWidth, Settings.windowHeight);
        stage.setScene(scene);
        stage.show();
        System.out.println("Highscore at start of game is: " + Highscore.getHighscore());
        SaveHandler.createDummySaves();
        stage.setResizable(false);
    }

    public static void setRoot(IController newController)
    {
        controller = newController;
        scene.setRoot(controller.getView());
    }

    public static void main(String[] args)
    {
        try
        {
            int width = Integer.parseInt(args[0]);
            int height = Integer.parseInt(args[1]);
            Settings.getGameSettings().setColumnCount(width);
            Settings.getGameSettings().setRowCount(height);
        }
        catch (Exception e)
        {
            System.out.println("Incorrect/no args, defaulting to 30x30 board.");
            System.out.println(
                    "Expected: two integer values width, height, that  are between 5 and 100");
        }
        launch();
    }

    public void stop()
    {
        gameTimer.cancel();
        System.out.println(controller);
        System.out.println("closing app");
    }

    public static void setTimer(Timer timer) {
        gameTimer = timer;
    }
}
