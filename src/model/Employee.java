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
public class Employee extends Person implements Logable {

    private int employeeld;
    private String password;

    private static final int EMPLOYEE_ID = 123;
    private static final String PASSWORD = "test";

    public Employee(int employeeld, String password, String name) {
        super(name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
    
    @Override
    public String toString() {
        return "Employee{" + "employeeld=" + employeeld + ", password=" + password + '}';
    }

    @Override
    public boolean login(int user, String password) {
        return user == EMPLOYEE_ID && password.equals(PASSWORD);
    }

}
