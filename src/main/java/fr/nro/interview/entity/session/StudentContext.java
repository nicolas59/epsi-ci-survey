package fr.nro.interview.entity.session;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

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
  
  @OneToMany(mappedBy="studentContext", cascade=CascadeType.ALL)
  @OrderBy("position asc")
  private Set<SessionCtxQuestion> sessionCtxQuestion;

  @Column(unique = true)
  private String uuid;

  public StudentContext() {
    super();
  }

  public StudentContext(Student student, Session session) {
    super();
    this.student = student;
    this.session = session;
    this.uuid = UUID.randomUUID().toString();
  }

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

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Set<SessionCtxQuestion> getSessionCtxQuestion() {
    return sessionCtxQuestion;
  }

  public void setSessionCtxQuestion(Set<SessionCtxQuestion> sessionCtxQuestion) {
    this.sessionCtxQuestion = sessionCtxQuestion;
  }

}
