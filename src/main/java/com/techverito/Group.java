package com.techverito;

import java.util.*;
import java.util.stream.Collectors;

public class Group {
  private Map<String, List<User>> groupInfoMap;
  private final String groupName;

  public Group(String groupName) {
    this.groupName = groupName;
    groupInfoMap = new HashMap<>();
    groupInfoMap.put(groupName, new ArrayList<>());
  }

  public void setGroupInfoMap(Map<String, List<User>> groupInfoMap) {
    this.groupInfoMap = groupInfoMap;
  }

  public String getGroupName() {
    return groupName;
  }

  public Map<String, List<User>> getGroupInfoMap() {
    return groupInfoMap;
  }

  public void addMemberInGroup(String member) {
    List<User> userList = groupInfoMap.get(this.groupName);
    userList.add(new User(member));
    groupInfoMap.put(groupName, userList);
  }

  public List<String> getGroupMembers(){
    return groupInfoMap.get(this.groupName).stream().map(User::getUserName)
        .collect(Collectors.toList());
  }
}
