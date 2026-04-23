package com.ptit.othello.api.dto;

public class MoveResponse {
    private int[] move; // [row, col]
    private String message;
    private boolean success;
    
    public MoveResponse() {}
    
    public MoveResponse(int[] move, String message, boolean success) {
        this.move = move;
        this.message = message;
        this.success = success;
    }
    
    public int[] getMove() {
        return move;
    }
    
    public void setMove(int[] move) {
        this.move = move;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
