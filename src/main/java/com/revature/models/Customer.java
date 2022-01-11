package com.revature.models;

import java.util.Objects;

public class Customer extends User{

    private final int id;
    private String name;
    private String username;
    private String emailAddress;
    private int tier; //1 for basic, 2 for premium
    private int tokenBalance;
    private int ticketBalance;


    public Customer() {
        this.id = ++totalAccounts;
    }

    public Customer(String name, String username, String emailAddress, int tier) {
        this();
        this.name = name;
        this.username = username;
        this.emailAddress = emailAddress;
        this.tier = tier;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && tier == customer.tier && tokenBalance == customer.tokenBalance && ticketBalance == customer.ticketBalance && Objects.equals(name, customer.name) && Objects.equals(username, customer.username) && Objects.equals(emailAddress, customer.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, emailAddress, tier, tokenBalance, ticketBalance);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", tier=" + tier +
                ", tokenBalance=" + tokenBalance +
                ", ticketBalance=" + ticketBalance +
                '}';
    }
}
