package io.github.bhavuklabs.sleepnswipebackend.health.domain.utilities;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SentimentLoader {

    private static SentimentLoader sentimentLoader = new SentimentLoader();

    private Map<String, Double> sentimentLoaderMap;

    private SentimentLoader() {

    }

    public static SentimentLoader getInstance() {
        return sentimentLoader;
    }

    public Map<String, Double> getSentimentLoaderMap() {
        return sentimentLoaderMap;
    }

    public SentimentLoader setSentimentLoaderMap() {
        Yaml yaml = new Yaml();
        try(var file = this.getClass().getClassLoader().getResourceAsStream("./sentiments.yml")) {
            Map<String, Object> map = yaml.load(file);
            var stringlist = (List<String>) map.get("attributes");
            sentimentLoaderMap = stringlist.stream()
                    .map(SentimentLoader::convertToLowercase)
                    .map(SentimentLoader::mapToAttribute)
                    .collect(Collectors.toMap(item -> item.attribute, item -> item.value()));
            return sentimentLoader;
        } catch(IOException e) {
            System.out.println(e);
            return null;
        }
    }

    private static String convertToLowercase(String attribute) {
        return attribute.toLowerCase();
    }

    private static Attribute mapToAttribute(String attributeName) {
        return new Attribute(attributeName, 0.0);
    }


    public record Attribute(String attribute, Double value){}


}
