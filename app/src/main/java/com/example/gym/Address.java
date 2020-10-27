package com.example.gym;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    private String cityName;
    private String streetName;
    private String streetNumber;

    protected Address(Parcel in) {
        cityName = in.readString();
        streetName = in.readString();
        streetNumber = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    // private String postalCode;

   public Address(String cityName, String streetName, String streetNumber){
        this.cityName=cityName;
        this.streetName=streetName;
        this.streetNumber=streetNumber;
    }

    public String getStringAddress(){
        return cityName +", "+streetName+" "+streetNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityName);
        dest.writeString(streetName);
        dest.writeString(streetNumber);
    }
}
