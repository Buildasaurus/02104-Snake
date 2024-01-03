package com.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import com.snake.Controllers.GameController;
import com.snake.Controllers.IController;
import com.snake.Controllers.MenuController;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static IController controller;

    @Override
    public void start(Stage stage) throws IOException {
        controller = new GameController(20, 20);
        scene = new Scene(controller.getView(), Settings.windowWidth, Settings.windowHeight);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(IController newController) {
        controller = newController;
        scene = new Scene(controller.getView(), Settings.windowWidth, Settings.windowHeight);
    }

    public static void main(String[] args) {
        launch();
    }
}
