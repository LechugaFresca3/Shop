/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Logable;

/**
 *
 * @author iancl
 */
public class Employee extends Person implements Logable{
    int employeeld;
    String password;
    
    public static final int EMPLOYEE_ID = 123;
    public static final String PASSWORD = "test";


    
    public Employee(int employeeld, String password) {
        this.employeeld = employeeld;
        this.password = password;
    }

    
    

    public int getEmployeeld() {
        return employeeld;
    }

    public void setEmployeeld(int employeeld) {
        this.employeeld = employeeld;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    
    @Override
    public boolean login(int user, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
