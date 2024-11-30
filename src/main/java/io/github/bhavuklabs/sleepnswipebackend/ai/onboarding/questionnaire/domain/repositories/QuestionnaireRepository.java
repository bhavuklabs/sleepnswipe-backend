package io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.ai.onboarding.questionnaire.domain.models.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, UUID> {

}
