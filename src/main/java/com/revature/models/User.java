package com.revature.models;

import java.util.Objects;

public class User {

    private String name;
    private int accountNumber;
    private int tokenBalance;

    public User() {
        this.accountNumber = (int) Math.round(Math.random()*100);
    }

    public User(String name, int accountNumber, int tokenBalance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.tokenBalance = tokenBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getTokenBalance() {
        return tokenBalance;
    }

    public void setTokenBalance(int tokenBalance) {
        this.tokenBalance = tokenBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return accountNumber == user.accountNumber && tokenBalance == user.tokenBalance && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, accountNumber, tokenBalance);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", accountNumber=" + accountNumber +
                ", tokenBalance=" + tokenBalance +
                '}';
    }
}
