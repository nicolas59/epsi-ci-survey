package fr.nro.interview.dto.session;

import java.util.List;

import fr.nro.interview.dto.interview.QuestionDTO;

public class ExamDTO {

  public enum Status {
    PENDING, END
  }
  
  private Status status;
  
  private List<QuestionDTO> questions;

  public List<QuestionDTO> getQuestions() {
    return questions;
  }

  public void setQuestions(List<QuestionDTO> questions) {
    this.questions = questions;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
  
  
  
  
}
