package com.example.gym;

public class Constants {

    private static final String ROOT_URL = "http://192.168.0.104/GymApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_USER = ROOT_URL + "createUser";
    public static final String URL_READ_USERS = ROOT_URL + "getUsers";
    public static final String URL_UPDATE_USER = ROOT_URL + "updateUser";
    public static final String URL_DELETE_USER = ROOT_URL + "deleteUser";
    public static final String URL_GET_USER_ID = ROOT_URL + "getUserId";
    public static final String URL_GET_USER_DATA = ROOT_URL + "getUserData";

    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

}