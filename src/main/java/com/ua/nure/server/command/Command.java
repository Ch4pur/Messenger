package com.ua.nure.server.command;

import com.ua.nure.server.exception.CommandException;
import com.ua.nure.data.ClientPackage;

import java.util.Map;

public interface Command {
    ClientPackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException;
}
