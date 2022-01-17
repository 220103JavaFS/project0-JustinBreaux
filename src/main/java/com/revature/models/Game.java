package com.revature.models;

import java.util.ArrayList;
import java.util.Objects;

public class Game {
    public String title;
    public int cost;
    public int pointMin;
    public int pointMax;
    public double ticketMultiplier;

    public Game() {
    }

    public Game(String title, int cost, int pointMin, int pointMax, double ticketMultiplier) {
        this.title = title;
        this.cost = cost;
        this.pointMin = pointMin;
        this.pointMax = pointMax;
        this.ticketMultiplier = ticketMultiplier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setPointMin(int pointMin) {
        this.pointMin = pointMin;
    }

    public int getPointMax() {
        return pointMax;
    }

    public void setPointMax(int pointMax) {
        this.pointMax = pointMax;
    }

    public double getTicketMultiplier() {
        return ticketMultiplier;
    }

    public void setTicketMultiplier(double ticketMultiplier) {
        this.ticketMultiplier = ticketMultiplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return getCost() == game.getCost() && getPointMin() == game.getPointMin() && getPointMax() == game.getPointMax() && Double.compare(game.getTicketMultiplier(), getTicketMultiplier()) == 0 && Objects.equals(getTitle(), game.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getCost(), getPointMin(), getPointMax(), getTicketMultiplier());
    }

    @Override
    public String toString() {
        return "Game{" +
                "title='" + title + '\'' +
                ", cost=" + cost +
                ", pointMin=" + pointMin +
                ", pointMax=" + pointMax +
                ", ticketMultiplier=" + ticketMultiplier +
                '}';
    }
}