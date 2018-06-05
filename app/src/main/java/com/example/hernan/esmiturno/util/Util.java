package com.example.hernan.esmiturno.util;

import android.util.Log;

import com.example.hernan.esmiturno.model.Addres;
import com.example.hernan.esmiturno.model.Contact;
import com.example.hernan.esmiturno.model.Customer;
import com.example.hernan.esmiturno.model.Meet;
import com.example.hernan.esmiturno.model.MeetPlace;
import com.example.hernan.esmiturno.model.Person;
import com.example.hernan.esmiturno.model.Provider;
import com.example.hernan.esmiturno.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static User parseJSONToUser(JSONObject json) throws JSONException {

        Customer customer = null;
        if (!"null".equalsIgnoreCase(json.getString("customer"))){
            JSONObject customerJSON = json.getJSONObject("customer");
            customer = new Customer(customerJSON.getLong("id"));
        }
        Provider provider = null;
        if (!"null".equalsIgnoreCase(json.getString("provider"))){
            JSONObject providerJSON = json.getJSONObject("provider");
            provider = new Provider(providerJSON.getLong("id"));
        }

        Person person = null;
        if (!"null".equalsIgnoreCase(json.getString("person"))){
            JSONObject personJSON = json.getJSONObject("person");
            person = parseJSONToPerson(personJSON);
        }

        User user = new User(json.getLong("id"),
                json.getString("username"),
                customer,
                provider,
                person);
        return user;
    }

    public static Person parseJSONToPerson(JSONObject json) throws JSONException {

        Contact contact = null;
        if (!"null".equalsIgnoreCase(json.getString("contact"))){
            JSONObject contactJSON = json.getJSONObject("contact");
            contact = parseJSONToContact(contactJSON);
        }

        Person person = new Person(json.getLong("id"),
                json.getString("first_name"),
                json.getString("last_name"),
                json.getString("document_type"),
                json.getString("document_number"),
                contact);

        return person;
    }

    public static MeetPlace parseJSONToMeetPlace(JSONObject json) throws JSONException {

        Contact contact = null;
        if (!"null".equalsIgnoreCase(json.getString("contact"))){
            JSONObject contactJSON = json.getJSONObject("contact");
            contact = parseJSONToContact(contactJSON);
        }
        Addres addres = null;
        if (!"null".equalsIgnoreCase(json.getString("addres"))){
            JSONObject addresJSON = json.getJSONObject("addres");
            addres = parseJSONToAddres(addresJSON);
        }

        MeetPlace place = new MeetPlace(json.getLong("id"),
                json.getString("name"),
                json.getString("fantasy_name"),
                addres,
                contact);
        return place;
    }

    public static Addres parseJSONToAddres(JSONObject json) throws JSONException {

        Addres addres = new Addres();
        addres.setId(json.getLong("id"));
        addres.setCountry(json.getString("country"));
        addres.setCountryCode(json.getString("country_code"));
        addres.setLocality(json.getString("locality"));
        addres.setPostalCode(json.getString("postal_code"));
        addres.setRegion(json.getString("region"));
        addres.setStreetName(json.getString("street_name"));
        addres.setStreetNumber(json.getString("street_number"));

        return addres;
    }

    public static Contact parseJSONToContact(JSONObject json) throws JSONException {
        return  new Contact(json.getLong("id"),
                json.getString("email"),
                json.getString("cellphone"),
                json.getString("email2"),
                json.getString("cellphone2"));
    }

    public static Meet parseJSONToMeet(JSONObject json) throws JSONException {

        User userProvider = null;
        if (!"null".equalsIgnoreCase(json.getString("userProvider"))){
            userProvider = parseJSONToUser(json.getJSONObject("userProvider"));
        }
        User userCustomer = null;
        if (!"null".equalsIgnoreCase(json.getString("userCustomer"))){
            userCustomer = parseJSONToUser(json.getJSONObject("userCustomer"));
        }
        MeetPlace meetplace = null;
        if (!"null".equalsIgnoreCase(json.getString("meetplace"))){
            meetplace = parseJSONToMeetPlace(json.getJSONObject("meetplace"));
        }
        Date date = null;
        try {
            date = Util.getDateMysql(json.getString("fecha"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Meet meet = new Meet(json.getLong("id"),
                date,
                meetplace,
                userProvider,
                userCustomer);
        return meet;
    }

    public static String getDateAsStringDefault(Date date) {
        return getDateAsString(date,"dd/MM/yyyy HH:mm");
    }
    public static String getDateAsStringToMysql(Date date) {
        return getDateAsString(date,"yyyy-MM-dd HH:mm:ss");
    }
    public static Date getDateMysql(String date) throws ParseException {
        DateFormat readFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss",Locale.getDefault());
        return readFormat.parse(date);
    }
    public static Date getDateFromDatePickerFormat(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return sdf.parse(date);
    }
    public static String getDateAsString(Date date, String format) {
        SimpleDateFormat dateForm = new SimpleDateFormat(format, Locale.getDefault());
        return dateForm.format(date);
    }
}
