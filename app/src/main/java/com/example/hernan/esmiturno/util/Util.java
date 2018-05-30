package com.example.hernan.esmiturno.util;

import android.util.Log;

import com.example.hernan.esmiturno.model.Contact;
import com.example.hernan.esmiturno.model.Customer;
import com.example.hernan.esmiturno.model.Person;
import com.example.hernan.esmiturno.model.Provider;
import com.example.hernan.esmiturno.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {
    public static User parseJSONToUser(JSONObject json) throws JSONException {

        JSONObject customerJSON = json.getJSONObject("customer");
        JSONObject providerJSON = json.getJSONObject("provider");

        Customer customer = null;
        if (!"null".equalsIgnoreCase(customerJSON.getString("id"))){
            customer = new Customer(customerJSON.getLong("id"));
        }
        Provider provider = null;
        if (!"null".equalsIgnoreCase(providerJSON.getString("id"))){
            provider = new Provider(providerJSON.getLong("id"));
        }

        Person person = null;
        Log.d("person: ", json.get("person").toString());
        if (!"null".equalsIgnoreCase(json.getString("person"))){

            JSONObject personJSON = json.getJSONObject("person");
            Contact contact = null;
            if (!"null".equalsIgnoreCase(personJSON.getString("contact"))){
                JSONObject contactJSON = personJSON.getJSONObject("contact");
                contact = new Contact(contactJSON.getLong("id"),
                        contactJSON.getString("email"),
                        contactJSON.getString("cellphone"),
                        contactJSON.getString("email2"),
                        contactJSON.getString("cellphone2"));
            }

            person = new Person(personJSON.getLong("id"),
                    personJSON.getString("first_name"),
                    personJSON.getString("last_name"),
                    personJSON.getString("document_type"),
                    personJSON.getString("document_number"),
                    contact);
        }

        User user = new User(json.getLong("id"),json.getString("username"),customer,provider,person);
        return user;
    }
}
