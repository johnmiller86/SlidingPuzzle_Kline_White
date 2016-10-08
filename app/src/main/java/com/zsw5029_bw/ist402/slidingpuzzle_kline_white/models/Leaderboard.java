package com.zsw5029_bw.ist402.slidingpuzzle_kline_white.models;

public class Leaderboard {

    // Instance Vars
    private int leaderboard_id;

    public int getLeaderboard_id() {
        return leaderboard_id;
    }

    public void setLeaderboard_id(int leaderboard_id) {
        this.leaderboard_id = leaderboard_id;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private int level_num, moves, score;
    private String time;

    public int getLevel_num() {
        return level_num;
    }

    public void setLevel_num(int level_num) {
        this.level_num = level_num;
    }





}