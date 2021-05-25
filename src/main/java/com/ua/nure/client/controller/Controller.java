package com.ua.nure.client.controller;

import com.ua.nure.client.Client;
import com.ua.nure.client.annotation.Command;
import com.ua.nure.client.util.Util;
import com.ua.nure.data.ClientPackage;
import com.ua.nure.data.ServerPackage;
import com.ua.nure.util.Namings;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ua.nure.util.ClientCommandNames.*;
public abstract class Controller implements Initializable {

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

    private double xOffset;
    private double yOffset;

    protected Client client;
    protected Stage stage;

    @SneakyThrows
    @FXML
    protected void exit() {
        client.disconnect();
        Platform.exit();
    }

    @FXML
    protected void minimizeWindow() {
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
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    protected void hideError()  {
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


    protected void switchCurrentFxml(String stringPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(stringPath));
        Parent root = loader.load();

        Controller controller = loader.getController();

        controller.setClient(client);
        controller.setStage(stage);
        client.setCurrentController(controller);

        Scene scene = new Scene(root);
        Platform.runLater(() -> stage.setScene(scene));
    }
    @Command(SWITCH_PANE)
    protected void switchCurrentFxml(ClientPackage clientPackage) throws IOException {
        switchCurrentFxml((String) clientPackage.getAttributes().get(Namings.PATH));
    }
    public void showError(String errorMessage) {
        Platform.runLater(() -> {
            errorLabel.setText(errorMessage);
            errorPane.setVisible(true);
        });
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

    public void sendAnswerToClient(ServerPackage serverPackage) {
        try {
            client.sendPackageToServer(serverPackage);
        } catch (InterruptedException e) {
            showError(Util.SERVER_ERROR_MESSAGE);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
