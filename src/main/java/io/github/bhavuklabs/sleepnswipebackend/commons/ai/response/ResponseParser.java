package io.github.bhavuklabs.sleepnswipebackend.commons.ai.response;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseParser<ParsedEntity> {

    private final Class<ParsedEntity> parsedEntityClass;
    private final ObjectMapper objectMapper;
    public ResponseParser(final Class<ParsedEntity> parsedEntityClass, final ObjectMapper objectMapper) {
        this.parsedEntityClass = parsedEntityClass;
        this.objectMapper = objectMapper;
    }

    public ParsedEntity parse(final String response) {
        try{
            return this.objectMapper.readValue(response, parsedEntityClass);
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't Parse Response");
        }
    }

}
