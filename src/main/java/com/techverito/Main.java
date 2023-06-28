package com.techverito;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter Group Name: ");
            String groupName = scanner.nextLine();
            Group group = new Group(groupName);
            System.out.print("Enter the number of users you want to add in group: ");
            int numUsers = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numUsers; i++) {
                System.out.println("Enter user name: ");
                String userName = scanner.nextLine();
                group.addMemberInGroup(userName);
            }
            ExpenseManager expenseManager = new ExpenseManager(group);
            boolean isRecordExpense = true;
            while(isRecordExpense){
                System.out.print("Do you want to record expense? (yes/no) ");
                String userChoice = scanner.nextLine();
                if (!userChoice.equals("yes")) {
                    isRecordExpense = false;
                } else {
                    System.out.println("Please provide expense details in following format? (payer amount groupexpense(yes/no)) ");
                    String expenseString = scanner.nextLine();
                    String[] arr = expenseString.split(" ");
                    if(!arr[2].equalsIgnoreCase("yes")){
                        System.out.println("Please provide beneficiaries in following format excluding payer? (beneficiaries1 beneficiaries2) ");
                        String beneficiariesInput = scanner.nextLine();
                        String[] beneficiariesArray = beneficiariesInput.split(" ");
                        expenseManager.recordExpense(arr[0], beneficiariesArray, Integer.parseInt(arr[1]), false);
                    }else{
                        expenseManager.recordExpense(arr[0], new String[0], Integer.parseInt(arr[1]), true);
                    }
                }
            }
            expenseManager.printSummaryAndSettleBalance();
        }
    }
}
