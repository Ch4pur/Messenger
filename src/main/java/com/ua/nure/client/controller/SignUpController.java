package com.ua.nure.client.controller;

import com.ua.nure.client.Client;
import com.ua.nure.client.util.Util;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
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

    @Autowired
    public SignUpController(Client client) {
        super(client);
    }

    @FXML
    protected void signUp(MouseEvent event) throws IOException {
        if (loginField.getText().isBlank() || passwordField.getText().isBlank()) {
            return;
        }
        switchCurrentFxml(Util.MAIN_PAGE_PATH, getStageFromEvent(event));
    }

    @FXML
    protected void toSignIn(MouseEvent event) throws IOException {
        Stage stage = getStageFromEvent(event);
        switchCurrentFxml(Util.SIGN_IN_PAGE_PATH, stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        Platform.runLater(() -> {
            addValidation(loginField, Util.LOGIN_REGEX,Util.LOGIN_ERROR_MESSAGE);
            addValidation(passwordField, Util.PASSWORD_REGEX, Util.PASSWORD_ERROR_MESSAGE);
        });
    }
}
