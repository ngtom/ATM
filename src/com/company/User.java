package com.company;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String fName;
    private String lName;
    private String uId;
    private byte[] pinHash;
    private ArrayList<Account> accs;

    /**
     * User Constructor : create new user
     * @param first user's first name
     * @param last  user's last name
     * @param pin   hash pin of user
     * @param bank  bank obj that user is customer of
     */

    public User (String first, String last, String pin, Bank bank){
        fName = first;
        lName = last;

        // pin hashing with md5
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No such Algorithm!");
            e.printStackTrace();
            System.exit(1);
        }

        uId = bank.getNewUserUid();
        accs = new ArrayList<>();
        System.out.printf("New user: %s, %s with id %s is created",lName,fName,uId);
    }

    public void addAccount(Account theAcc){
        accs.add(theAcc);
    }

    public String getUid(){
        return uId;
    }

    public boolean validatePin(String pin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No such Algorithm!");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName(){return this.fName;}

    public void printAccSummary(){
        System.out.printf("\n\n%s's accounts summary: \n", this.fName);
        for(int i=0;i<accs.size();i++){
            System.out.printf(" %d) %s\n", i+1, accs.get(i).getSummary());
        }
    }
    public int numAccount(){
        return accs.size();
    }

    public void printAccTransactionHistory(int index){
        this.accs.get(index).printTransHistory();
    }

    public double getAccBal(int idxAcc){
        return accs.get(idxAcc).getBalance();
    }

    public String getAccUid(int idx){
        return this.accs.get(idx).getUid();
    }

    public void addAccTransaction(int idx, double amount, String memo){
        this.accs.get(idx).addTransaction(amount, memo);
    }
}
