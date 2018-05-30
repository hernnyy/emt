package com.example.hernan.esmiturno.model;

import java.io.Serializable;

/**
 * Created by Hernan on 12/05/2018.
 */

public class Contact implements Serializable {
    private Long id;
    private String email;
    private String cellphone;
    private String emailAlt;
    private String cellphoneAlt;

    public Contact(Long id, String email, String cellphone, String emailAlt, String cellphoneAlt) {
        this.id = id;
        this.email = email;
        this.cellphone = cellphone;
        this.emailAlt = emailAlt;
        this.cellphoneAlt = cellphoneAlt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmailAlt() {
        return emailAlt;
    }

    public void setEmailAlt(String emailAlt) {
        this.emailAlt = emailAlt;
    }

    public String getCellphoneAlt() {
        return cellphoneAlt;
    }

    public void setCellphoneAlt(String cellphoneAlt) {
        this.cellphoneAlt = cellphoneAlt;
    }
}
