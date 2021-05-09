package com.ua.nure.view.handler;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractHandler implements Initializable {

    private double xOffset;
    private double yOffset;

    @FXML
    protected AnchorPane mainPane;

    @FXML
    protected AnchorPane additionPane;

    @FXML
    protected Button exitButton;

    @FXML
    protected Button minimizeButton;


    @FXML
    protected AnchorPane errorPane;

    @FXML
    protected Label errorLabel;

    @FXML
    protected void exit() {
        Platform.exit();
    }

    @FXML
    protected void minimizeWindow(MouseEvent event) {
        Stage stage = getStageFromEvent(event);
        stage.setIconified(true);
    }

    @FXML
    protected void disableFocus() {
        mainPane.requestFocus();
    }

    @FXML
    protected void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    protected void onMouseDragged(MouseEvent event) {
        Stage stage = getStageFromEvent(event);
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    protected void hideError(MouseEvent event) {
        errorPane.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            mainPane.requestFocus();
            if (errorPane != null) {
                errorPane.setVisible(false);
            }
        });
    }

    protected Stage getStageFromEvent(MouseEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    protected void switchCurrentFxml(String stringPath, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(stringPath));
        stage.setScene(new Scene(loader.load()));
    }

    protected void showError(String errorMessage) {
        errorLabel.setText(errorMessage);
        errorPane.setVisible(true);
    }


    protected void addValidation(TextField textField, String validationRegex, String errorMessage) {
        textField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!textField.getText().matches(validationRegex)) {
                    textField.setText("");
                    showError(errorMessage);
                }
            }
        });
    }
}
