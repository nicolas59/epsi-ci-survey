package fr.nro.interview.dto.session;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import fr.nro.interview.dto.Identifier;

public class AnswerDTO {

  /**
   * Contexte 
   */
  @NotBlank
  private String uuid;
  
  /**
   * Proposal answer
   */
  @NotNull
  private String answer;
  
  /**
   * Identifier's question
   */
  private Identifier<Long> questionId;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public Identifier<Long> getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Identifier<Long> questionId) {
    this.questionId = questionId;
  }
  
}
