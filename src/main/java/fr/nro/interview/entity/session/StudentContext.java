package fr.nro.interview.entity.session;

import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.nro.interview.entity.Student;
import fr.nro.interview.entity.interview.Question;
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
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar startedDate;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar endDate;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar deathLine;
  
  @OneToMany(mappedBy="studentContext", cascade=CascadeType.ALL)
  @OrderBy("position asc")
  private Set<SessionCtxQuestion> sessionCtxQuestion;

  @Column(unique = true)
  private String uuid;
  
  @ManyToOne(optional= true)
  @JoinColumn(name = "last_question_answered_id")
  private Question lastQuestionAnswered;

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

  public Calendar getStartedDate() {
    return startedDate;
  }

  public void setStartedDate(Calendar startedDate) {
    this.startedDate = startedDate;
  }

  public Calendar getEndDate() {
    return endDate;
  }

  public void setEndDate(Calendar endDate) {
    this.endDate = endDate;
  }

  public Calendar getDeathLine() {
    return deathLine;
  }

  public void setDeathLine(Calendar deathLine) {
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

  public Question getLastQuestionAnswered() {
    return lastQuestionAnswered;
  }

  public void setLastQuestionAnswered(Question lastQuestionAnswered) {
    this.lastQuestionAnswered = lastQuestionAnswered;
  }
  
  

}
