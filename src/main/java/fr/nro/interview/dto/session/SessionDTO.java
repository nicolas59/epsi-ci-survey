package fr.nro.interview.dto.session;

import java.util.List;

import javax.validation.constraints.NotNull;

import fr.nro.interview.dto.Identifier;

public class SessionDTO extends Identifier<Long> {
  
  /**
   * 
   */
  private static final long serialVersionUID = -5618872777375434695L;

  @NotNull
  private Identifier<Long> surveyId;
  
  private List<Identifier<Long>> studentIds;
  
  private Integer duration;

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public Identifier<Long> getSurveyId() {
    return surveyId;
  }

  public void setSurveyId(Identifier<Long> surveyId) {
    this.surveyId = surveyId;
  }

  public List<Identifier<Long>> getStudentIds() {
    return studentIds;
  }

  public void setStudentIds(List<Identifier<Long>> studentIds) {
    this.studentIds = studentIds;
  }

}
