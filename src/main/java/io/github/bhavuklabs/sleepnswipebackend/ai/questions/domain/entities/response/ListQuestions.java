package io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.response;

import io.github.bhavuklabs.sleepnswipebackend.ai.questions.domain.entities.QuestionDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListQuestions {

    private List<QuestionDomain> questions;
}
