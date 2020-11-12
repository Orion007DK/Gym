package com.example.gym;

import org.json.JSONException;
import org.json.JSONObject;

public class Gym {

    int gymId;
    String name;
    Address address;
    String email;
    String phoneNumber;
    String description;
    String voivodeship;
    String postalCode;

    public Gym(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Gym(String name, Address address, String email, String phoneNumber, String description) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    public Gym(int gymId, String name, Address address, String email, String phoneNumber, String description) {
        this.gymId = gymId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    public Gym(int gymId, String name, Address address, String email, String phoneNumber, String description, String voivodeship) {
        this.gymId = gymId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.voivodeship=voivodeship;
        this.postalCode=postalCode;
    }

    public Gym(JSONObject jsonObject) throws JSONException {
        this.gymId = jsonObject.getInt("gymId");
        this.name = jsonObject.getString("gymName");
        String place = jsonObject.getString("place");
        String street = jsonObject.getString("street");
        String streetNumber = jsonObject.getString("streetNumber");
        String apartmentNumber = jsonObject.getString("apartmentNumber");
        Address address;
        if(apartmentNumber!=null){
            address = new Address(place, street, streetNumber);}
        else{
            address = new Address(place, street, streetNumber, apartmentNumber);
        }
        this.address = address;
        this.email = jsonObject.getString("email");
        this.voivodeship=jsonObject.getString("voivodeship");
        this.postalCode=jsonObject.getString("postalCode");
        this.phoneNumber=jsonObject.getString("phoneNumber");
        this.description=jsonObject.getString("description");
    }



    public int getGymId() {
        return gymId;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getStringAddress(){
        return address.getStringAddress();
    }

}


