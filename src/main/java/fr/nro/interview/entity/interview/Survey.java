package fr.nro.interview.entity.interview;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import fr.nro.interview.entity.session.Session;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Survey extends PanacheEntity {

  private String label;

  @OneToMany(mappedBy="survey", cascade=CascadeType.ALL)
  private List<Question> questions;
  
  @OneToMany(mappedBy="survey")
  private List<Session> session;

  public List<Session> getSession() {
    return session;
  }

  public void setSession(List<Session> session) {
    this.session = session;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public List<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(List<Question> questions) {
    this.questions = questions;
  }

}
