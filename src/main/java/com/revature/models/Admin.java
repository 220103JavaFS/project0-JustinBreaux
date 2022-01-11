package com.revature.models;

public class Admin extends User{
    private final int id;
    private String name;

    public Admin() {
        this.id = ++totalAccounts;
    }

    public Admin(String name) {
        this();
        this.name = name;
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
}
