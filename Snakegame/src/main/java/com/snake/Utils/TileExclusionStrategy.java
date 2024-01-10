package com.snake.Utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.snake.Model.Fruit;
import com.snake.Model.SnakeTile;
import com.snake.Model.Tile;

public class TileExclusionStrategy implements ExclusionStrategy {
    public boolean shouldSkipClass(Class<?> clazz) {
        if (clazz.equals(Tile.class) && !clazz.equals(SnakeTile.class) && !clazz.equals(Fruit.class)) {
            return true;
        }
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        return false;
    }
}
