package com.example.hernan.esmiturno.model;

/**
 * Created by Hernan on 12/11/2017.
 */

public class MeetPlace {
    private Long id;
    private Addres addres;

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
}
