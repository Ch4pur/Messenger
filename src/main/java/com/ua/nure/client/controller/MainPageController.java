package com.ua.nure.client.controller;

import com.ua.nure.client.annotation.CommandFromServer;
import com.ua.nure.client.application.ClientMain;
import com.ua.nure.client.config.ApplicationContext;
import com.ua.nure.client.node.MessageBox;
import com.ua.nure.client.parser.Parser;
import com.ua.nure.client.node.RoomBox;
import com.ua.nure.data.ClientPackage;
import com.ua.nure.data.ServerPackage;
import com.ua.nure.server.model.entity.Message;
import com.ua.nure.server.model.entity.Room;
import com.ua.nure.server.model.entity.User;
import com.ua.nure.util.ServerCommandNames;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
        User user = parser.parse(clientPackage.getAttribute(MAIN_USER), User.class);
        Platform.runLater(() -> {
            for (Message message : messages) {
                createMessage(user, message);
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
        User user = parser.parse(clientPackage.getAttribute(MAIN_USER), User.class);

        Platform.runLater(() -> createMessage(user, message));
    }

    @CommandFromServer(GET_ALL_ROOMS)
    private void updateRooms(ClientPackage clientPackage) {
        ApplicationContext context = ClientMain.getContext();
        Parser parser = (Parser) context.getBean("parser");

        List<Room> rooms = parser.parseList((List<?>) clientPackage.getAttribute(ROOMS), Room.class);
        Platform.runLater(() -> {
            roomsBox.getChildren().clear();
            for (Room room : rooms) {
                createRoom(room);
            }
        });
    }

    @CommandFromServer(ADD_ROOM)
    private void addRoom(ClientPackage clientPackage) {
        ApplicationContext context = ClientMain.getContext();
        Parser parser = (Parser) context.getBean("parser");
        Room room = parser.parse(clientPackage.getAttribute(NEW_ROOM), Room.class);

        Platform.runLater(() -> createRoom(room));
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

    private void createRoom(Room room) {
        RoomBox roomBox = new RoomBox(room);
        roomBox.setPrefSize(ROOM_PREF_WIDTH, ROOM_PREF_HEIGHT);
        roomBox.addEventHandler(MouseEvent.MOUSE_CLICKED, this::selectRoom);

        roomsBox.getChildren().add(roomBox);

        roomBoxes.add(roomBox);
    }

    private void createMessage(User sender, Message message) {
        MessageBox chatBox = new MessageBox(message);
        if (sender.equals(message.getMember().getUser())) {
            chatBox.setPadding(new Insets(0,0, 0, messagesBox.getWidth() - 350));
            chatBox.getStyleClass().add("your");
        } else {
            chatBox.getStyleClass().add("other");
        }

        messagesBox.getChildren().add(chatBox);
    }
}
