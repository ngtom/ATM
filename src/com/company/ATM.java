package com.company;

import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank theBCA = new Bank("BCA");
        User userTom = theBCA.addUser("Tom", "Goertzen", "2313");
        Account theAcc = new Account("Checking",userTom,theBCA);
        userTom.addAccount(theAcc);
        theBCA.addAccount(theAcc);

        User curUser;
        while(true){
            curUser = ATM.mainMenuPrompt(theBCA, sc);
            ATM.printUserMenu(curUser,sc);
        }

    }
    public static User mainMenuPrompt(Bank theBank, Scanner sc){
        String userId;
        String pin;
        User authUser;

        do{
            System.out.printf("\n\nWelcome to the %s\n", theBank.getName());
            System.out.print("Enter your id: ");
            userId = sc.nextLine();
            System.out.print("Enter your pin: ");
            pin = sc.nextLine();

            authUser = theBank.login(userId, pin);
            if(authUser == null){
                System.out.println("Error, incorrect id/pin combination \n please try again!");
            }
        }while(authUser==null);

        return authUser;
    }

    public static void printUserMenu(User user, Scanner sc){
        user.printAccSummary();
        int choice;
        do{
            System.out.printf("Welcome %s, what would u like to do", user.getFirstName());
            System.out.println("\n 1) Show transaction history");
            System.out.println(" 2) Withdraw ");
            System.out.println(" 3) Deposit ");
            System.out.println(" 4) Transfer ");
            System.out.println(" 5) Quit ");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            if (choice <1 || choice > 5) System.out.println("please choose between 1-5");
        }while(choice<1 || choice>5);

        switch(choice){
            case 1:
                ATM.showTransaction(user, sc);
                break;
            case 2:
                ATM.withdraw(user, sc);
                break;
            case 3:
                ATM.deposit(user, sc);
                break;
            case 4:
                ATM.transfer(user, sc);
                break;
            case 5:
                sc.nextLine();
                break;

        }
        if (choice != 5) {
            ATM.printUserMenu(user, sc);
        }
    }

    public static void showTransaction(User user, Scanner sc){
        int idxAcc;
        do {
            System.out.printf("Enter the number (1-%d) of the account\n"+
                    "whose transaction you want to display: ",
                    user.numAccount());
            idxAcc = sc.nextInt()-1;
            if(idxAcc<0 || idxAcc >= user.numAccount()){
                System.out.println("Invalid account, please try again!");
            }
        }while (idxAcc<0 || idxAcc >= user.numAccount());

        user.printAccTransactionHistory(idxAcc);
    }

    public static void transfer (User user, Scanner sc){
        int fromAcc;
        int toAcc;
        double userInput;
        double accBal;

        do {
            System.out.printf("enter number(1-%d) to choose which account to transfer to", user.numAccount());
            fromAcc = sc.nextInt()-1;
            if(fromAcc<0 || fromAcc>= user.numAccount()){
                System.out.println("Invalid account, please try again!");
            }
        }while(fromAcc<0 || fromAcc>= user.numAccount());

        accBal = user.getAccBal(fromAcc);

        do {
            System.out.printf("enter number(1-%d) to choose which account to transfer to:", user.numAccount());
            toAcc = sc.nextInt()-1;
            if(toAcc<0 || toAcc>= user.numAccount()){
                System.out.println("Invalid account, please try again!");
            }
        }while(toAcc<0 || toAcc>= user.numAccount());

        do{
            System.out.printf("Enter amount to transfer(max $%.02f", accBal);
            userInput = sc.nextInt();
            if(userInput<0){
                System.out.println("Enter the amount greater than zero");
            }else{
                System.out.printf("amount must not be greater than $%.02f", accBal);
            }
        }while(userInput<0 || userInput>accBal);


        user.addAccTransaction(fromAcc, -1*userInput, String.format("Transfer to account %s", user.getAccUid(toAcc)));
        user.addAccTransaction(toAcc, userInput, String.format("Transfer to account %s", user.getAccUid(fromAcc)));
    }

    public static void withdraw(User user, Scanner sc){
        int fromAcc;
        double userInput;
        double accBal;
        String memo;

        do {
            System.out.printf("enter number(1-%d) to choose which account to withdraw from:", user.numAccount());
            fromAcc = sc.nextInt()-1;
            if(fromAcc<0 || fromAcc>= user.numAccount()){
                System.out.println("Invalid account, please try again!");
            }
        }while(fromAcc<0 || fromAcc>= user.numAccount());

        accBal = user.getAccBal(fromAcc);

        do{
            System.out.printf("Enter amount to withdraw(max $%.02f)", accBal);
            userInput = sc.nextInt();
            if(userInput<0){
                System.out.println("Enter the amount greater than zero");
            }else{
                System.out.printf("amount must not be greater than $%.02f", accBal);
            }
        }while(userInput<0 || userInput>accBal);

        sc.nextLine();

        // get a memo
        System.out.print("Enter memo:");
        memo = sc.nextLine();
        // add withdrawl

        user.addAccTransaction(fromAcc, -1*userInput, memo);
    }

    public static void deposit(User user, Scanner sc){
        int toAcc;
        double userInput;
        double accBal;
        String memo;

        do {
            System.out.printf("Enter number(1-%d) to choose which account to deposit in:", user.numAccount());
            toAcc = sc.nextInt()-1;
            if(toAcc<0 || toAcc>= user.numAccount()){
                System.out.println("Invalid account, please try again!");
            }
        }while(toAcc<0 || toAcc>= user.numAccount());

        accBal = user.getAccBal(toAcc);

        do{
            System.out.printf("Enter amount to deposit(max $%.02f)", accBal);
            userInput = sc.nextInt();
            if(userInput<0){
                System.out.println("Enter the amount greater than zero");
            }
        }while(userInput<0);

        sc.nextLine();

        // get a memo
        System.out.print("\nEnter memo:");
        memo = sc.nextLine();
        // add withdraw

        user.addAccTransaction(toAcc, userInput, memo);
    }
}
