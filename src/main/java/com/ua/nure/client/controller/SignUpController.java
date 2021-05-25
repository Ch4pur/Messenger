package com.ua.nure.client.controller;

import com.ua.nure.client.util.Util;
import com.ua.nure.data.ServerPackage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ua.nure.util.Namings.*;

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
    protected void signUp() throws InterruptedException {
        if (loginField.getText().isBlank() || passwordField.getText().isBlank()) {
            return;
        }
        ServerPackage serverPackage = new ServerPackage();

        serverPackage.setCommandName("signUp");
        serverPackage.addAttribute(LOGIN, loginField.getText());
        serverPackage.addAttribute(PASSWORD, passwordField.getText());
        serverPackage.addAttribute(USERNAME, usernameField.getText());

        client.sendPackageToServer(serverPackage);
    }

    @FXML
    protected void toSignIn() throws IOException {
        switchCurrentFxml(Util.SIGN_IN_PAGE_PATH);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        Platform.runLater(() -> {
            addValidation(loginField, Util.LOGIN_REGEX, Util.LOGIN_ERROR_MESSAGE);
            addValidation(passwordField, Util.PASSWORD_REGEX, Util.PASSWORD_ERROR_MESSAGE);
        });
    }
}
