package fr.nro.interview.mapper;

import static java.util.stream.Collectors.toList;

import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Singleton;

import fr.nro.interview.dto.interview.SurveyDTO;
import fr.nro.interview.entity.interview.Survey;

@Singleton
public class SurveyDTOMapper implements Function<SurveyDTO, Survey> {

  QuestionDTOMapper questionDTOMapper;
  
  @Inject
  public SurveyDTOMapper(QuestionDTOMapper questionDTOMapper) {
    super();
    this.questionDTOMapper = questionDTOMapper;
  }

  @Override
  public Survey apply(SurveyDTO t) {
    Survey interview  = new Survey();
    interview.id = t.getId();
    interview.setLabel(t.getName());
    if(t.getQuestions() !=null) {
      interview.setQuestions(t.getQuestions().stream().map(questionDTOMapper).collect(toList()));
    }
    return interview;
  }
}
