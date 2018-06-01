package com.example.hernan.esmiturno.util;

import android.util.Log;

import com.example.hernan.esmiturno.model.Addres;
import com.example.hernan.esmiturno.model.Contact;
import com.example.hernan.esmiturno.model.Customer;
import com.example.hernan.esmiturno.model.MeetPlace;
import com.example.hernan.esmiturno.model.Person;
import com.example.hernan.esmiturno.model.Provider;
import com.example.hernan.esmiturno.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static MeetPlace parseJSONToMeetPlace(JSONObject json) throws JSONException {

        Contact contact = null;
        if (!"null".equalsIgnoreCase(json.getString("contact"))){
            JSONObject contactJSON = json.getJSONObject("contact");
            contact = new Contact(contactJSON.getLong("id"),
                    contactJSON.getString("email"),
                    contactJSON.getString("cellphone"),
                    contactJSON.getString("email2"),
                    contactJSON.getString("cellphone2"));
        }
        Addres addres = null;
        if (!"null".equalsIgnoreCase(json.getString("addres"))){
            JSONObject addresJSON = json.getJSONObject("addres");
            addres = new Addres();
            addres.setCountry(addresJSON.getString("country"));
            addres.setCountryCode(addresJSON.getString("country_code"));
            addres.setId(addresJSON.getLong("id"));
            addres.setLocality(addresJSON.getString("locality"));
            addres.setPostalCode(addresJSON.getString("postal_code"));
            addres.setRegion(addresJSON.getString("region"));
            addres.setStreetName(addresJSON.getString("street_name"));
            addres.setStreetNumber(addresJSON.getString("street_number"));
        }


        MeetPlace place = new MeetPlace(json.getLong("id"),
                json.getString("name"),
                json.getString("fantasy_name"),
                addres,
                contact);
        return place;
    }

    public static String getDateAsStringDefault(Date date) {
        SimpleDateFormat dateForm = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return dateForm.format(date);

    }

    public static String getDateAsStringToMysql(Date date) {
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateForm.format(date);

    }
}
