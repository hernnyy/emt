package com.example.hernan.esmiturno.model;

import java.util.Date;

/**
 * Created by Hernan on 7/11/2017.
 */

public class Meet {
    private Long id;
    private Date fecha;
    private MeetPlace meetPlace;
    private Provider provider;
    private Customer customer;
    //dummys
    private int colorResource;
    private String fechaAsString;

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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getFechaAsString() {
        return fechaAsString;
    }

    public void setFechaAsString(String fechaAsString) {
        this.fechaAsString = fechaAsString;
    }
}
