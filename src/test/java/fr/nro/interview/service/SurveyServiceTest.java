package fr.nro.interview.service;

import static org.mockito.Mockito.when;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import fr.nro.interview.dto.interview.QuestionDTO;
import fr.nro.interview.entity.interview.Question;
import fr.nro.interview.entity.interview.Survey;
import fr.nro.interview.mapper.QuestionDTOMapper;
import fr.nro.interview.mapper.SurveyDTOMapper;
import fr.nro.interview.repository.QuestionRepository;
import fr.nro.interview.repository.SurveyRepository;

@RunWith(MockitoJUnitRunner.class)
public class SurveyServiceTest {

  private SurveyService surveyService;

  @Mock
  SurveyRepository surveyRepository;

  @Mock
  QuestionRepository questionRepository;

  /**
   * Initialisation des mocks.
   */
  @Before
  public void setUp() {
    //TODO
    
    QuestionDTOMapper questionDTOMapper = new QuestionDTOMapper();
    SurveyDTOMapper surveyDTOMapper = new SurveyDTOMapper(questionDTOMapper);
    
    surveyService = new SurveyService(surveyRepository, 
        questionRepository, 
        surveyDTOMapper, 
        questionDTOMapper);
    
  }

  /**
   * Verifier que le sondage est bien créé.
   */
  @Test
  public void should_create_new_survey() {
    // TODO Réaliser les tests
  }

  /**
   * Verifier que la méthode <tt>addQuestion</tt> leve une exception quand le
   * sondage n'existe pas.
   */
  @Test(expected=ConstraintViolationException.class)
  public void should_throw_exception_when_survey_not_found() {
    // TODO Réaliser les tests
    QuestionDTO questionDTO = new QuestionDTO();
    questionDTO.setLabel("Q1");
    
    when(this.surveyRepository.findById(1L))
    .thenReturn(null);
    
    this.surveyService.addQuestion(1L, questionDTO);
  }

  /**
   * Verifier que la question est correctement ajouté au sondage
   */
  @Test
  public void should_add_question_when_survey_found() {
    QuestionDTO questionDTO = new QuestionDTO();
    questionDTO.setLabel("Q1");
    
    when(this.surveyRepository.findById(1L))
    .thenReturn(new Survey());
    
    this.surveyService.addQuestion(1L, questionDTO);
    
    Mockito.verify(this.questionRepository,  Mockito.times(1))
          .persist(ArgumentMatchers.any(Question.class));;
  }
}
