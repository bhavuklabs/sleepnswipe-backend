package io.github.bhavuklabs.sleepnswipebackend.commons.ai.domain;

import io.github.bhavuklabs.sleepnswipebackend.commons.ai.embedding.Embedding;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AIRequestDomain {

    private List<Content> contents;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Content {
        private List<Part> parts;

        @Override
        public String toString() {
            return "Content{" +
                    "parts=" + parts +
                    '}';
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Part {
        private String text;

        @Override
        public String toString() {
            return text;
        }
    }
}
