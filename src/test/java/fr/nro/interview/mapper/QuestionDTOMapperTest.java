package fr.nro.interview.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.nro.interview.dto.interview.QuestionDTO;
import fr.nro.interview.entity.interview.Question;

public class QuestionDTOMapperTest {
  
  QuestionDTOMapper mapper;
  
  @BeforeEach
  public void setUp() {
    mapper = new QuestionDTOMapper();
  }
  
  
  @Test
  public void should_map_dto_without_items() {
    // TODO faire le test unitaire permettant de valider que les données sont
    // correctement recopiées en l'absence d'items
    
    // preparation de la données.
    QuestionDTO questionDTO = new QuestionDTO();
    questionDTO.setId(1L);
    questionDTO.setLabel("Question 1: Quel est votre langage prefere ?" );
    questionDTO.setScore(1);
    questionDTO.setResponse("R1");
    
    // appel de la méthode
    Question question = mapper.apply(questionDTO);
    
    // controle
    assertEquals(questionDTO.getLabel(),  question.getLabel());
    assertEquals(questionDTO.getResponse(), question.getResponse());
    assertEquals(questionDTO.getScore(), question.getScore());
    assertEquals(questionDTO.getId(), question.id);
    
    assertNull(question.getItems());
  }
  
  @Test
  public void should_map_dto_with_items() {
   
    
  }
}
