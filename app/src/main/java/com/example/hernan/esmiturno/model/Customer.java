package com.example.hernan.esmiturno.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Hernan on 7/11/2017.
 */

public class Customer implements Serializable {
    private Long id;

    public Customer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
