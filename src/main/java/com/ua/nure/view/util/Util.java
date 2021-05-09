package com.ua.nure.view.util;

public final class Util {
    private Util() {
    }

    private static final String FXML_ROOT_PATH = "/static/fxml/";

    public static final String SIGN_IN_PAGE_PATH = FXML_ROOT_PATH + "login.fxml";
    public static final String SIGN_UP_PAGE_PATH = FXML_ROOT_PATH + "register.fxml";
    public static final String MAIN_PAGE_PATH = FXML_ROOT_PATH + "main.fxml";


    public static final String LOGIN_ERROR_MESSAGE = "Login must have from 2 to 19 length starting from lower case";
    public static final String PASSWORD_ERROR_MESSAGE = "Password must have minimum eight characters, at least one letter and one number";
    public static final String NONEXISTENT_USER_MESSAGE = "User with specified login doesn`t exist";
    public static final String WRONG_PASSWORD_MESSAGE = "Wrong password";

    public static final String LOGIN_REGEX = "^[A-Za-z][A-Za-z\\d]{2,19}$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
}
