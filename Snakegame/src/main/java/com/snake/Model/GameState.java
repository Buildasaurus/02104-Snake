package com.snake.Model;

public class GameState {
    private Tile[][] board;
    private Snake[] players;

    public GameState(Tile[][] board, Snake[] players) {
        this.board = board;
        this.players = players;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Snake[] getPlayers() {
        return players;
    }
}
