package com.snake.Views;

import java.net.URL;

import com.snake.OurButton;
import com.snake.Settings;
import com.snake.Controllers.NewGameController;
import com.snake.Model.GameSettings.GameMode;
import com.snake.Model.GameSettings.Level;
import javafx.geometry.HPos;
<<<<<<< Updated upstream
=======
import javafx.geometry.Insets;
>>>>>>> Stashed changes
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

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

        URL url = getClass().getResource("/com/snake/Graphics/baggrund.png");
        setStyle("-fx-background-image: url('" + url.toExternalForm() + "')");

        //background image scaling

        for (int i = 0; i < 3; i++)
        {
            ColumnConstraints column = new ColumnConstraints(Settings.windowWidth /  3);
            RowConstraints row = new RowConstraints(Settings.windowHeight / 3);
            getColumnConstraints().add(column);
            getRowConstraints().add(row);
        }

        //playerselection hbox

<<<<<<< Updated upstream
        // player count setting
        /*ComboBox<Integer> playerCountDropDown = new ComboBox<Integer>();
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
        })*/

        //playerselection hbox

        CustomHBox playerselection = new CustomHBox();

        SelectionButton singlePlayer = new SelectionButton("1");
        SelectionButton twoPlayer = new SelectionButton("2");
        SelectionButton threePlayer = new SelectionButton("3");

        //set button actions

        singlePlayer.setOnAction(controller::handlesinglePlayerButtonPressed);
        twoPlayer.setOnAction(controller::handletwoPlayerButtonPressed);
        threePlayer.setOnAction(controller::handlethreePlayerButtonPressed);
        
        playerselection.getChildren().addAll(singlePlayer, twoPlayer, threePlayer);
=======
        CustomHBox playerselection = new CustomHBox();

        for (int i : playerCounts){
            SelectionButton button = new SelectionButton(Integer.toString(i));
            button.setOnAction(controller::handlePlayerCounts);
            playerselection.getChildren().add(button);
        }
>>>>>>> Stashed changes

        //gamemode hbox

        CustomHBox gamemode = new CustomHBox();

<<<<<<< Updated upstream
        SelectionButton easy = new SelectionButton("Easy");
        SelectionButton normal = new SelectionButton("Normal");
        SelectionButton hard = new SelectionButton("Hard");
        SelectionButton insane = new SelectionButton("Insane");

        easy.setOnAction(controller::handleeasyButtonPressed);
        normal.setOnAction(controller::handlenormalButtonPressed);
        hard.setOnAction(controller::handlehardButtonPressed);
        insane.setOnAction(controller::handleinsaneButtonPressed);

        gamemode.getChildren().addAll(easy, normal, hard, insane);

        CustomHBox maptype = new CustomHBox();

        SelectionButton empty = new SelectionButton("Emptt");
        SelectionButton random = new SelectionButton("Random");

        empty.setOnAction(controller::handleemptyButtonPressed);
        random.setOnAction(controller::handlerandomButtonPressed);

        maptype.getChildren().addAll(empty,random);

        VBox vbox = new VBox(30);

        getChildren().add(vbox);

        vbox.getChildren().addAll(playerselection,gamemode);
        

        /*
        // gamemode setting
         ComboBox<GameMode> gameModeDropDown = new ComboBox<GameMode>();
        for (GameMode gameMode : GameMode.values())
        {
            gameModeDropDown.getItems().add(gameMode);
=======
        for (GameMode mode : GameMode.values()){
            SelectionButton button = new SelectionButton(mode.toString());
            button.setOnAction(controller::handlegameMode);
            gamemode.getChildren().add(button);
>>>>>>> Stashed changes
        }

        //maptype hbox

        CustomHBox maptype = new CustomHBox();

        for (Level level : Level.values()){
            SelectionButton button = new SelectionButton(level.toString());
            button.setOnAction(controller::handlelevel);
            maptype.getChildren().add(button);
        }
<<<<<<< Updated upstream
        add(levelModeDropDown, 2, 0);
        levelModeDropDown.getSelectionModel().select(controller.getLevel());
        levelModeDropDown.getSelectionModel().selectedItemProperty().addListener((s) -> {
            controller.setLevel(levelModeDropDown.getSelectionModel().getSelectedItem());
        }); 
        */
=======

        VBox vbox = new VBox(10);

        setMargin(vbox,new Insets(500,100,100,0));

        vbox.getChildren().addAll(playerselection,gamemode,maptype);

        getChildren().add(vbox);

>>>>>>> Stashed changes


        // Start game
        OurButton startGame = new OurButton("Start game");
        add(startGame, 1, 1);
        startGame.setOnAction(controller::handlePlayButtonPressed);

        startGame.setTranslateY(50);
        //getChildren().add(startGame);

        // Center align
        for (Node node : getChildren())
        {
            setHalignment(node, HPos.CENTER); // To align horizontally in the cell
            setValignment(node, VPos.CENTER); // To align vertically in the cell
        }
    }

    }
