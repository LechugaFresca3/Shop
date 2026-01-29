/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Payable;

/**
 *
 * @author iancl
 */
public class Client extends Person implements Payable{
    private int memberld;
    private Amount balance;
    private static final int MEMBER_ID = 456;
    private static final double BALANCE = 50.00;

    public Client(int memberld, Amount balance, String name) {
        super(name);
        this.memberld = memberld;
        this.balance = balance;
    }

    public int getMemberld() {
        return memberld;
    }

    public void setMemberld(int memberld) {
        this.memberld = memberld;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Client{" + "memberld=" + memberld + ", balance=" + balance + '}';
    }
    
    @Override
    public boolean pay(Amount amount) {

        double finalBalance = balance.getValue() - amount.getValue();

        if (finalBalance > 0) {

            balance.setValue(finalBalance);

            return true;

        } else {

            return false;

        }
    }
 

    
    
    
}
