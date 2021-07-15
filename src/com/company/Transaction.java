package com.company;

import java.util.Date;

public class Transaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;

    public Transaction(double amount, Account inAcc){
        this.amount = amount;
        this.inAccount = inAcc;
        timestamp = new Date();
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account acc){
        this(amount, acc);
        this.memo = memo;
    }

    public double getAmount() {
        return this.amount;
    }

    /**
     *
     * @return
     */

    public String getSummary(){
        if (this.amount>=0){
            return String.format("%s : $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        } else{
            return String.format("%s : ($%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
        }
    }
}
