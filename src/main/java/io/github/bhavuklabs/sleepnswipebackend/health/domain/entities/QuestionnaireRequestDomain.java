package io.github.bhavuklabs.sleepnswipebackend.health.domain.entities;

import java.util.List;

public record QuestionnaireRequestDomain(
        List<String> questionsList,
        List<String> optionsList
) {
}
