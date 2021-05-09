package com.ua.nure.view.handler;

import com.ua.nure.view.util.Util;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SignInHandler extends AbstractHandler {

    private static final String LOGIN_ERROR_MESSAGE = "Login must have from 2 to 19 length starting from lower case";
    private static final String LOGIN_REGEX = "^[A-Za-z][A-Za-z\\d]{2,19}$";

    @FXML
    protected TextField loginField;
    @FXML
    protected PasswordField passwordField;
    @FXML
    protected Button submit;
    @FXML
    protected Button toSignUp;

    @FXML
    protected void authorize(MouseEvent event) {
        System.out.println("Authorizing");
    }

    @FXML
    protected void goToSignUpPage(MouseEvent event) throws IOException {
        Stage stage = getStageFromEvent(event);
        switchCurrentFxml(Util.SIGN_UP_PAGE_PATH, stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        Platform.runLater(() -> addValidation(loginField, LOGIN_REGEX, LOGIN_ERROR_MESSAGE));
    }
}
