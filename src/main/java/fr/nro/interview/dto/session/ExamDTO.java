package fr.nro.interview.dto.session;

import java.util.List;

import fr.nro.interview.dto.Identifier;
import fr.nro.interview.dto.interview.QuestionDTO;

public class ExamDTO {

  public enum Status {
    PENDING, END, EXPIRED
  }

  private Status status;

  private List<QuestionDTO> questions;

  private Identifier<Long> lastQuestionAnswered;
  
  private Long remainingTimeInMinutes;

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

  public Identifier<Long> getLastQuestionAnswered() {
    return lastQuestionAnswered;
  }

  public void setLastQuestionAnswered(Identifier<Long> lastQuestionAnswered) {
    this.lastQuestionAnswered = lastQuestionAnswered;
  }

  public Long getRemainingTimeInMinutes() {
    return remainingTimeInMinutes;
  }

  public void setRemainingTimeInMinutes(Long remainingTimeInMinutes) {
    this.remainingTimeInMinutes = remainingTimeInMinutes;
  }
}
