package io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.repositories;

import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QuestionsRepository extends JpaRepository<Questions, UUID> {
    Optional<Questions> findByQuestionName(String questionName);

}
