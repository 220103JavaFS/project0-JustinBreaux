package com.revature.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Record {
    private Timestamp time;
    private Player player;
    private int score;
    private int machineNum;
    private Game game;

    public Record() {
    }

    public Record(Timestamp time, Player player, int score, int machineNum, Game game) {
        this.time = time;
        this.player = player;
        this.score = score;
        this.machineNum = machineNum;
        this.game = game;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMachineNum() {
        return machineNum;
    }

    public void setMachineNum(int machineNum) {
        this.machineNum = machineNum;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;
        Record record = (Record) o;
        return getScore() == record.getScore() && getMachineNum() == record.getMachineNum() && Objects.equals(getTime(), record.getTime()) && Objects.equals(getPlayer(), record.getPlayer()) && Objects.equals(getGame(), record.getGame());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime(), getPlayer(), getScore(), getMachineNum(), getGame());
    }

    @Override
    public String toString() {
        return "Record{" +
                "time=" + time +
                ", player=" + player +
                ", score=" + score +
                ", machineNum=" + machineNum +
                ", game=" + game +
                '}';
    }
}
