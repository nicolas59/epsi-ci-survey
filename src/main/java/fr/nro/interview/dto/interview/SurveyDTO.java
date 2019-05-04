package fr.nro.interview.dto.interview;

import java.util.List;

import fr.nro.interview.dto.Identifier;

public class SurveyDTO extends Identifier<Long>{

  /**
   * 
   */
  private static final long serialVersionUID = 369770988214886699L;

  private String name;
  
  private List<QuestionDTO> questions;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<QuestionDTO> getQuestions() {
    return questions;
  }

  public void setQuestions(List<QuestionDTO> questions) {
    this.questions = questions;
  }
  
}
