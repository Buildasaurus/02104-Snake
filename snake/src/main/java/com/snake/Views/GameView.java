package com.snake.Views;

import java.util.Set;
import com.snake.Model.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    int height;
    int width;

    /**
     * Initialises the board with a height and width.
     *
     * @param rowCount The amount of rows (height) that will be drawn.
     * @param columnCount The amount of columns (width) that will be drawn.
     * @param height The height of the board in pixels
     * @param width The width of the board in pixels
     */
    public GameView(int rowCount, int columnCount, int height, int width)
    {
        this.height = height;
        this.width = width;
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

        for (int i = 0; i < columnCount; i++)
        {
            ColumnConstraints column = new ColumnConstraints(width / columnCount);
            this.getColumnConstraints().add(column);
        }
        for (int i = 0; i < rowCount; i++)
        {
            RowConstraints row = new RowConstraints(height / rowCount);
            this.getRowConstraints().add(row);
        }
    }

    public void update(Tile[][] board)
    {
        for (int row = 0; row < rowCount; row++)
        {
            for (int column = 0; column < columnCount; column++)
            {
                // Text text = new Text(x + " " + y);
                // this.add(text, x, 7 - y);
                if (board[row][column] != null)
                {
                    ImageView imageView = new ImageView(board[row][column].getImage());
                    imageView.setFitWidth(height / rowCount);
                    imageView.setPreserveRatio(true);
                    this.add(imageView, column, rowCount - row);
                }
            }
        }
    }
}
