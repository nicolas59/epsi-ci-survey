package fr.nro.interview.mapper;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import fr.nro.interview.dto.interview.SurveyDTO;
import fr.nro.interview.entity.interview.Survey;

@ApplicationScoped
public class SurveyMapper implements Function<Survey , SurveyDTO>{

  @Inject
  QuestionMapper questionMapper;
  
  @Override
  public SurveyDTO apply(Survey t) {
    SurveyDTO surveyDTO = new SurveyDTO();
    surveyDTO.setName(t.getLabel());
    surveyDTO.setId(t.id);
    
    if(t.getQuestions() != null) {
      surveyDTO.setQuestions(new ArrayList<>(t.getQuestions()
          .stream()
          .map(questionMapper)
          .collect(toList())));
    }

    return surveyDTO;
  }

}
