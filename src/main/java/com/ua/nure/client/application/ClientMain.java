package com.ua.nure.client.application;


import com.ua.nure.client.Client;
import com.ua.nure.client.controller.Controller;
import com.ua.nure.client.config.ApplicationContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ClientMain extends Application {

    private static Client client;
    private static String startingPath;

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(startingPath));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setStage(stage);
        controller.setClient(client);
        client.setCurrentController(controller);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext.createContext();
        client.connect();
        launch(args);
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        ClientMain.client = client;
    }

    public static void setStartingPath(String startingPath) {
        ClientMain.startingPath = startingPath;
    }
}
