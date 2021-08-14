package com.ua.nure.client.controller;

import com.ua.nure.client.util.Util;
import com.ua.nure.data.ServerPackage;
import com.ua.nure.util.ServerCommandNames;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.io.IOException;

import static com.ua.nure.util.Namings.LOGIN;
import static com.ua.nure.util.Namings.PASSWORD;
import static com.ua.nure.util.Namings.USERNAME;


public class SignUpController extends Controller {

    @FXML
    protected TextField loginField;

    @FXML
    protected TextField usernameField;

    @FXML
    protected TextField passwordField;

    @FXML
    protected Button signUpButton;

    @FXML
    protected Button toSignInButton;


    @FXML
    protected void signUp()  {
        if (loginField.getText().isBlank() || passwordField.getText().isBlank()) {
            return;
        }
        ServerPackage serverPackage = new ServerPackage();

        serverPackage.setCommandName(ServerCommandNames.SIGN_UP);
        serverPackage.addAttribute(LOGIN, loginField.getText());
        serverPackage.addAttribute(PASSWORD, passwordField.getText());
        serverPackage.addAttribute(USERNAME, usernameField.getText());

        sendAnswerToClient(serverPackage);
    }

    @FXML
    protected void toSignIn() throws IOException {
        switchCurrentFxml(Util.SIGN_IN_PAGE_PATH);
    }

    @Override
    public void initialize() {
        super.initialize();
        Platform.runLater(() -> {
            addValidation(loginField, Util.LOGIN_REGEX, Util.LOGIN_ERROR_MESSAGE);
            addValidation(passwordField, Util.PASSWORD_REGEX, Util.PASSWORD_ERROR_MESSAGE);
        });
    }
}
