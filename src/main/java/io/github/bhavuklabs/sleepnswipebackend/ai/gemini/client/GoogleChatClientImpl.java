package io.github.bhavuklabs.sleepnswipebackend.ai.gemini.client;

import io.github.bhavuklabs.sleepnswipebackend.ai.gemini.pojo.GeminiResponsePojo;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIRequestDomain;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIResponseDomain;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleChatClientImpl implements GoogleChatClient {

    private final String apiUrl;
    private final String apiKey;
    private final RestTemplate restTemplate;

    public GoogleChatClientImpl(final String apiUrl, final String apiKey, final RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }


    @Override
    public String sendMessage(AIRequestDomain request) throws URISyntaxException {
        String url = apiUrl + apiKey;
        var map = new HashMap<String, List<String>>();
        map.put("Content-Type", List.of("application/json"));
        LinkedMultiValueMap<String, String> linkedMap = new LinkedMultiValueMap<>();
        linkedMap.putAll(map);
        HttpRequest requestBuilder = HttpRequest.newBuilder()
                .uri(new URI(url))
                .setHeader("Authorization", "Bearer "+apiKey)
                .setHeader("Content-Type", "application/json").build();
        HttpEntity<AIRequestDomain> entity = new HttpEntity<>(request, linkedMap);
        ResponseEntity<GeminiResponsePojo> response = this.restTemplate.postForEntity(url, entity, GeminiResponsePojo.class);
        String content = response.getBody().getCandidates().get(0).getContent().getParts().get(0).getText();
        content = content.replace("```json", "").replace("```", "");

        return content;
    }

    @Override
    public Map<String, String> getHistoryContext(String history) {
        Map<String, String> context = new HashMap<>();
        String[] entries = history.split("\n");
        for(String entry : entries) {
            String[] keyValue = entry.split(":");
            if(keyValue.length == 2) {
                context.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }

        return context;
    }
}
