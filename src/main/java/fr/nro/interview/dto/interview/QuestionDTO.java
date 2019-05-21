package fr.nro.interview.dto.interview;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import fr.nro.interview.dto.Identifier;

public class QuestionDTO extends Identifier<Long>{

  /**
   * 
   */
  private static final long serialVersionUID = -6963304060233724024L;
  
  @Valid
  private ArrayList<ItemDTO> items;
  
  @NotBlank
  private String label;
  
  @NotNull
  private Integer score;
  
  @NotBlank
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


  public List<ItemDTO> getItems() {
    return items;
  }


  public void setItems(ArrayList<ItemDTO> items) {
    this.items = items;
  }

}
