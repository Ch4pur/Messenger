package com.ua.nure.client.node;

import com.ua.nure.client.util.StyleClasses;
import com.ua.nure.server.model.entity.Room;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


public class RoomBox extends AnchorPane {

    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

    private final Room room;

    private BooleanProperty isActiveProperty;

    public RoomBox(Room room) {
        this.room = room;
        isActiveProperty = new SimpleBooleanProperty(false);

        isActiveProperty.set(false);
        createRoomPanes();
    }

    public void setIsActiveProperty(boolean isActiveProperty) {
        this.isActiveProperty.set(isActiveProperty);
    }

    private void createRoomPanes() {
        Label roomTitle = new Label(room.getTitle());
        roomTitle.getStyleClass().add("title");
        getStyleClass().add(StyleClasses.ROOM);

        getChildren().add(roomTitle);

        isActiveProperty.addListener(e -> pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isActiveProperty.get()));
    }

    public Room getRoom() {
        return room;
    }
}
