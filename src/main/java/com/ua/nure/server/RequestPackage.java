package com.ua.nure.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class RequestPackage {
    @Getter
    @Setter
    @JsonProperty
    private String commandName;

    @Getter
    @JsonProperty
    private final Map<String, Object> attributes;

    public RequestPackage() {
        attributes = new HashMap<>();
    }

    public void addAttribute(String name, Object attribute) {
        attributes.put(name, attribute);
    }
}
