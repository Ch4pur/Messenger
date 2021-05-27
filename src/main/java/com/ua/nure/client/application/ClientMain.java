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

    private static ApplicationContext applicationContext;

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);

        String startingPath = (String) applicationContext.getBean("startingPagePath");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(startingPath));

        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.setStage(stage);

        Client client = (Client) applicationContext.getBean("client");
        client.setCurrentController(controller);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        applicationContext = ApplicationContext.createContext();
        Client client = (Client) applicationContext.getBean("client");
        client.connect();
        launch(args);
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }
}
