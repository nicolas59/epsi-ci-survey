package fr.nro.interview.service;

import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;
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
import fr.nro.interview.mapper.SurveyMapper;
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
  SurveyMapper mapperDTO;

  @Inject
  QuestionDTOMapper questionMapper;

  public SurveyService() {
    super();
  }

  public SurveyService(SurveyRepository surveyRepository, QuestionRepository questionRepository, SurveyDTOMapper mapper, QuestionDTOMapper questionMapper) {
    super();
    this.surveyRepository = surveyRepository;
    this.questionRepository = questionRepository;
    this.mapper = mapper;
    this.questionMapper = questionMapper;
  }

  public void save(@Valid SurveyDTO interviewDTO) throws ConstraintViolationException {
    Survey interview = this.mapper.apply(interviewDTO);
    interview.id = null;

    surveyRepository.persist(interview);

    interviewDTO.setId(interview.id);
  }

  public List<SurveyDTO> findAll() {
    return this.surveyRepository.findAll()
      .stream()
      .map(mapperDTO)
      .collect(toList());
  }

  public SurveyDTO findById(Long id) {
    Survey survey = this.surveyRepository.findById(id);
    return survey != null ? this.mapperDTO.apply(survey) : null;
  }

  public void addQuestion(Long interviewId, @Valid QuestionDTO questionDTO) throws ConstraintViolationException {
    Question question = this.questionMapper.apply(questionDTO);
    question.id = null;

    Survey survey = this.surveyRepository.findById(interviewId);
    if (survey == null) {
      Set violations = new HashSet<>();
      violations.add(ContraintViolationUtils.createConstraint("survey", "Survey does not exist", Survey.class));
      throw new ConstraintViolationException(violations);
    }

    question.setSurvey(survey);
    this.questionRepository.persist(question);

    questionDTO.setId(question.id);
  }

}
