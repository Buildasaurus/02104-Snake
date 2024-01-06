package com.snake.Views;

import com.snake.Settings;
import com.snake.Controllers.NewGameController;
import com.snake.Model.GameSettings.GameMode;
import com.snake.Model.GameSettings.Level;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class NewGameView extends GridPane
{
    NewGameController controller;

    public NewGameView(NewGameController controller, int[] playerCounts)
    {
        this.controller = controller;
        initialize(playerCounts);
    }

    private void initialize(int[] playerCounts)
    {
        for (int i = 0; i < 3; i++)
        {
            ColumnConstraints column = new ColumnConstraints(Settings.windowWidth / 3);
            RowConstraints row = new RowConstraints(Settings.windowHeight / 3);
            getColumnConstraints().add(column);
            getRowConstraints().add(row);
        }


        // player count setting
        ComboBox<Integer> playerCountDropDown = new ComboBox<Integer>();
        for (int i : playerCounts)
        {
            playerCountDropDown.getItems().add(i);
        }
        add(playerCountDropDown, 0, 0);
        playerCountDropDown.getSelectionModel()
                .select(Integer.valueOf(controller.getPlayerCount()));
        // listen for changes
        playerCountDropDown.getSelectionModel().selectedItemProperty().addListener((s) -> {
            System.out.println(playerCountDropDown.getSelectionModel().getSelectedItem());
            System.out.println(controller
                    .setPlayerCount(playerCountDropDown.getSelectionModel().getSelectedItem()));
        });


        // gamemode setting
        ComboBox<GameMode> gameModeDropDown = new ComboBox<GameMode>();
        for (GameMode gameMode : GameMode.values())
        {
            gameModeDropDown.getItems().add(gameMode);
        }
        add(gameModeDropDown, 1, 0);
        gameModeDropDown.getSelectionModel().select(controller.getGameMode()); // inital chosen item
        // listen for changes
        gameModeDropDown.getSelectionModel().selectedItemProperty().addListener((s) -> {
            System.out.println(gameModeDropDown.getSelectionModel().getSelectedItem());
            controller.setGameMode(gameModeDropDown.getSelectionModel().getSelectedItem());
        });



        // Level setting
        ComboBox<Level> levelModeDropDown = new ComboBox<Level>();
        for (Level level : Level.values())
        {
            levelModeDropDown.getItems().add(level);
        }
        add(levelModeDropDown, 2, 0);
        levelModeDropDown.getSelectionModel().select(controller.getLevel());
        levelModeDropDown.getSelectionModel().selectedItemProperty().addListener((s) -> {
            controller.setLevel(levelModeDropDown.getSelectionModel().getSelectedItem());
        });



        // Start game
        Button startGame = new Button("Start game");
        add(startGame, 1, 1);
        startGame.setOnAction(controller::handlePlayButtonPressed);

        // Center align
        for (Node node : getChildren())
        {
            setHalignment(node, HPos.CENTER); // To align horizontally in the cell
            setValignment(node, VPos.CENTER); // To align vertically in the cell
        }

    }


}
