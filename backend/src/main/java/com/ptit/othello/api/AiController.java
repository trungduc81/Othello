package com.ptit.othello.api;

import com.ptit.othello.ai.Minimax;
import com.ptit.othello.api.dto.GameStateResponse;
import com.ptit.othello.api.dto.MoveRequest;
import com.ptit.othello.core.Board;
import com.ptit.othello.core.GameRules;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AiController {
    // Trả về nước đi của máy 
    @PostMapping("/ai/calculate-move")
    public Map<String, Object> getAiMove(@RequestBody MoveRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Board board = new Board();
            board.setBoardFromCopy(request.getBoard());
            
            // Lấy màu cờ của AI từ Web gửi lên 
            int aiColor = request.getAiRole(); 
            Minimax minimax = new Minimax(aiColor);
            int searchDepth = request.getDepth() > 0 ? request.getDepth() : 6;
            minimax.setMaxDepth(searchDepth);
            
            int[] bestMoveArray = minimax.findBestMove(board);
            
            if (bestMoveArray == null) {
                response.put("success", false);
                response.put("message", "Không có nước đi hợp lệ");
                return response;
            }
            
            Map<String, Integer> bestMove = new HashMap<>();
            bestMove.put("row", bestMoveArray[0]);
            bestMove.put("col", bestMoveArray[1]);
            
            response.put("success", true);
            response.put("bestMove", bestMove);
            response.put("message", "Nước đi của AI tính toán thành công");
            
            return response;
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Lỗi: " + e.getMessage());
            return response;
        }
    }
    // Gợi ý nước đi 
    @PostMapping("/game/validate-move")
    public GameStateResponse validateMove(@RequestBody MoveRequest request) {
        try {
            Board board = new Board();
            board.setBoardFromCopy(request.getBoard());
            
            int currentPlayer = request.getAiRole(); 
            List<int[]> validMoves = GameRules.getValidMoves(board, currentPlayer);
            
            boolean gameOver = GameRules.isGameOver(board);
            
            return new GameStateResponse(
                board.getBoardCopy(),
                validMoves,
                currentPlayer,
                board.countPieces(Board.BLACK),
                board.countPieces(Board.WHITE),
                gameOver
            );
        } catch (Exception e) {
            return new GameStateResponse(null, null, 0, 0, 0, false);
        }
    }
    
    // Kiểm tra lượt 
    @PostMapping("/game/next-player")
    public GameStateResponse getNextPlayer(@RequestBody MoveRequest request) {
        try {
            Board board = new Board();
            board.setBoardFromCopy(request.getBoard());
            
            int currentPlayer = request.getAiRole(); 
            int nextPlayer = GameRules.getOpponent(currentPlayer);
            
            List<int[]> validMoves = GameRules.getValidMoves(board, nextPlayer);
            
            if (validMoves.isEmpty()) {
                validMoves = GameRules.getValidMoves(board, currentPlayer);
                nextPlayer = currentPlayer;
            }
            
            boolean gameOver = GameRules.isGameOver(board);
            
            return new GameStateResponse(
                board.getBoardCopy(),
                validMoves,
                nextPlayer,
                board.countPieces(Board.BLACK),
                board.countPieces(Board.WHITE),
                gameOver
            );
        } catch (Exception e) {
            return new GameStateResponse(null, null, 0, 0, 0, false);
        }
    }
    // Kiểm tra kết nối BE 
    @GetMapping("/health")
    public String health() {
        return "Backend đang chạy - Othello AI v1.0";
    }
}