package fr.nro.interview.entity.session;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import fr.nro.interview.entity.interview.Question;

@Entity
public class SessionCtxQuestion {

  @EmbeddedId
  private SessionCtxAsso sessionCtxAsso;
  
  @ManyToOne
  @MapsId("student_ctx_id")
  @JoinColumn(name = "student_ctx_id")
  StudentContext studentContext;

  @ManyToOne
  @MapsId("question_id")
  @JoinColumn(name = "question_id")
  Question question;
  
  private Integer position;
  
  private String answer;

  public SessionCtxQuestion(StudentContext studentContext, Question question, Integer position) {
    super();
    this.setQuestion(question);
    this.setStudentContext(studentContext);
    this.position = position;
    
  }
  
  public SessionCtxQuestion() {
    super();
  }

  public SessionCtxAsso getSessionCtxAsso() {
    if(this.sessionCtxAsso == null) {
      this.sessionCtxAsso = new SessionCtxAsso();
    }
    return sessionCtxAsso;
  }

  public void setSessionCtxAsso(SessionCtxAsso sessionCtxAsso) {
    this.sessionCtxAsso = sessionCtxAsso;
  }

  public StudentContext getStudentContext() {
    return studentContext;
  }

  public void setStudentContext(StudentContext studentContext) {
    this.studentContext = studentContext;
    getSessionCtxAsso().setStudentCtxId(studentContext.id);
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
    getSessionCtxAsso().setQuestionId(this.question.id);
  }

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
  
}
