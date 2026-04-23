package com.ptit.othello.core;

public class Board {
    private static final int SIZE = 8;
    private int[][] board;
    
    public static final int EMPTY = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    
    public Board() {
        board = new int[SIZE][SIZE];
        initializeBoard();
    }
    // Restart trạng thái bàn cờ 
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;
    }
    
    // Lấy nước đi 
    public int getPiece(int row, int col) {
        if (isValidPosition(row, col)) {
            return board[row][col];
        }
        return EMPTY;
    }
    
    public void setPiece(int row, int col, int player) {
        if (isValidPosition(row, col)) {
            board[row][col] = player;
        }
    }
    
    // Nước đi hợp lệ 
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }
    
    public int[][] getBoardCopy() {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    
    public void setBoardFromCopy(int[][] boardCopy) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = boardCopy[i][j];
            }
        }
    }
    
    public int getSize() {
        return SIZE;
    }
    // Đếm quân 
    public int countPieces(int player) {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == player) {
                    count++;
                }
            }
        }
        return count;
    }
}