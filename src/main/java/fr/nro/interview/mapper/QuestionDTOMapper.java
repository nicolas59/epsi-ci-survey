package fr.nro.interview.mapper;

import static java.util.stream.Collectors.toSet;

import java.util.function.Function;

import javax.inject.Singleton;

import fr.nro.interview.dto.interview.QuestionDTO;
import fr.nro.interview.entity.interview.Item;
import fr.nro.interview.entity.interview.Question;

@Singleton
public class QuestionDTOMapper implements Function<QuestionDTO, Question> {

  @Override
  public Question apply(QuestionDTO t) {
    final Question question = new Question();
    question.id = t.getId();
    question.setLabel(t.getLabel());
    question.setScore(t.getScore());
    question.setResponse(t.getResponse());
    if (t.getItems() != null) {
      question.setItems(t.getItems()
        .stream()
        .map(item -> new Item(item.getOrder(), item.getLabel(), question))
        .collect(toSet()));
    }
    return question;
  }

}
