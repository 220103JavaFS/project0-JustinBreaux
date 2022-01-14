package com.revature.models;

import java.util.Objects;

public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private boolean adminStatus;

    public User() {
    }

    public User(int userId, String firstName, String lastName, boolean adminStatus) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.adminStatus = adminStatus;
    }
    public User(String firstName, String lastName, boolean adminStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.adminStatus = adminStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public boolean isAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() && isAdminStatus() == user.isAdminStatus() && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getFirstName(), getLastName(), isAdminStatus());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", adminStatus=" + adminStatus +
                '}';
    }
}
