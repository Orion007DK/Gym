package com.example.gym;

import org.json.JSONException;
import org.json.JSONObject;

public class GymWorker {

    private int workerId;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String description;
    private String photo;

    public GymWorker(int workerId, String name, String surname, String phoneNumber, String email, String description, String photo) {
        this.workerId = workerId;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
        this.photo = photo;
    }

    public GymWorker(int workerId, String name, String surname, String phoneNumber, String email, String description) {
        this.workerId = workerId;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
    }

    public GymWorker(JSONObject jsonObject) throws JSONException {
        this.workerId = jsonObject.getInt("id");
        this.name = jsonObject.getString("name");
        this.surname = jsonObject.getString("surname");
        this.phoneNumber = jsonObject.getString("phoneNumber");
        this.email = jsonObject.getString("email");
        this.description = jsonObject.getString("description");
    }

    public GymWorker(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public int getWorkerId() {
        return workerId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }
}
