package com.ua.nure.client.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private final ObjectMapper objectMapper;

    public Parser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> List<T> parseList(List<?> unparsedList, Class<T> clazz) {
        CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return objectMapper.convertValue(unparsedList, type);
    }

    public <K, V> Map<K, V> parseMap(Map<?, ?> unparsedMap, Class<K> keyClass, Class<V> valueClass) {
        Map<K, V> parsedMap = new HashMap<>();
        for (Map.Entry<?, ?> entry : unparsedMap.entrySet()) {
            K key = objectMapper.convertValue(entry.getKey(), keyClass);
            V value = objectMapper.convertValue(entry.getValue(), valueClass);
            parsedMap.put(key, value);
        }
        return parsedMap;
    }
}
