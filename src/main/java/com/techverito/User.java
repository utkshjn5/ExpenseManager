package com.techverito;

public class User {
  private final String userName;
  private double balance;

  public User(String userName) {
    this.userName = userName;
    this.balance = 0.0;
  }

  public String getUserName() {
    return userName;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }
}
