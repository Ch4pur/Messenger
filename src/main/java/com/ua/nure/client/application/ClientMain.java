package com.ua.nure.client.application;


import com.ua.nure.client.Client;
import com.ua.nure.server.application.ServerMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.ua.nure.client.util.Util;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@SpringBootApplication(scanBasePackages = "com.ua.nure.client")
public class ClientMain extends Application {

    private static Client client;

    @Autowired
    public ClientMain(Client client) {
        ClientMain.client = client;
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource(Util.SIGN_IN_PAGE_PATH));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ServerMain.class, args);
        client.runClient();
        launch(args);
    }
}
