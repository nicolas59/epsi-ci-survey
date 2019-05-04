package fr.nro.interview.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import fr.nro.interview.validator.CategoryIdentifier;

public class StudentDTO extends Identifier<Long>{

  /**
   * 
   */
  private static final long serialVersionUID = -1259316307899095194L;

  @NotNull
  @CategoryIdentifier
  private Identifier<Long> category;
  
  @NotEmpty(message="Name can not be empty")
  private String name;
  
  @NotEmpty(message="firstName can not be empty")
  private String firstName;

  @NotEmpty(message="email can not be empty")
  @Pattern(regexp="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message="Email format invalid")
  private String email;

  public Identifier<Long> getCategory() {
    return category;
  }

  public void setCategory(Identifier<Long> category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
