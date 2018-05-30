package com.example.hernan.esmiturno.model;

import java.io.Serializable;

/**
 * Created by Hernan on 12/05/2018.
 */

public class User implements Serializable{
    private Long id;
    private String username;
    private Customer customer;
    private Provider provider;
    private Person person;

    public User(Long id, String username, Customer customer, Provider provider, Person person) {
        this.id = id;
        this.username = username;
        this.customer = customer;
        this.provider = provider;
        this.person = person;
    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
