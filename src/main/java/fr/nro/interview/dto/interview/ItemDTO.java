package fr.nro.interview.dto.interview;

import fr.nro.interview.dto.Identifier;

public class ItemDTO extends Identifier<Long>{
  
  /**
   * 
   */
  private static final long serialVersionUID = 924446458270946558L;
  
  private Integer order;
  
  private String label;

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
  
  

}
