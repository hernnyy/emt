package com.example.hernan.esmiturno.model.list;

import com.example.hernan.esmiturno.model.Customer;
import com.example.hernan.esmiturno.model.MeetPlace;
import com.example.hernan.esmiturno.model.Provider;
import com.example.hernan.esmiturno.model.User;

public class AutoCompleteDTO {
    private Long id;
    private String description;
    private User provider;
    private User customer;
    private MeetPlace meetplace;

    public AutoCompleteDTO(User user, Customer customer) {
        this.id = customer.getId();
        this.description = user.getUsername();
        this.customer= user;
    }

    public AutoCompleteDTO(User user, Provider provider) {
        this.id = provider.getId();
        this.description = user.getUsername();
        this.provider = user;
    }

    public AutoCompleteDTO(MeetPlace place) {
        this.id = place.getId();
        this.description = place.getFantasyName();
        this.meetplace = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public MeetPlace getMeetplace() {
        return meetplace;
    }

    public void setMeetplace(MeetPlace meetplace) {
        this.meetplace = meetplace;
    }
}