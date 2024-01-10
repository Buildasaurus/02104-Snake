package com.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import com.snake.Controllers.IController;
import com.snake.Controllers.MenuController;
import com.snake.Utils.Highscore;
import com.snake.Utils.SaveHandler;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    private static IController controller;

    @Override
    public void start(Stage stage) throws IOException {
        controller = new MenuController();
        scene = new Scene(controller.getView(), Settings.windowWidth, Settings.windowHeight);
        stage.setScene(scene);
        stage.show();
        System.out.println("Highscore at start of game is: " + Highscore.getHighscore());
        SaveHandler.createDummySaves();
        stage.setResizable(false);
    }

    public static void setRoot(IController newController) {
        controller = newController;
        scene.setRoot(controller.getView());
    }

    public static void main(String[] args) {
        launch();
    }

    public void stop()
    {
        System.out.println(controller);
        System.out.println("closing app");
    }
}
