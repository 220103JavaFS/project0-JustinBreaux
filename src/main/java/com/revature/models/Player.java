package com.revature.models;

import java.util.Objects;

public class Player extends Admin{
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private int tokenBalance;
    private int ticketBalance;
    private int tier;

    public Player() {
    }

    public Player(String email, String firstName, String lastName, String username, int tokenBalance, int ticketBalance, int tier) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.tokenBalance = tokenBalance;
        this.ticketBalance = ticketBalance;
        this.tier = tier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTokenBalance() {
        return tokenBalance;
    }

    public void setTokenBalance(int tokenBalance) {
        this.tokenBalance = tokenBalance;
    }

    public int getTicketBalance() {
        return ticketBalance;
    }

    public void setTicketBalance(int ticketBalance) {
        this.ticketBalance = ticketBalance;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getTokenBalance() == player.getTokenBalance() && getTicketBalance() == player.getTicketBalance() && getTier() == player.getTier() && Objects.equals(getEmail(), player.getEmail()) && Objects.equals(getFirstName(), player.getFirstName()) && Objects.equals(getLastName(), player.getLastName()) && Objects.equals(getUsername(), player.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getFirstName(), getLastName(), getUsername(), getTokenBalance(), getTicketBalance(), getTier());
    }

    @Override
    public String toString() {
        return "Player{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", tokenBalance=" + tokenBalance +
                ", ticketBalance=" + ticketBalance +
                ", tier=" + tier +
                '}';
    }
}
