package com.example.gym;

public class Constants {

    private static final String ROOT_URL = "http://192.168.0.103/GymApi/v1/Api.php?apicall=";

    private static final String DIET_ROOT_URL = "http://192.168.0.103/GymApi/v1/DietApi.php?apicall=";
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



    public static final String SP_USER_DATA ="userData";
    public static final String SP_DIETICIAN_DATA ="dieticianData";
    public static final String SP_TRAINER_DATA ="trainerData";

    //private static final String SP_DIETICIAN_ID = "dieticianId";
    public static final String SP_GYM_WORKER_NAME = "name";
    public static final String SP_GYM_WORKER_SURNAME = "surname";
    public static final String SP_GYM_WORKER_PHONE_NUMBER = "phoneNumber";
    public static final String SP_GYM_WORKER_EMAIL = "email";
    public static final String SP_GYM_WORKER_DESCRIPTION = "description";
    public static final String SP_GYM_WORKER_PHOTO = "photo";



    public static final String SP_USER_ID ="userId";
    public static final String SP_USER_NAME ="userName";
    public static final String SP_USER_SURNAME ="userSurname";
    public static final String SP_GYM_ID="gymId";
    public static final String SP_TRAINER_ID="trainerId";
    public static final String SP_DIETICIAN_ID="dieticianId";

    public static final String URL_CREATE_DIMENSIONS=ROOT_URL + "createDimensions";
    public static final String URL_GET_USER_ALL_DIMENSIONS=ROOT_URL + "getUserAllDimensions";
    public static final String URL_GET_DIMENSIONS=ROOT_URL + "getDimensions";

    public static final String URL_GET_GYMS=ROOT_URL + "getGyms";
    public static final String URL_GET_GYM_DATA=ROOT_URL + "getGymData";
    public static final String URL_SUBSCRIBE_GYM=ROOT_URL + "subscribeGym";
    public static final String URL_UNSUBSCRIBE_GYM=ROOT_URL + "unsubscribeGym";

    public static final String URL_GET_TRAINERS=ROOT_URL + "getTrainers";
    public static final String URL_GET_TRAINER_DATA=ROOT_URL + "getTrainerData";
    public static final String URL_SUBSCRIBE_TRAINER=ROOT_URL + "subscribeTrainer";
    public static final String URL_UNSUBSCRIBE_TRAINER=ROOT_URL + "unsubscribeTrainer";

    public static final String URL_GET_DIETICIANS=ROOT_URL + "getDieticians";
    public static final String URL_GET_DIETICIAN_DATA=ROOT_URL + "getDieticianData";
    public static final String URL_SUBSCRIBE_DIETICIAN=ROOT_URL + "subscribeDietician";
    public static final String URL_UNSUBSCRIBE_DIETICIAN=ROOT_URL + "unsubscribeDietician";

    public static final String URL_GET_USER_DIETS=DIET_ROOT_URL + "getUserDiets";
    public static final String URL_GET_DIET_DAY_MEALS=DIET_ROOT_URL + "getDietDayMeals";
    public static final String URL_GET_MEAL_DATA=DIET_ROOT_URL + "getMealData";
    public static final String URL_GET_USER_AVAILABLE_DIETS=DIET_ROOT_URL + "getUserAvailableDiets";
    public static final String URL_SUBSCRIBE_DIET= DIET_ROOT_URL + "subscribeDiet";
    public static final String URL_UNSUBSCRIBE_DIET= DIET_ROOT_URL + "unsubscribeDiet";





    public static final int CODE_GET_REQUEST = 1024;
    public static final int CODE_POST_REQUEST = 1025;



    public final static String BUNDLE_DIET_NAME="dietName";
    public final static String BUNDLE_DIET_ID="dietId";
    public final static String BUNDLE_DIET_DAY_OF_WEEK_ID="dayOfWeekId";
    public final static String BUNDLE_DIET_DAY_OF_WEEK_NAME="dayOfWeekName";
    public final static String BUNDLE_DIET_SUB_STATUS="dietSubOrUnsub";





}