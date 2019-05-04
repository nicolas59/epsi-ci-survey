package fr.nro.interview.entity.session;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import fr.nro.interview.entity.Student;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class StudentContext extends PanacheEntity {

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private Session session;

  @Column
  private LocalDate startedDate;

  @Column
  private LocalDate endDate;
  
  @Column
  private LocalDate deathLine;

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }

  public LocalDate getStartedDate() {
    return startedDate;
  }

  public void setStartedDate(LocalDate startedDate) {
    this.startedDate = startedDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalDate getDeathLine() {
    return deathLine;
  }

  public void setDeathLine(LocalDate deathLine) {
    this.deathLine = deathLine;
  }

}
