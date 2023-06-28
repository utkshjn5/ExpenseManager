package com.techverito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class ExpenseManagerTest {


  ExpenseManager expenseManager;
  Map<String, List<User>> groupInfoMap = new HashMap<>();

  @BeforeEach
  void setUp() {
    groupInfoMap.put("Group1", Arrays.asList(new User("John"),new User("Alice"),new User("Bob")));
    Group mockedGroup = Mockito.mock(Group.class);
    when(mockedGroup.getGroupInfoMap()).thenReturn(groupInfoMap);
    when(mockedGroup.getGroupName()).thenReturn("Group1");
    when(mockedGroup.getGroupMembers()).thenReturn(Arrays.asList("Alice","Bob","John"));
    expenseManager = new ExpenseManager(mockedGroup);
  }

  @Test
  public void testRecordExpense_IndividualExpense() throws UserNotFoundException {
    String payer = "John";
    String[] beneficiaries = {"Alice"};
    double amount = 300.0;
    boolean isGroupExpense = false;

    expenseManager.recordExpense(payer, beneficiaries, amount, isGroupExpense);

    List<User> userList = groupInfoMap.get("Group1");
    Assertions.assertEquals(-150.0, userList.get(0).getBalance());
    Assertions.assertEquals(150.0, userList.get(1).getBalance());
    Assertions.assertEquals(0.0, userList.get(2).getBalance());
  }

  @Test
  public void testRecordExpense_GroupExpense() throws UserNotFoundException {
    String payer = "John";
    String[] beneficiaries = {};
    double amount = 150.0;
    boolean isGroupExpense = true;

    expenseManager.recordExpense(payer, beneficiaries, amount, isGroupExpense);

    List<User> userList = groupInfoMap.get("Group1");
    Assertions.assertEquals(-100.0, userList.get(0).getBalance());
    Assertions.assertEquals(50.0, userList.get(1).getBalance());
    Assertions.assertEquals(50.0, userList.get(2).getBalance());
  }

  @Test
  public void testPrintSummaryAndSettleBalance() {
    expenseManager.printSummaryAndSettleBalance();
    List<User> users = groupInfoMap.get("Group1");
    Assertions.assertEquals(0.0, users.get(0).getBalance());
    Assertions.assertEquals(0.0, users.get(1).getBalance());
    Assertions.assertEquals(0.0, users.get(2).getBalance());
  }

  @Test
  public void testRecordExpense_UserNotFoundException() {
    String payer = "John";
    String[] beneficiaries = {"Eve"};  // User not in the group
    double amount = 100.0;
    boolean isGroupExpense = false;

    UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class, () -> {
      expenseManager.recordExpense(payer, beneficiaries, amount, isGroupExpense);
    });

    String expectedMessage = "No beneficiary with name Eve available in group";
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
}
