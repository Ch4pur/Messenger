package com.ua.nure.client.controller;

import com.ua.nure.client.annotation.ClientCommand;
import com.ua.nure.client.tag.ChatPane;
import com.ua.nure.client.tag.RoomsPane;
import com.ua.nure.data.ClientPackage;

import static com.ua.nure.util.ClientCommandNames.*;
public class MainPageController extends Controller {

    private RoomsPane roomsPane;
    private ChatPane chatPane;

    @ClientCommand(UPDATE_MESSAGES_PANE)
    private void updateMessages(ClientPackage clientPackage) {
    }

    @ClientCommand(UPDATE_ROOMS_PANE)
    private void updateRooms(ClientPackage clientPackage) {
    }
}
