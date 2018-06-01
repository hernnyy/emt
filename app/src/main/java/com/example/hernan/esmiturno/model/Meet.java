package com.example.hernan.esmiturno.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Hernan on 7/11/2017.
 */

public class Meet implements Serializable {
    private Long id;
    private Date fecha;
    private MeetPlace meetPlace;
    private User userProvider;
    private User userCustomer;
    //dummys
    private int colorResource;
    private String fechaAsString;

    public Meet(Long id, Date fecha, MeetPlace meetPlace, User userProvider, User userCustomer) {
        this.id = id;
        this.fecha = fecha;
        this.meetPlace = meetPlace;
        this.userProvider = userProvider;
        this.userCustomer = userCustomer;
    }

    public Meet() {
    }

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

    public User getUserProvider() {
        return userProvider;
    }

    public void setUserProvider(User userProvider) {
        this.userProvider = userProvider;
    }

    public User getUserCustomer() {
        return userCustomer;
    }

    public void setUserCustomer(User userCustomer) {
        this.userCustomer = userCustomer;
    }

    public String getFechaAsString() {
        return fechaAsString;
    }

    public void setFechaAsString(String fechaAsString) {
        this.fechaAsString = fechaAsString;
    }
}
