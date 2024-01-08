package com.snake.Views;

import com.snake.Settings;
import com.snake.Model.Tile;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

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
    public GameView(int height, int width)
    {
        this.height = height;
        this.width = width;
        this.rowCount = Settings.rowCount;
        this.columnCount = Settings.columnCount;
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

        BackgroundFill bgFillDark = new BackgroundFill(new Color(0,0.6,0.1,1), null, getInsets());
        BackgroundFill bgFillLight = new BackgroundFill(new Color(0,0.5,0.1,1), null, getInsets());
        Background bgDark = new Background(bgFillDark);
        Background bgLight = new Background(bgFillLight);
        boolean isLastDark = false;
        for (int i = 0; i < rowCount; i++) {
            for (int n = 0; n < columnCount; n++) {
                Pane bgCell = new Pane();
                if (i != 0 && n == 0) {
                    if (rowCount % 2 == 0) {
                        if (isLastDark) {
                            isLastDark = false;
                        } else {
                            isLastDark = true;
                        }
                    }
                }
                if (isLastDark) {
                    bgCell.setBackground(bgLight);
                    isLastDark = false;
                } else {
                    bgCell.setBackground(bgDark);
                    isLastDark = true;
                }
                this.add(bgCell, i, n);
            }
        }
    }

    public void update(Tile[][] board)
    {
        if (board != null)
        {
            //this.getChildren().clear(); // Clear the current view

            /* */
            ObservableList<Node> panes = this.getChildren();

            for (Node node : panes) {
                Pane pane = (Pane) node;
                pane.getChildren().clear();
                int row = GridPane.getRowIndex(pane);
                int column = GridPane.getColumnIndex(pane);
                if (board[rowCount - 1 - row][column] != null) {
                    ImageView imageView = board[rowCount - 1 - row][column].getImage();
                    imageView.setFitWidth(height / rowCount);
                    imageView.setPreserveRatio(true);
                    pane.getChildren().add(imageView);
                }
            }

            /*
            for (int row = 0; row < rowCount; row++)
            {
                for (int column = 0; column < columnCount; column++)
                {
                    // Text text = new Text(x + " " + y);
                    // this.add(text, x, 7 - y);
                    if (board[row][column] != null)
                    {
                        ImageView imageView = board[row][column].getImage();
                        imageView.setFitWidth(height / rowCount);
                        imageView.setPreserveRatio(true);
                        this.add(imageView, column, rowCount - row);
                    }
                }
            }
            */
        }
        else
        {
            System.out.println("board isnull");

        }

    }
}