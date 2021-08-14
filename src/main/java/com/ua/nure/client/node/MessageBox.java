package com.ua.nure.client.node;

import com.ua.nure.client.util.StyleClasses;
import com.ua.nure.server.model.entity.Message;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;

public class MessageBox extends VBox {

    private final Message message;

    public MessageBox(Message message) {
        this.message = message;
        createBox();
    }

    private void createBox() {
        String senderName = message.getMember().getUser().getUsernameOrLogin();
        Label senderLabel = new Label(senderName);
        senderLabel.getStyleClass().add(StyleClasses.SENDER);

        Label content = new Label(message.getContent());
        content.getStyleClass().add(StyleClasses.CONTENT);
        content.setWrapText(true);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String dateString = format.format( message.getDate());
        Label sentDate = new Label(dateString);
        sentDate.getStyleClass().add(StyleClasses.SENT_DATE);

        HBox headerBox = new HBox(senderLabel, sentDate);
        headerBox.getStyleClass().add(StyleClasses.HEADER);

        VBox messageBox = new VBox(headerBox, content);
        messageBox.setMaxHeight(Region.USE_COMPUTED_SIZE);
        messageBox.setMinHeight(Region.USE_COMPUTED_SIZE);
        messageBox.getStyleClass().add(StyleClasses.INNER);

        getStyleClass().add(StyleClasses.MESSAGE);
        getChildren().add(messageBox);
    }
}
