package com.ua.nure.client.controller;

import com.ua.nure.client.Client;
import com.ua.nure.client.util.Util;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SignInController extends Controller {

    @FXML
    protected TextField loginField;
    @FXML
    protected PasswordField passwordField;
    @FXML
    protected Button submit;
    @FXML
    protected Button toSignUp;

    @Autowired
    public SignInController(Client client) {
        super(client);
    }

    @FXML
    protected void authorize(MouseEvent event) throws IOException {
        if (loginField.getText().isBlank()) {
            return;
        }
        switchCurrentFxml(Util.MAIN_PAGE_PATH, getStageFromEvent(event));
    }

    @FXML
    protected void goToSignUpPage(MouseEvent event) throws IOException {
        Stage stage = getStageFromEvent(event);
        switchCurrentFxml(Util.SIGN_UP_PAGE_PATH, stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        Platform.runLater(() -> addValidation(loginField, Util.LOGIN_REGEX, Util.LOGIN_ERROR_MESSAGE));
    }
}
