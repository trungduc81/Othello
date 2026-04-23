package com.ptit.othello.api.dto;

import java.util.List;

public class GameStateResponse {
    private int[][] board;
    private List<int[]> validMoves;
    private int currentPlayer;
    private int blackCount;
    private int whiteCount;
    private boolean gameOver;
    
    public GameStateResponse() {}
    
    public GameStateResponse(int[][] board, List<int[]> validMoves, int currentPlayer, 
                            int blackCount, int whiteCount, boolean gameOver) {
        this.board = board;
        this.validMoves = validMoves;
        this.currentPlayer = currentPlayer;
        this.blackCount = blackCount;
        this.whiteCount = whiteCount;
        this.gameOver = gameOver;
    }
    
    public int[][] getBoard() {
        return board;
    }
    
    public void setBoard(int[][] board) {
        this.board = board;
    }
    
    public List<int[]> getValidMoves() {
        return validMoves;
    }
    
    public void setValidMoves(List<int[]> validMoves) {
        this.validMoves = validMoves;
    }
    
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    public int getBlackCount() {
        return blackCount;
    }
    
    public void setBlackCount(int blackCount) {
        this.blackCount = blackCount;
    }
    
    public int getWhiteCount() {
        return whiteCount;
    }
    
    public void setWhiteCount(int whiteCount) {
        this.whiteCount = whiteCount;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
