package com.example.contactbook.Model;

public class Contact {

    private int id;
    private String name;
    private String phoneNumber;
    private String Description;
    private String password;

    // constructors for contact object
    public Contact(int id, String name, String phoneNumber){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact(String name, String phoneNumber, String Description, String password){
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.Description = Description;
        this.password = password;
    }

    public Contact(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact() {

    }


    // getter and setter functions for contact object
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }


}
