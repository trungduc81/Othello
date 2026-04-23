package com.ptit.othello.ai;

import com.ptit.othello.core.Board;
import com.ptit.othello.core.GameRules;
import java.util.List;

public class Minimax {
    private int maxDepth = 6; // Độ sâu tìm kiếm
    private int aiPlayer;
    private int humanPlayer;
    
    public Minimax(int aiPlayer) {
        this.aiPlayer = aiPlayer;
        this.humanPlayer = GameRules.getOpponent(aiPlayer);
    }
    
    public int[] findBestMove(Board board) {
        Board boardCopy = new Board();
        boardCopy.setBoardFromCopy(board.getBoardCopy());
        
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;
        List<int[]> validMoves = GameRules.getValidMoves(boardCopy, aiPlayer);
        
        if (validMoves.isEmpty()) {
            return null;
        }
        
        for (int[] move : validMoves) {
            Board testBoard = new Board();
            testBoard.setBoardFromCopy(boardCopy.getBoardCopy());
            
            GameRules.makeMove(testBoard, move[0], move[1], aiPlayer);
            int score = minimax(testBoard, maxDepth - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        
        return bestMove;
    }
    
    // Minimax với Alpha-Beta pruning
    private int minimax(Board board, int depth, boolean isMaximizing, int alpha, int beta) {
        // Kiểm tra kết thúc game
        if (depth == 0 || GameRules.isGameOver(board)) {
            return evaluate(board, depth);
        }
        
        int currentPlayer = isMaximizing ? aiPlayer : humanPlayer;
        List<int[]> validMoves = GameRules.getValidMoves(board, currentPlayer);
        
        // Nếu không có nước đi hợp lệ
        if (validMoves.isEmpty()) {
            int opponent = GameRules.getOpponent(currentPlayer);
            List<int[]> opponentMoves = GameRules.getValidMoves(board, opponent);
            
            if (opponentMoves.isEmpty()) {
                // Game kết thúc
                return Heuristic.evaluateEndGame(board, aiPlayer);
            } else {
                // Opponent tiếp tục chơi
                return minimax(board, depth - 1, !isMaximizing, alpha, beta);
            }
        }
        
        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            
            for (int[] move : validMoves) {
                Board newBoard = new Board();
                newBoard.setBoardFromCopy(board.getBoardCopy());
                
                GameRules.makeMove(newBoard, move[0], move[1], aiPlayer);
                int eval = minimax(newBoard, depth - 1, false, alpha, beta);
                
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                
                if (beta <= alpha) {
                    break; // Alpha-Beta pruning
                }
            }
            
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            
            for (int[] move : validMoves) {
                Board newBoard = new Board();
                newBoard.setBoardFromCopy(board.getBoardCopy());
                
                GameRules.makeMove(newBoard, move[0], move[1], humanPlayer);
                int eval = minimax(newBoard, depth - 1, true, alpha, beta);
                
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                
                if (beta <= alpha) {
                    break; // Alpha-Beta pruning
                }
            }
            
            return minEval;
        }
    }
    
    private int evaluate(Board board, int depth) {
        if (depth == 0 && GameRules.isGameOver(board)) {
            return Heuristic.evaluateEndGame(board, aiPlayer);
        }
        
        if (GameRules.isGameOver(board)) {
            return Heuristic.evaluateEndGame(board, aiPlayer);
        }
        
        return Heuristic.evaluatePosition(board, aiPlayer);
    }
    
    public void setMaxDepth(int depth) {
        this.maxDepth = depth;
    }
}