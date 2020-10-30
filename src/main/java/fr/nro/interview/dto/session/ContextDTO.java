package fr.nro.interview.dto.session;

import java.time.LocalDateTime;
import java.util.Date;

import fr.nro.interview.dto.StudentDTO;

public class ContextDTO {

  private StudentDTO student;

  private String uuid;
  
  private Integer score;
  
  private Date startedDate;
  
  private LocalDateTime endDate;
   
  private LocalDateTime deathLine;

  public StudentDTO getStudent() {
    return student;
  }

  public void setStudent(StudentDTO student) {
    this.student = student;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public Date getStartedDate() {
    return startedDate;
  }

  public void setStartedDate(Date startedDate) {
    this.startedDate = startedDate;
  }

  public LocalDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDateTime endDate) {
    this.endDate = endDate;
  }

  public LocalDateTime getDeathLine() {
    return deathLine;
  }

  public void setDeathLine(LocalDateTime deathLine) {
    this.deathLine = deathLine;
  }
  
}
