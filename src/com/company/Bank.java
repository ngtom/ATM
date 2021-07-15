package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accs;

    public Bank (String name){
        this.name = name;
        users = new ArrayList<>();
        accs = new ArrayList<>();
    }

    public String getNewUserUid(){
        String res;
        Random rnd = new Random();
        int len = 6;
        boolean nonUnique;

        //keep generating until unique id is found
        do{
            res = "";
            for(int i=0; i<len; i++){
                res += ((Integer)rnd.nextInt(10)).toString();
            }

            // check for Unique
            nonUnique = false;
            for(Account u: accs){
               if(res.compareTo(u.getUid())==0){
                   nonUnique = true;
                   break;
               }
            }
        }while(nonUnique);

        return res;
    }

    /**
     *
     * @return
     */
    public String getNewAccUid(){
        String res;
        Random rnd = new Random();
        int len = 10;
        boolean nonUnique;

        //keep generating until unique id is found
        do{
            res = "";
            for(int i=0; i<len; i++){
                res += ((Integer)rnd.nextInt(10)).toString();
            }

            // check for Unique
            nonUnique = false;
            for(User u: users){
                if(res.compareTo(u.getUid())==0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);

        return res;
    }

    public void addAccount(Account acc){
        this.accs.add(acc);
    }

    public User addUser(String first, String last, String pin){
        User newUser = new User(first, last, pin, this);
        this.users.add(newUser);

        Account newAcc = new Account("Saving", newUser, this);
        newUser.addAccount(newAcc);
        this.accs.add(newAcc);

        return newUser;
    }

    public User login(String id, String pin){
        for(User u : users){
            if(u.getUid().compareTo(id)==0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }

    public String getName(){
        return this.name;
    }

}
