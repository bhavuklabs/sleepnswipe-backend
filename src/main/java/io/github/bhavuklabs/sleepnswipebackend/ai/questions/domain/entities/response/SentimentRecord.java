package io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.response;

import java.util.List;

public record SentimentRecord(
        List<SentimentDomain> sentimentsMap
) {
    public static class SentimentDomain {
        private String sentiment;
        private Double value;

        public SentimentDomain(String sentiment, Double value) {
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
