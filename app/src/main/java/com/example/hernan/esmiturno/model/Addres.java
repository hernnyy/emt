package com.example.hernan.esmiturno.model;

import java.io.Serializable;

/**
 * Created by Hernan on 12/11/2017.
 */

public class Addres implements Serializable {
    private Long id;
    private Contact contact;

    public Addres(Long id, Contact contact) {
        this.id = id;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
