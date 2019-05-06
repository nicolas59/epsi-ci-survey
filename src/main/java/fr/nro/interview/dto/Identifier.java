package fr.nro.interview.dto;

import java.io.Serializable;

public class Identifier<T> implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 5920804525581159133L;
  
  private T id;
  
  public Identifier(T id) {
    this.id = id;
  }
  
  public Identifier() {
    
  }

  public T getId() {
    return id;
  }

  public void setId(T id) {
    this.id = id;
  }
  
  
}
