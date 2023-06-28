package com.techverito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GroupTest {

  private Group group;
  private Map<String, List<User>> groupInfoMap;

  @BeforeEach
  public void setUp() {
    groupInfoMap = new HashMap<>();
    groupInfoMap.put("Group1", new ArrayList<>());
    group = new Group("Group1");
    group.setGroupInfoMap(groupInfoMap);
  }

  @Test
  public void testAddMemberInGroup() {
    String member = "John";
    List<User> userList = groupInfoMap.get("Group1");

    assertEquals(0, userList.size());

    group.addMemberInGroup(member);

    assertEquals(1, userList.size());
    assertEquals(member, userList.get(0).getUserName());
  }

  @Test
  public void testGetGroupMembers() {
    List<User> userList = groupInfoMap.get("Group1");
    userList.add(new User("Alice"));
    userList.add(new User("Bob"));

    List<String> expectedMembers = List.of("Alice", "Bob");
    List<String> actualMembers = group.getGroupMembers();

    assertEquals(expectedMembers, actualMembers);
  }

  @Test
  public void testGetGroupName() {
    String expectedName = "Group1";
    String actualName = group.getGroupName();

    assertEquals(expectedName, actualName);
  }
}
