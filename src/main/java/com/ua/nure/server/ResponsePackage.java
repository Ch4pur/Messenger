package com.ua.nure.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ToString
public class ResponsePackage {
    @Getter
    private final List<Long> receiversId;

    @Getter
    @Setter
    @JsonProperty
    private String commandName;

    @Getter
    @JsonProperty
    private Map<String, Object> attributes;

    @Getter
    @JsonProperty
    private final Map<String, Object> cacheChanges;

    @Getter
    @Setter
    @JsonProperty
    private String exceptionMessage;

    public ResponsePackage() {
        cacheChanges = new HashMap<>();
        receiversId = new ArrayList<>();
        attributes = new HashMap<>();
    }

    public void putCacheChange(String key, Object object) {
        cacheChanges.put(key, object);
    }

    public void addReceiverId(long id) {
        receiversId.add(id);
    }

    public void addAttribute(String key, Object attribute) {
        attributes.put(key, attribute);
    }
}
