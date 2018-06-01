package com.example.hernan.esmiturno.model;

import java.io.Serializable;

/**
 * Created by Hernan on 12/11/2017.
 */

public class MeetPlace implements Serializable {
    private Long id;
    private String name;
    private String fantasyName;
    private String comments;
    private Addres addres;
    private Contact contact;

    public MeetPlace(Long id, String name, String fantasy_name, Addres addres, Contact contact) {
        this.id = id;
        this.name = name;
        this.fantasyName = fantasy_name;
        this.addres = addres;
        this.contact = contact;
    }

    public MeetPlace(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Addres getAddres() {
        return addres;
    }

    public void setAddres(Addres addres) {
        this.addres = addres;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasy_name) {
        this.fantasyName = fantasy_name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
