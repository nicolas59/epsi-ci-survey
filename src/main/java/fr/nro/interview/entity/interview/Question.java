package fr.nro.interview.entity.interview;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Question extends PanacheEntity{

  @NotNull
  @Size(max=250)
  private String label;
  
  @OneToMany(mappedBy="question", cascade= CascadeType.ALL)
  @OrderBy("itemOrder")
  private Set<Item> items;
  
  @ManyToOne
  @JoinColumn(name="survey_id")
  private Survey survey;
  
  @Column
  @NotNull
  private Integer score;
  
  @Column
  private String response;

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Set<Item> getItems() {
    return items;
  }

  public void setItems(Set<Item> items) {
    this.items = items;
  }

  public Survey getSurvey() {
    return survey;
  }

  public void setSurvey(Survey survey) {
    this.survey = survey;
  }
}
