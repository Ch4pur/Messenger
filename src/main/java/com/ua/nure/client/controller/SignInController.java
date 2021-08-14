package com.ua.nure.client.controller;

import com.ua.nure.client.util.Util;
import com.ua.nure.data.ServerPackage;
import com.ua.nure.util.ServerCommandNames;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static com.ua.nure.util.Namings.LOGIN;
import static com.ua.nure.util.Namings.PASSWORD;


public class SignInController extends Controller {

    @FXML
    protected TextField loginField;
    @FXML
    protected PasswordField passwordField;
    @FXML
    protected Button submit;
    @FXML
    protected Button toSignUp;

    @FXML
    protected void authorize() {
        if (loginField.getText().isBlank()) {
            return;
        }
        ServerPackage serverPackage = new ServerPackage();
        serverPackage.setCommandName(ServerCommandNames.SIGN_IN);
        serverPackage.addAttribute(LOGIN, loginField.getText());
        serverPackage.addAttribute(PASSWORD, passwordField.getText());

        sendAnswerToClient(serverPackage);
    }

    @FXML
    protected void goToSignUpPage() throws IOException {
        switchCurrentFxml(Util.SIGN_UP_PAGE_PATH);
    }

    @Override
    public void initialize() {
        super.initialize();
        Platform.runLater(() -> addValidation(loginField, Util.LOGIN_REGEX, Util.LOGIN_ERROR_MESSAGE));
    }
}
