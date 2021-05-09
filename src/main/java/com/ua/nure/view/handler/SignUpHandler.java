package com.ua.nure.view.handler;

import com.ua.nure.view.util.Util;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SignUpHandler extends AbstractHandler {

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
    protected void signUp(MouseEvent event) {
        if (loginField.getText().isBlank() || passwordField.getText().isBlank()) {
            return;
        }


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
