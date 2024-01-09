package com.snake.Views;

import com.snake.Settings;
import com.snake.Model.LevelGenerator;
import com.snake.Model.Tile;
import com.snake.Model.Wall;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
        this.rowCount = Settings.rowCount + 2; // +2 because this is for when "extravision" is
                                               // turned on
        this.columnCount = Settings.columnCount + 2;
        initialize();
    }

    boolean firstUdpdate = true;

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

        BackgroundFill bgFillDark =
                new BackgroundFill(new Color(0, 0.6, 0.1, 1), null, getInsets());
        BackgroundFill bgFillLight =
                new BackgroundFill(new Color(0, 0.5, 0.1, 1), null, getInsets());
        Background bgDark = new Background(bgFillDark);
        Background bgLight = new Background(bgFillLight);
        boolean isLastDark = false;
        for (int row = 0; row < rowCount; row++)
        {
            for (int column = 0; column < columnCount; column++)
            {
                Pane bgCell = new Pane();
                if (row != 0 && column == 0)
                {
                    if (rowCount % 2 == 0)
                    {
                        isLastDark = !isLastDark;
                    }
                }
                if (isLastDark)
                {
                    bgCell.setBackground(bgLight);
                }
                else
                {
                    bgCell.setBackground(bgDark);
                }
                isLastDark = !isLastDark;
                this.add(bgCell, column, row);
            }
        }
    }

    public void update(Tile[][] board)
    {

        // This variable might change from frame to frame, so
        boolean extraVision = Settings.getGameSettings().getExtraVision();
        System.out.println("update");
        if (board != null)
        {
            ObservableList<Node> panes = this.getChildren();

            for (Node node : panes)
            {
                Pane pane = (Pane) node;
                int row = GridPane.getRowIndex(pane) - 1;
                int column = GridPane.getColumnIndex(pane) - 1;


                // if is on edge, set visible to whether extravision is true. Also these squares are
                // special
                if (row == -1 || row == rowCount - 2 || column == -1 || column == columnCount - 2)
                {

                    column = column == -1 ? columnCount - 3 : column;
                    column = column == columnCount - 2 ? 0 : column;

                    row = row == -1 ? rowCount - 3 : row;
                    row = row == rowCount - 2 ? 0 : row;
                }
                Tile tile = board[rowCount - 3 - row][column];

                if (tile instanceof Wall && !firstUdpdate)
                {
                    firstUdpdate = false;
                    continue;
                }
                pane.getChildren().clear();
                //Label text = new Label(getColumnIndex(pane) + " " + getRowIndex(pane) + " : " + column + " " + row);
                //pane.getChildren().add(text);
                if (tile != null)
                {
                    ImageView imageView = tile.getImage();
                    imageView.setFitWidth(height / rowCount);
                    imageView.setPreserveRatio(true);

                    pane.getChildren().add(imageView);
                }
            }
        }
        else
        {
            System.out.println("board isnull");

        }
    }
}
