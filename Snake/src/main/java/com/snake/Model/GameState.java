package com.snake.Model;

public class GameState {
    private Tile[][] board;
    private double speed;
    private double acceleration;
    private Snake[] players;

    public GameState(Tile[][] board, double speed, double acceleration, Snake[] players) {
        this.board = board;
        this.speed = speed;
        this.acceleration = acceleration;
        this.players = players;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public Snake[] getPlayers() {
        return players;
    }
}
