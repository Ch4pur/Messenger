package com.ua.nure.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
public class ClientPackage {

    @Getter
    @Setter
    @JsonProperty
    private String commandName;

    @JsonProperty
    private Map<String, Object> attributes = new HashMap<>();

    @Getter
    @JsonIgnore
    private final List<Long> receiversId = new ArrayList<>();

    @Getter
    @Setter
    @JsonProperty
    private String exceptionMessage;

    public void addReceiverId(long id) {
        receiversId.add(id);
    }

    public void addAttribute(String key, Object attribute) {
        attributes.put(key, attribute);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }
}
