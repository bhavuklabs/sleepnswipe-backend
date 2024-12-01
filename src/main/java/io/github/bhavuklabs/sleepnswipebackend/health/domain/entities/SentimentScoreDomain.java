package io.github.bhavuklabs.sleepnswipebackend.health.domain.entities;

import java.util.List;

public class SentimentScoreDomain {

    private List<Sentiment> sentimentsMap;

    public SentimentScoreDomain(List<Sentiment> sentimentsMap) {
        this.sentimentsMap = sentimentsMap;
    }

    public List<Sentiment> getSentimentsMap() {
        return sentimentsMap;
    }

    public void setSentimentsMap(List<Sentiment> sentimentsMap) {
        this.sentimentsMap = sentimentsMap;
    }

    public static class Sentiment {
        private String sentiment;
        private Double value;

        public Sentiment(String sentiment, Double value) {
            this.sentiment = sentiment;
            this.value = value;
        }

        public String getSentiment() {
            return sentiment;
        }

        public void setSentiment(String sentiment) {
            this.sentiment = sentiment;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }
    }
}
