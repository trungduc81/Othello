package com.ptit.othello.core;

import java.util.ArrayList;
import java.util.List;

public class GameRules {
    private static final int SIZE = 8;
    
    private static final int[][] DIRECTIONS = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1},
        {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };
    
    public static List<int[]> getValidMoves(Board board, int player) {
        List<int[]> validMoves = new ArrayList<>();
        int opponent = getOpponent(player);
        
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getPiece(row, col) == Board.EMPTY && isValidMove(board, row, col, player, opponent)) {
                    validMoves.add(new int[]{row, col});
                }
            }
        }
        return validMoves;
    }
    
    private static boolean isValidMove(Board board, int row, int col, int player, int opponent) {
        for (int[] dir : DIRECTIONS) {
            if (canFlipInDirection(board, row, col, dir[0], dir[1], player, opponent)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean canFlipInDirection(Board board, int row, int col, 
                                              int dRow, int dCol, int player, int opponent) {
        int r = row + dRow;
        int c = col + dCol;
        boolean hasOpponent = false;
        
        while (board.isValidPosition(r, c)) {
            int piece = board.getPiece(r, c);
            
            if (piece == Board.EMPTY) {
                return false;
            }
            
            if (piece == opponent) {
                hasOpponent = true;
            } else if (piece == player) {
                return hasOpponent;
            } else {
                return false;
            }
            
            r += dRow;
            c += dCol;
        }
        
        return false;
    }
    
    public static void makeMove(Board board, int row, int col, int player) {
        if (board.getPiece(row, col) != Board.EMPTY) {
            return;
        }
        
        board.setPiece(row, col, player);
        int opponent = getOpponent(player);
        
        for (int[] dir : DIRECTIONS) {
            flipPiecesInDirection(board, row, col, dir[0], dir[1], player, opponent);
        }
    }
    
    private static void flipPiecesInDirection(Board board, int row, int col,
                                              int dRow, int dCol, int player, int opponent) {
        List<int[]> toFlip = new ArrayList<>();
        int r = row + dRow;
        int c = col + dCol;
        
        while (board.isValidPosition(r, c)) {
            int piece = board.getPiece(r, c);
            
            if (piece == Board.EMPTY) {
                break;
            }
            
            if (piece == opponent) {
                toFlip.add(new int[]{r, c});
            } else if (piece == player) {
                for (int[] pos : toFlip) {
                    board.setPiece(pos[0], pos[1], player);
                }
                return;
            } else {
                break;
            }
            
            r += dRow;
            c += dCol;
        }
    }
    
    public static int getOpponent(int player) {
        return player == Board.BLACK ? Board.WHITE : Board.BLACK;
    }
    
    public static boolean hasValidMove(Board board, int player) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board.getPiece(row, col) == Board.EMPTY) {
                    int opponent = getOpponent(player);
                    if (isValidMove(board, row, col, player, opponent)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean isGameOver(Board board) {
        return !hasValidMove(board, Board.BLACK) && !hasValidMove(board, Board.WHITE);
    }
}