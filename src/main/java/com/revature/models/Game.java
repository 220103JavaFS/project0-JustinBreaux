package com.revature.models;

import java.util.ArrayList;

public final class Game {
    public String name;
    public int cost;
    public final int pointMin;
    public final int pointMax;
    public double ticketMultiplier;
    public ArrayList highScores;
    private Customer player;

    public Game(int pointMin, int pointMax) {
        this.pointMin = pointMin;
        this.pointMax = pointMax;
        this.highScores = new ArrayList();
    }

    public Game(String name, int cost, int pointMin, int pointMax, double ticketMultiplier) {
        this(pointMin, pointMax);
        this.name = name;
        this.cost = cost;
        this.ticketMultiplier = ticketMultiplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getPointMin() {
        return pointMin;
    }

    public int getPointMax() {
        return pointMax;
    }

    public double getTicketMultiplier() {
        return ticketMultiplier;
    }

    public void setTicketMultiplier(int ticketMultiplier) {
        this.ticketMultiplier = ticketMultiplier;
    }

    public ArrayList getHighScores() {
        return highScores;
    }

    public void setHighScores(ArrayList highScores) {
        this.highScores = highScores;
    }
}
