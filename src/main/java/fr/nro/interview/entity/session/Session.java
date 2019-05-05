package fr.nro.interview.entity.session;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fr.nro.interview.entity.interview.Survey;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Session extends PanacheEntity{
  
  public enum Status {
    CREATING, PENDING, FINISH
  }

  @Column
  private LocalDate createdDate;
  
  @Column
  private Integer duration;
  
  @Column
  private LocalDate startDate;
  
  @Column
  @Enumerated(EnumType.STRING)
  private Status status;
  
  @ManyToOne
  @JoinColumn(name="survey_id")
  private Survey survey;
  
  @OneToMany(mappedBy="session", cascade=CascadeType.ALL)
  private List<StudentContext> studentContexts;

  public LocalDate getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDate createdDate) {
    this.createdDate = createdDate;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Survey getSurvey() {
    return survey;
  }

  public void setSurvey(Survey survey) {
    this.survey = survey;
  }

  public List<StudentContext> getStudentContexts() {
    return studentContexts;
  }

  public void setStudentContexts(List<StudentContext> studentContexts) {
    this.studentContexts = studentContexts;
  }
}
