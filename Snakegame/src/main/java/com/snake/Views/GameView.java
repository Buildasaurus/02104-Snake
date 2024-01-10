package com.snake.Views;

import java.util.ArrayList;
import com.snake.Settings;
import com.snake.Model.Tile;
import com.snake.Model.Vector;
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

public class GameView extends GridPane
{
    int rowCount;
    int columnCount;
    int height;
    int width;
    int extraVisionDepth = 2;
    Node[][] nodes;

    /**
     * Initialises the board with a height and width.
     *
     * @param rowCount The amount of rows (height) that will be drawn.
     * @param columnCount The amount of columns (width) that will be drawn.
     * @param height The height of the board in pixels
     * @param width The width of the board in pixels
     */
    public GameView(int height, int width, Tile[][] board)
    {
        this.height = height;
        this.width = width;
        this.rowCount = Settings.rowCount + extraVisionDepth * 2; // +2 because this is for when
                                                                  // "extravision" is
        // turned on
        this.columnCount = Settings.columnCount + extraVisionDepth * 2;
        initialize(board);
    }

    boolean firstUdpdate = true;

    /**
     * Initializes the board with the height and width stored.
     */
    public void initialize(Tile[][] board)
    {
        nodes = new Node[rowCount][columnCount];
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
        // Save all nodes for later easy access.
        for (Node node : this.getChildren())
        {
            nodes[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
            refreshPoint(new Vector(GridPane.getColumnIndex(node), GridPane.getRowIndex(node)),
                    board, Settings.getGameSettings().getExtraVision());

        }
    }

    public void update(Tile[][] board, ArrayList<Vector> relevantPositions)
    {
        // This variable might change from frame to frame, so
        boolean extraVision = Settings.getGameSettings().getExtraVision();
        System.out.println("update");
        if (board != null)
        {
            for (Vector position : relevantPositions)
            {
                updatePoint(position, board, extraVision);
            }
        }
        else
        {
            System.out.println("board isnull");

        }
    }


    public void refreshPoint(Vector position, Tile[][] board, boolean extraVision)
    {
        Pane pane = (Pane) nodes[position.y][position.x];
        int row = getRowIndex(pane) - extraVisionDepth;
        int column = getColumnIndex(pane) - extraVisionDepth;


        // if is on edge, set visible to whether extravision is true. Also these squares are
        // special
        if (row < 0 || row > rowCount - 1 - extraVisionDepth * 2 || column < 0
                || column > columnCount - 1 - extraVisionDepth * 2)
        {
            column = column < 0 ? columnCount - extraVisionDepth * 2 + column : column;
            column = column > columnCount - 1 - extraVisionDepth * 2
                    ? column - (columnCount - extraVisionDepth * 2)
                    : column;

            row = row < 0 ? rowCount - extraVisionDepth * 2 + row : row;
            row = row > rowCount - 1 - extraVisionDepth * 2
                    ? row - (rowCount - extraVisionDepth * 2)
                    : row;
            pane.setVisible(extraVision);
            pane.setOpacity(0.6);
        }
        Tile tile = board[row][column];

        pane.getChildren().clear();
        // Label text = new Label(
        // getColumnIndex(pane) + " " + getRowIndex(pane) + " : " + column + " " + row);
        // pane.getChildren().add(text);
        if (tile != null)
        {
            ImageView imageView = tile.getImage();
            imageView.setFitWidth(height / rowCount);
            imageView.setPreserveRatio(true);

            pane.getChildren().add(imageView);
        }
    }

    public void updatePoint(Vector position, Tile[][] board, boolean extraVision)
    {
        Vector gridCoordinate = position.add(extraVisionDepth);
        ArrayList<Vector> gridPositions = new ArrayList<Vector>();
        gridPositions.add(gridCoordinate);
        if (extraVision)
        {
            // if is on edge, Several panes should be refreshed, if extravision is true
            if (position.x < extraVisionDepth)
            {
                gridPositions
                        .add(new Vector(Settings.columnCount + gridCoordinate.x, gridCoordinate.y));
            }
            if (position.y < extraVisionDepth)
            {
                gridPositions
                        .add(new Vector(gridCoordinate.x, Settings.rowCount + gridCoordinate.y));
            }
            if (position.y >= Settings.rowCount - extraVisionDepth)
            {
                gridPositions
                        .add(new Vector(gridCoordinate.x, gridCoordinate.y - Settings.rowCount));
            }
            if (position.x >= Settings.columnCount - extraVisionDepth)
            {
                gridPositions
                        .add(new Vector(gridCoordinate.x - Settings.columnCount, gridCoordinate.y));

            }
            if (position.x < extraVisionDepth && position.y < extraVisionDepth)
            {
                gridPositions.add(new Vector(Settings.columnCount + gridCoordinate.x,
                        gridCoordinate.y - Settings.rowCount));
            }
            if (position.y >= Settings.rowCount - extraVisionDepth
                    && position.x >= Settings.columnCount - extraVisionDepth)
            {
                gridPositions.add(new Vector(gridCoordinate.x - Settings.columnCount,
                        gridCoordinate.y - Settings.rowCount));
            }



        }

        for (Vector vector : gridPositions)
        {
            refreshPoint(vector, board, extraVision);
        }
    }
}
