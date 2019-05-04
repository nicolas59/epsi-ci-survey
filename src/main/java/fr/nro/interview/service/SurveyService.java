package fr.nro.interview.service;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import fr.nro.interview.ContraintViolationUtils;
import fr.nro.interview.dto.interview.QuestionDTO;
import fr.nro.interview.dto.interview.SurveyDTO;
import fr.nro.interview.entity.interview.Question;
import fr.nro.interview.entity.interview.Survey;
import fr.nro.interview.mapper.QuestionDTOMapper;
import fr.nro.interview.mapper.SurveyDTOMapper;
import fr.nro.interview.repository.QuestionRepository;
import fr.nro.interview.repository.SurveyRepository;

@ApplicationScoped
public class SurveyService {

  @Inject
  SurveyRepository surveyRepository;

  @Inject
  QuestionRepository questionRepository;

  @Inject
  SurveyDTOMapper mapper;

  @Inject
  QuestionDTOMapper questionMapper;

  public void save(@Valid SurveyDTO interviewDTO) throws ConstraintViolationException {
    Survey interview = this.mapper.apply(interviewDTO);
    interview.id = null;

    surveyRepository.persist(interview);

    interviewDTO.setId(interview.id);
  }

  public void addQuestion(Long interviewId, @Valid QuestionDTO questionDTO) throws ConstraintViolationException {
    Question question = this.questionMapper.apply(questionDTO);
    question.id = null;

    Survey survey = this.surveyRepository.findById(interviewId);
    if (survey == null) {
      throw new ConstraintViolationException(
          Set.of(ContraintViolationUtils.createConstraint("survey", "Survey does not exist", Survey.class)));
    }

    question.setSurvey(survey);
    this.questionRepository.persist(question);
    
    questionDTO.setId(question.id);
  }

}
