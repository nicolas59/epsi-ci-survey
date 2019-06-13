package fr.nro.interview.dto.interview;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import fr.nro.interview.dto.Identifier;

public class ItemDTO extends Identifier<Long>{
  
  /**
   * 
   */
  private static final long serialVersionUID = 924446458270946558L;
  
  @NotNull
  private Integer order;
  
  @NotBlank
  private String label;
  
  @NotBlank
  private String reference;

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }
  
  

}
