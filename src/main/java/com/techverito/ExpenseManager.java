package com.techverito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public record ExpenseManager(Group group) {

  void updateBalance(String person, double amount) {
    group.getGroupInfoMap().getOrDefault(group.getGroupName(), Collections.emptyList())
        .stream()
        .filter(user -> user.getUserName().equals(person))
        .findFirst()
        .ifPresent(user -> user.setBalance(user.getBalance() + amount));
  }

  public void recordExpense(String payer, String[] beneficiaries, double amount, boolean isGroupExpense) throws UserNotFoundException{
    List<User> userList = group.getGroupInfoMap().get(group.getGroupName());
    if(userList.isEmpty()){
      throw new UserNotFoundException("No User available in group");
    }else{
      Arrays.stream(beneficiaries).forEach(person->{
            if(!group.getGroupMembers().contains(person)){
              throw new UserNotFoundException("No beneficiary with name "+ person+ " available in group");
            }});
    }
    if (beneficiaries.length > 0) {
      double individualShare = amount / (beneficiaries.length + 1);
      Arrays.stream(beneficiaries).forEach(person -> updateBalance(person, individualShare));
      updateBalance(payer, individualShare);
    } else if (isGroupExpense) {
        userList.forEach(person -> updateBalance(person.getUserName(), amount/userList.size()));
    }
    updateBalance(payer, -amount);
  }

  public void printSummaryAndSettleBalance() {
    List<User> users = group.getGroupInfoMap().get(group.getGroupName());

    users.stream()
        .filter(user -> user.getBalance() > 0)
        .forEach(user -> settlePositiveBalance(user, users));

    users.stream()
        .filter(user -> user.getBalance() < 0)
        .forEach(user -> settleNegativeBalance(user, users));
  }

  private void settlePositiveBalance(User user, List<User> users) {
    users.stream()
        .filter(otherUser -> otherUser.getBalance() < 0)
        .forEach(otherUser -> {
          double remaining = user.getBalance() + otherUser.getBalance();
          printOweStatement(user, otherUser, Math.abs(otherUser.getBalance()));
          if (remaining > 0) {
            user.setBalance(remaining);
            otherUser.setBalance(0.0);
          } else {
            otherUser.setBalance(remaining);
            user.setBalance(0.0);
          }
        });
  }

  private void settleNegativeBalance(User user, List<User> users) {
    users.stream()
        .filter(otherUser -> otherUser.getBalance() > 0)
        .forEach(otherUser -> {
          printOweStatement(otherUser, user, Math.abs(user.getBalance()));
          double remaining = user.getBalance() + otherUser.getBalance();
          if (remaining > 0) {
            otherUser.setBalance(remaining);
            user.setBalance(0.0);
          } else {
            user.setBalance(remaining);
            otherUser.setBalance(0.0);
          }
        });
  }

  private void printOweStatement(User fromUser, User toUser, double amount) {
    System.out.printf("%s owes %.2frs to %s\n", fromUser.getUserName(), amount, toUser.getUserName());
  }


}
