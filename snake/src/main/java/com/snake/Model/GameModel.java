package com.snake.Model;

import java.util.Timer;

public class GameModel {
    Tile[][] board;
    Vector direction;
    Timer time;
    void updateDirection(Vector _direction)
    {
        direction = _direction;
    }
    void nextState()
    {
        //TODO implement this.
    }
}
