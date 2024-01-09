package com.snake.Utils;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;
import com.snake.Model.SnakeTile;
import com.snake.Model.Tile;

public class TileInstanceCreator implements InstanceCreator<Tile> {
    public Tile createInstance(Type type) {
        return new SnakeTile(null, null, null, 0);
    }
}
