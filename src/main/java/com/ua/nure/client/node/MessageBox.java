package com.ua.nure.client.node;

import com.ua.nure.server.model.entity.Message;
import com.ua.nure.server.model.entity.User;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;

public class MessageBox extends VBox {

    private Message message;

    public MessageBox(Message message) {
        this.message = message;
        createBox();
    }

    private void createBox() {
        String senderName = message.getMember().getUser().getUsernameOrLogin();
        Label senderLabel = new Label(senderName);
        senderLabel.getStyleClass().add("--sender");
        senderLabel.setPrefWidth(150);
        senderLabel.setWrapText(true);
        Label content = new Label(message.getContent());
        content.setWrapText(true);
        content.getStyleClass().add("--content");

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String dateString = format.format( message.getDate());
        Label sentDate = new Label(dateString);
        sentDate.setPadding(new Insets(0,0,0,100));
        sentDate.getStyleClass().add("--date");
        HBox innerBox = new HBox(senderLabel, sentDate);

        VBox messageBox = new VBox(innerBox, content);
        messageBox.setMaxHeight(Region.USE_COMPUTED_SIZE);
        messageBox.setMinHeight(Region.USE_COMPUTED_SIZE);
        messageBox.getStyleClass().add("message");

        getChildren().add(messageBox);
    }

}
