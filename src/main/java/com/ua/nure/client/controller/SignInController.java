package com.ua.nure.client.controller;

import com.ua.nure.client.util.Util;
import com.ua.nure.data.ServerPackage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ua.nure.util.Namings.*;
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
    protected void authorize() throws InterruptedException {
        if (loginField.getText().isBlank()) {
            return;
        }
        ServerPackage serverPackage = new ServerPackage();
        serverPackage.setCommandName("signIn");
        serverPackage.addAttribute(LOGIN, loginField.getText());
        serverPackage.addAttribute(PASSWORD, passwordField.getText());

        client.sendPackageToServer(serverPackage);
    }

    @FXML
    protected void goToSignUpPage() throws IOException {
        switchCurrentFxml(Util.SIGN_UP_PAGE_PATH);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        Platform.runLater(() -> addValidation(loginField, Util.LOGIN_REGEX, Util.LOGIN_ERROR_MESSAGE));
    }
}
