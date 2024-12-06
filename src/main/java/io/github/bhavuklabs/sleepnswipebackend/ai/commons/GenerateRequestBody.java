package io.github.bhavuklabs.sleepnswipebackend.ai.commons;

import io.github.bhavuklabs.javageminiclient.commons.prompt.RequestPrompt;
import io.github.bhavuklabs.javageminiclient.commons.utilities.commons.Content;
import io.github.bhavuklabs.javageminiclient.commons.utilities.commons.Part;
import io.github.bhavuklabs.javageminiclient.commons.utilities.request.RequestBody;

import java.util.List;

public class GenerateRequestBody {
    public static RequestBody getRequestBody(String prompt) {
        Part<String> part = new Part<>(new RequestPrompt<>(prompt));
        Content content = new Content(List.of(part));
        RequestBody requestBody = new RequestBody(List.of(content));
        return requestBody;
    }
}
