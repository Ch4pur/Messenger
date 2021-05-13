package com.ua.nure.server.command;

import com.ua.nure.exception.CommandException;
import com.ua.nure.server.ResponsePackage;

import java.util.Map;

public interface Command {
    ResponsePackage execute(Map<String, Object> session, Map<String, Object> attributes) throws CommandException;
}
