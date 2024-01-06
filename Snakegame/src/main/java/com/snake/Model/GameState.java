package com.snake.Model;

public class GameState {
    private Tile[][] board;
    private double acceleration;
    private Snake[] players;

    public GameState(Tile[][] board, double speed, double acceleration, Snake[] players) {
        this.board = board;
        this.acceleration = acceleration;
        this.players = players;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public Snake[] getPlayers() {
        return players;
    }
}
