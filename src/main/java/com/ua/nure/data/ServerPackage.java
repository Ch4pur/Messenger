package com.ua.nure.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class ServerPackage {
    @Getter
    @Setter
    @JsonProperty
    private String commandName;

    @Getter
    @JsonProperty
    private final Map<String, Object> attributes;

    public ServerPackage() {
        attributes = new HashMap<>();
    }

    public void addAttribute(String name, Object attribute) {
        attributes.put(name, attribute);
    }
}
