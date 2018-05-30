package com.example.hernan.esmiturno.model;

import java.io.Serializable;

/**
 * Created by Hernan on 7/11/2017.
 */

public class Provider implements Serializable {
    private Long id;
    private Long dots;

    public Provider(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDots() {
        return dots;
    }

    public void setDots(Long dots) {
        this.dots = dots;
    }
}
