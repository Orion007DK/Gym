package com.example.gym.dataClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    private String cityName;
    private String streetName;
    private String streetNumber;
    private String apartmentNumber;

    protected Address(Parcel in) {
        cityName = in.readString();
        streetName = in.readString();
        streetNumber = in.readString();
        apartmentNumber = in.readString();
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

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    // private String postalCode;

   public Address(String cityName, String streetName, String streetNumber){
        this.cityName=cityName;
        this.streetName=streetName;
        this.streetNumber=streetNumber;
    }

    public Address(String cityName, String streetName, String streetNumber, String aparatmentNumber){
        this.cityName=cityName;
        this.streetName=streetName;
        this.streetNumber=streetNumber;
        this.apartmentNumber=aparatmentNumber;
    }



    public String getStringAddress(){
        if(apartmentNumber!=null)
            return cityName +", "+streetName+" "+streetNumber+"/"+apartmentNumber;
        else
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
        dest.writeString(apartmentNumber);
    }
}
