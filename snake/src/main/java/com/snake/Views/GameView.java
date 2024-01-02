package com.snake.Views;

import com.snake.Model.Tile;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class GameView extends GridPane
{
    int rowCount;
    int columnCount;

    /**
     * Initialises the board with a height and width.
     * @param rowCount The amount of rows (height) that will be drawn.
     * @param columnCount The amount of columns (width) that will be drawn.
     */
    public GameView(int rowCount, int columnCount)
    {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        initialize();
    }

    /**
     * Initializes the board with the height and width stored.
     */
    public void initialize()
    {
        this.getColumnConstraints().clear();
        this.getRowConstraints().clear();

        for (int i = 0; i < 8; i++)
        {
            ColumnConstraints column = new ColumnConstraints(2);
            RowConstraints row = new RowConstraints(2);
            this.getColumnConstraints().add(column);
            this.getRowConstraints().add(row);
        }


    }

    public void update(Tile[][] board)
    {

    }

}
