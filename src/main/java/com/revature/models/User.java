package com.revature.models;

import java.util.Objects;

public abstract class User {

    public static int totalAccounts;
    private int id;
    private String name;

    public User() {
    }

    public abstract int getId();

    //public abstract void setId(int id);

    public abstract String getName();

    public abstract void setName(String name);
}
