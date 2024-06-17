package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

public class Client {
    public int id;
    public String firstName;
    public String lastName;
    public String email;
    public String phone;

    public Client(int id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public Client(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public Client() {
    }
}
