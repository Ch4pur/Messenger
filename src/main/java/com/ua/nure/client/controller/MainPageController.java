package com.ua.nure.client.controller;

import com.ua.nure.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainPageController extends Controller {
    @Autowired
    public MainPageController(Client client) {
        super(client);
    }
}
