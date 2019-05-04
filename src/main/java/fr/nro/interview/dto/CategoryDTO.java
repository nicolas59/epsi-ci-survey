package fr.nro.interview.dto;

public class CategoryDTO extends Identifier<Long> {

  /**
   * 
   */
  private static final long serialVersionUID = -1198718503530215686L;
  
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
