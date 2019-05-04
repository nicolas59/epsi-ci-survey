package fr.nro.interview.entity.interview;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;


@Entity
public class Item extends PanacheEntity{

  private String label;
  
  @Column(name="itemOrder")
  private Integer order;
  
  @Column
  private String functionalReference;
  
  @ManyToOne()
  @JoinColumn(name="question_id")
  private Question question;

  public Item() {
    super();
  }

  public Item(Integer order, String label, Question question) {
    super();
    this.order = order;
    this.label = label;
    this.question = question;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }
  
  public String getFunctionalReference() {
    return functionalReference;
  }

  public void setFunctionalReference(String functionalReference) {
    this.functionalReference = functionalReference;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }
}
