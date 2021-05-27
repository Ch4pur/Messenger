package com.ua.nure.client.controller;

import com.ua.nure.client.annotation.CommandFromServer;
import com.ua.nure.client.application.ClientMain;
import com.ua.nure.client.config.ApplicationContext;
import com.ua.nure.client.parser.Parser;
import com.ua.nure.client.tag.RoomBox;
import com.ua.nure.data.ClientPackage;
import com.ua.nure.data.ServerPackage;
import com.ua.nure.server.model.entity.Message;
import com.ua.nure.server.model.entity.Room;
import com.ua.nure.util.ServerCommandNames;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ua.nure.util.ClientCommandNames.*;
import static com.ua.nure.util.Namings.*;

public class MainPageController extends Controller {

    private static final int ROOM_PREF_WIDTH = 400;
    private static final int ROOM_PREF_HEIGHT = 50;

    private List<RoomBox> roomBoxes;

    private RoomBox currentRoom;

    @FXML
    protected TextField messageField;

    @FXML
    protected TextField userSearchingField;

    @FXML
    protected ImageView createDialog;

    @FXML
    protected ImageView sendMessageButton;

    @FXML
    protected VBox roomsBox;

    @FXML
    protected VBox messagesBox;

    @FXML
    protected Label roomTitle;

    @FXML
    protected void createDialogRoom() {
        if (!userSearchingField.getText().isBlank()) {
            ServerPackage serverPackage = new ServerPackage();

            serverPackage.setCommandName(ServerCommandNames.CREATE_DIALOG);
            serverPackage.addAttribute(OTHER_USER_LOGIN, userSearchingField.getText());

            sendAnswerToClient(serverPackage);
        }
    }

    @FXML
    protected void sendMessage() {
        if (!messageField.getText().isBlank() && currentRoom != null) {
            ServerPackage serverPackage = new ServerPackage();
            serverPackage.setCommandName(ServerCommandNames.SEND_MESSAGE);
            serverPackage.addAttribute(ROOM_ID, currentRoom.getRoom().getId());
            serverPackage.addAttribute(CONTENT, messageField.getText());
            sendAnswerToClient(serverPackage);
        }
        messageField.setText("");
    }

    @FXML
    protected void selectRoom(MouseEvent event) {
        RoomBox selectedRoomBox = (RoomBox) event.getSource();
        selectedRoomBox.setIsActiveProperty(true);
        if (currentRoom != null) {
            currentRoom.setIsActiveProperty(false);
        }
        currentRoom = selectedRoomBox;
        roomTitle.setText(currentRoom.getRoom().getTitle());
        messagesBox.getChildren().clear();
        ServerPackage serverPackage = new ServerPackage();
        serverPackage.setCommandName(ServerCommandNames.GET_MESSAGES);
        serverPackage.addAttribute(ROOM_ID, currentRoom.getRoom().getId());
        sendAnswerToClient(serverPackage);
    }


    @CommandFromServer(GET_MESSAGES)
    private void updateMessages(ClientPackage clientPackage) {
        long id = (int) clientPackage.getAttribute(ROOM_ID);
        if (id != currentRoom.getRoom().getId()) {
            return;
        }
        ApplicationContext context = ClientMain.getContext();
        Parser parser = (Parser) context.getBean("parser");
        List<Message> messages = parser.parseList((List<?>) clientPackage.getAttribute(MESSAGES), Message.class);
        Platform.runLater(() -> {
            for (Message message : messages) {
                String senderLogin = message.getMember().getUser().getLogin();
                Label senderLabel = new Label(senderLogin);
                Label content = new Label(message.getContent());
                content.getStyleClass().add("--content");

                SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                String dateString = format.format(message.getDate());

                Label sentDate = new Label(dateString);
                HBox innerBox = new HBox(senderLabel, sentDate);

                VBox messageBox = new VBox(innerBox, content);
                messageBox.setMaxHeight(Region.USE_COMPUTED_SIZE);
                messageBox.setMinHeight(Region.USE_COMPUTED_SIZE);
                messageBox.getStyleClass().add("message");
                messagesBox.getChildren().add(messageBox);
            }
        });
    }

    @CommandFromServer(ADD_MESSAGE)
    private void addMessage(ClientPackage clientPackage) {
        long id = (int) clientPackage.getAttribute(ROOM_ID);
        if (id != currentRoom.getRoom().getId()) {
            return;
        }
        ApplicationContext context = ClientMain.getContext();
        Parser parser = (Parser) context.getBean("parser");
        Message message = parser.parse(clientPackage.getAttribute(NEW_MESSAGE), Message.class);
        Platform.runLater(() -> {
            String senderLogin = message.getMember().getUser().getLogin();
            Label senderLabel = new Label(senderLogin);
            Label content = new Label(message.getContent());
            content.getStyleClass().add("--content");

            Label sentDate = new Label(message.getDate().toString());
            HBox innerBox = new HBox(senderLabel, sentDate);

            VBox messageBox = new VBox(innerBox, content);
            messageBox.setMaxHeight(Region.USE_COMPUTED_SIZE);
            messageBox.setMinHeight(Region.USE_COMPUTED_SIZE);
            messageBox.getStyleClass().add("message");
            messagesBox.getChildren().add(messageBox);
        });
    }

    @CommandFromServer(GET_ALL_ROOMS)
    private void updateRooms(ClientPackage clientPackage) {
        ApplicationContext context = ClientMain.getContext();
        Parser parser = (Parser) context.getBean("parser");

        List<Room> rooms = parser.parseList((List<?>) clientPackage.getAttribute(ROOMS), Room.class);
        Platform.runLater(() -> {
            roomsBox.getChildren().clear();
            for (Room room : rooms) {
                RoomBox roomBox = new RoomBox(room);
                roomBox.setPrefSize(ROOM_PREF_WIDTH, ROOM_PREF_HEIGHT);
                roomBox.addEventHandler(MouseEvent.MOUSE_CLICKED, this::selectRoom);

                roomsBox.getChildren().add(roomBox);

                roomBoxes.add(roomBox);
            }
        });
    }

    @CommandFromServer(ADD_ROOM)
    private void addRoom(ClientPackage clientPackage) {
        ApplicationContext context = ClientMain.getContext();
        Parser parser = (Parser) context.getBean("parser");
        System.out.println(clientPackage);
        Room room = parser.parse(clientPackage.getAttribute(NEW_ROOM), Room.class);
        Platform.runLater(() -> {
            RoomBox roomBox = new RoomBox(room);
            roomBox.setPrefSize(ROOM_PREF_WIDTH, ROOM_PREF_HEIGHT);
            roomBox.addEventHandler(MouseEvent.MOUSE_CLICKED, this::selectRoom);

            roomsBox.getChildren().add(roomBox);

            roomBoxes.add(roomBox);
        });
    }

    @Override
    public void initialize() {
        super.initialize();
        roomBoxes = new ArrayList<>();
        Platform.runLater(() -> {
            ServerPackage serverPackage = new ServerPackage();
            serverPackage.setCommandName(ServerCommandNames.GET_ROOMS);
            sendAnswerToClient(serverPackage);
        });
    }
}
