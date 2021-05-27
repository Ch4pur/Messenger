package com.ua.nure.client.controller;

import com.ua.nure.client.annotation.CommandFromServer;
import com.ua.nure.client.application.ClientMain;
import com.ua.nure.client.config.ApplicationContext;
import com.ua.nure.client.parser.Parser;
import com.ua.nure.client.tag.MessagesBox;
import com.ua.nure.client.tag.RoomBox;
import com.ua.nure.data.ClientPackage;
import com.ua.nure.data.ServerPackage;
import com.ua.nure.server.model.entity.Room;
import com.ua.nure.util.Namings;
import com.ua.nure.util.ServerCommandNames;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import static com.ua.nure.util.ClientCommandNames.*;

public class MainPageController extends Controller {

    private RoomBox roomsPane;
    private MessagesBox chatPane;

    private Room currentRoom;

    private Set<Room> rooms;

    @FXML
    protected TextField messageField;

    @FXML
    protected TextField userSearchingField;

    @FXML
    protected ImageView searchUser;

    @FXML
    protected ImageView sendMessageButton;

    @FXML
    protected VBox roomsBox;

    @FXML
    protected void createRoom() {
        if (!userSearchingField.getText().isBlank()) {

        }
    }

    @FXML
    protected void sendMessage() {
        if (!messageField.getText().isBlank()) {
            ServerPackage serverPackage = new ServerPackage();
            serverPackage.setCommandName(ServerCommandNames.SEND_MESSAGE);
            serverPackage.addAttribute(Namings.ROOM_ID, currentRoom);
            serverPackage.addAttribute(Namings.CONTENT, messageField.getText());
            sendAnswerToClient(serverPackage);
        }
    }

    @CommandFromServer(UPDATE_MESSAGES_PANE)
    private void updateMessages(ClientPackage clientPackage) {
    }

    @CommandFromServer(UPDATE_ROOMS_PANE)
    private void updateRooms(ClientPackage clientPackage) {
        ApplicationContext context = ClientMain.getContext();
        Parser parser = (Parser) context.getBean("parser");

        List<Room> rooms = parser.parseList((List<?>) clientPackage.getAttributes().get(Namings.ROOMS), Room.class);

        roomsBox.getChildren().clear();
        for (Room room : rooms) {
            Label label = new Label(room.getTitle());

            AnchorPane anchorPane = new AnchorPane(label);

            anchorPane.setPrefSize(300,50);
            anchorPane.getStyleClass().add("room");

            roomsBox.getChildren().add(anchorPane);
        }

    }

    @Override
    public void initialize() {
        super.initialize();
        Platform.runLater(() -> {
            ServerPackage serverPackage = new ServerPackage();
            serverPackage.setCommandName(ServerCommandNames.GET_ROOMS);
            sendAnswerToClient(serverPackage);
        });
    }
}
