package com.example.hernan.esmiturno.model;

import java.util.Date;

/**
 * Created by Hernan on 7/11/2017.
 */

public class Meet {
    private Long id;
    private Date fecha;
    private MeetPlace meetPlace;
    private int colorResource;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public MeetPlace getMeetPlace() {
        return meetPlace;
    }

    public void setMeetPlace(MeetPlace meetPlace) {
        this.meetPlace = meetPlace;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }
}
