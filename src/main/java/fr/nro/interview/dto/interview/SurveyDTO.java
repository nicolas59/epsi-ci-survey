package fr.nro.interview.dto.interview;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import fr.nro.interview.dto.Identifier;

public class SurveyDTO extends Identifier<Long>{

  @NotBlank
  private String name;
  
  @Valid
  private ArrayList<QuestionDTO> questions;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<QuestionDTO> getQuestions() {
    return questions;
  }

  public void setQuestions(ArrayList<QuestionDTO> questions) {
    this.questions = questions;
  }
  
}
