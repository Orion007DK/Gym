package com.example.gym;

public class Constants {

    private static final String ROOT_URL = "http://192.168.0.103/GymApi/v1/Api.php?apicall=";
   //private static final String ROOT_URL = "http://192.168.43.231/GymApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_USER = ROOT_URL + "createUser";
    public static final String URL_READ_USERS = ROOT_URL + "getUsers";
    public static final String URL_UPDATE_USER = ROOT_URL + "updateUser";
    public static final String URL_DELETE_USER = ROOT_URL + "deleteUser";
    public static final String URL_GET_USER_ID = ROOT_URL + "getUserId";
    public static final String URL_GET_USER_DATA = ROOT_URL + "getUserData";
    public static final String URL_LOG_IN = ROOT_URL + "logIn";
    public static final String URL_UPDATE_PASSWORD = ROOT_URL + "updatePassword";

    public static final String DATA_FORMAT="dd.MM.yyyy";
    public static final String DATABASE_DATA_FORMAT="yyyy-MM-dd";



    public static final String USER_DATA="userData";

    public static final String USER_ID="userId";
    public static final String USER_NAME="userName";
    public static final String USER_SURNAME="userSurname";

    public static final String URL_CREATE_DIMENSIONS=ROOT_URL + "createDimensions";
    public static final String URL_GET_USER_ALL_DIMENSIONS=ROOT_URL + "getUserAllDimensions";
    public static final String URL_GET_DIMENSIONS=ROOT_URL + "getDimensions";




    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;

}