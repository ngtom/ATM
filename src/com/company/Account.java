package com.company;

import java.util.ArrayList;

public class Account {
    private String name;
    private String uId;
    private User holder;
    private ArrayList<Transaction> trans;

    public Account(String name, User holder, Bank bank ){
        this.name = name;
        this.holder = holder;
        this.uId = bank.getNewAccUid();

        trans = new ArrayList<>();

    }

    public String getUid(){
        return this.uId;
    }

    public String getSummary(){
        double balance = this.getBalance();
        if(balance >= 0){
            return String.format("%s : $%.02f : %s", this.uId, balance, this.name);
        }else{
            return String.format("%s : $(%.02f) : %s", this.uId, balance, this.name);
        }
    }

    public double getBalance (){
        double balance = 0;
        for (Transaction t : trans){
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransHistory(){
        System.out.printf("Transaction history for account %s\n", this.uId);
        for (int i= trans.size()-1; i>=0; i--){
            System.out.println(this.trans.get(i).getSummary());
        }
        System.out.println();
    }


    public void addTransaction(double amount, String memo){
        Transaction t = new Transaction(amount, memo, this);
        this.trans.add(t);
    }
}
