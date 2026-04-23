package com.ptit.othello.api.dto;

public class MoveRequest {
    private int[][] board;
    private int aiRole; 
    private int depth ; 

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getAiRole() {
        return aiRole;
    }

    public void setAiRole(int aiRole) {
        this.aiRole = aiRole;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}