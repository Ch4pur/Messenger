package com.ua.nure.client.controller;

import com.ua.nure.client.Client;
import com.ua.nure.client.annotation.CommandFromServer;
import com.ua.nure.client.application.ClientMain;
import com.ua.nure.client.config.ApplicationContext;
import com.ua.nure.client.util.Util;
import com.ua.nure.data.ClientPackage;
import com.ua.nure.data.ServerPackage;
import com.ua.nure.util.Namings;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

import static com.ua.nure.util.ClientCommandNames.*;

public abstract class Controller {

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

    protected Stage stage;

    @SneakyThrows
    @FXML
    protected void exit() {
        ApplicationContext context = ClientMain.getContext();
        Client client = (Client) context.getBean("client");
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
    public void initialize() {
        Platform.runLater(() -> {
            mainPane.requestFocus();
            if (errorPane != null) {
                errorPane.setVisible(false);
                errorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> errorPane.setVisible(false));
            }
        });
    }

    protected void switchCurrentFxml(String stringPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(stringPath));
        Parent root = loader.load();

        Controller controller = loader.getController();

        controller.setStage(stage);
        ApplicationContext context = ClientMain.getContext();
        Client client = (Client) context.getBean("client");
        client.setCurrentController(controller);

        Scene scene = new Scene(root);
        Platform.runLater(() -> stage.setScene(scene));
    }

    @CommandFromServer(SWITCH_PANE)
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
            ApplicationContext context = ClientMain.getContext();
            Client client = (Client) context.getBean("client");
            client.sendPackageToServer(serverPackage);
        } catch (InterruptedException e) {
            showError(Util.SERVER_ERROR_MESSAGE);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
