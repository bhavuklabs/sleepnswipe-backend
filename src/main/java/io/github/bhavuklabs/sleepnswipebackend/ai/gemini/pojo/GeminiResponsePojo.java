package io.github.bhavuklabs.sleepnswipebackend.ai.gemini.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain.AIRequestDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeminiResponsePojo {

    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Candidate {
        private Content content;
        private String finishReason;
        private double avgLogprobs;

        @Override
        public String toString() {
            return "Candidate{" +
                    "content=" + content +
                    ", finishReason='" + finishReason + '\'' +
                    ", avgLogprobs=" + avgLogprobs +
                    '}';
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Content {
        private List<AIRequestDomain.Part> parts;
        private String role;

        @Override
        public String toString() {
            return "Content{" +
                    "parts=" + parts +
                    ", role='" + role + '\'' +
                    '}';
        }
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UsageMetadata {
        @JsonProperty("promptTokenCount")
        private int promptTokenCount;

        @JsonProperty("candidatesTokenCount")
        private int candidatesTokenCount;

        @JsonProperty("totalTokenCount")
        private int totalTokenCount;

        @Override
        public String toString() {
            return "UsageMetadata{" +
                    "promptTokenCount=" + promptTokenCount +
                    ", candidatesTokenCount=" + candidatesTokenCount +
                    ", totalTokenCount=" + totalTokenCount +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GeminiResponsePojo{" +
                "candidates=" + candidates +
                ", usageMetadata=" + usageMetadata +
                ", modelVersion='" + modelVersion + '\'' +
                '}';
    }
}
