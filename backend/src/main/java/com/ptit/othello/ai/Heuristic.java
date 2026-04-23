package com.ptit.othello.ai;

import com.ptit.othello.core.Board;
import com.ptit.othello.core.GameRules;

public class Heuristic {
    private static final int SIZE = 8;
    
    private static final int[][] POSITION_WEIGHTS = {
        { 100, -20,  10,   5,   5,  10, -20, 100 },
        { -20, -50,  -2,  -2,  -2,  -2, -50, -20 },
        {  10,  -2,   5,   1,   1,   5,  -2,  10 },
        {   5,  -2,   1,   1,   1,   1,  -2,   5 },
        {   5,  -2,   1,   1,   1,   1,  -2,   5 },
        {  10,  -2,   5,   1,   1,   5,  -2,  10 },
        { -20, -50,  -2,  -2,  -2,  -2, -50, -20 },
        { 100, -20,  10,   5,   5,  10, -20, 100 }
    };
    
    public static int evaluatePosition(Board board, int player) {
        return positionScore(board, player) + 
               pieceDifferential(board, player) + 
               mobilityScore(board, player);
    }
    
    private static int positionScore(Board board, int player) {
        int score = 0;
        int opponent = GameRules.getOpponent(player);
        
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int piece = board.getPiece(row, col);
                if (piece == player) {
                    score += POSITION_WEIGHTS[row][col];
                } else if (piece == opponent) {
                    score -= POSITION_WEIGHTS[row][col];
                }
            }
        }
        
        return score;
    }
    
    private static int pieceDifferential(Board board, int player) {
        int playerPieces = board.countPieces(player);
        int opponentPieces = board.countPieces(GameRules.getOpponent(player));
        
        return (playerPieces - opponentPieces) * 2;
    }
    
    private static int mobilityScore(Board board, int player) {
        int playerMoves = GameRules.getValidMoves(board, player).size();
        int opponentMoves = GameRules.getValidMoves(board, GameRules.getOpponent(player)).size();
        
        if (playerMoves + opponentMoves == 0) {
            return 0;
        }
        
        return (playerMoves - opponentMoves) * 5;
    }
    
    public static int evaluateEndGame(Board board, int player) {
        int playerPieces = board.countPieces(player);
        int opponentPieces = board.countPieces(GameRules.getOpponent(player));
        
        if (playerPieces > opponentPieces) {
            return 10000;
        } else if (playerPieces < opponentPieces) {
            return -10000;
        } else {
            return 0;
        }
    }
}