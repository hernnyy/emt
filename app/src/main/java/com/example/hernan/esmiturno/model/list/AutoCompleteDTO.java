package com.example.hernan.esmiturno.model.list;

import com.example.hernan.esmiturno.model.Customer;
import com.example.hernan.esmiturno.model.Provider;
import com.example.hernan.esmiturno.model.User;

public class AutoCompleteDTO {
    private Long id;
    private String description;

    public AutoCompleteDTO(User user, Customer customer) {
        this.id = customer.getId();
        this.description = user.getUsername();
    }

    public AutoCompleteDTO(User user, Provider provider) {
        this.id = provider.getId();
        this.description = user.getUsername();
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

}