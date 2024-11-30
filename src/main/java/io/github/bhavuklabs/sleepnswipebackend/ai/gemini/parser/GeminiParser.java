package io.github.bhavuklabs.sleepnswipebackend.ai.gemini.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bhavuklabs.sleepnswipebackend.ai.gemini.pojo.GeminiResponsePojo;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.response.ResponseParser;

public class GeminiParser extends ResponseParser<GeminiResponsePojo> {
    public GeminiParser(Class<GeminiResponsePojo> responsePojo, ObjectMapper objectMapper) {
        super(responsePojo, objectMapper);
    }
}
