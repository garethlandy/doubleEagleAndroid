package com.example.gareth.androidphpmysql;

public class Constants {

    private static final String ROOT_URL = "http://192.168.1.21/Android/v1/";
   // private static final String ROOT_URL = "http://192.168.1.21/Android/v1/";



    public static final String URL_REGISTER = ROOT_URL+"registerUser.php";
    public static final String URL_LOGIN = ROOT_URL+"userLogin.php";
    public static final String URL_REGISTER_SCORE = ROOT_URL+"registerScore.php";
    public static final String URL_GET_COURSES = ROOT_URL+"getCourses.php";
    public static final String URL_CREATE_TEETIME = ROOT_URL+"createTeeTime.php";
    public static final String URL_GET_DATA = ROOT_URL+"getdata.php";
    public static final String URL_TEE_TIME_BY_USER = ROOT_URL+"getTeeTimesByUser.php?username=";
    public static final String URL_GET_COURSE_INFO = ROOT_URL+"getCourseInfo.php?course_id=";
    public static final String URL_GET_COURSE_INFO_BY_ID = ROOT_URL+"getCourseInfoById.php?id=";
    public static final String URL_GET_SCORE_BY_COMPETITION = ROOT_URL +"userScorePieChart.php";
}
