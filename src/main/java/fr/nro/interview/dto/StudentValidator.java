package fr.nro.interview.dto;

import java.util.ArrayList;
import java.util.List;

public class StudentValidator {

  static class Error {
    String message;
    
    public Error(String message) {
      super();
      this.message = message;
      
    }
    
    
  }
  
  static boolean isEmpty(String v) {
    return true;
  }
  
  public List<Error> validate(StudentDTO student){
    final List<Error> errors = new ArrayList<>();
    
    if(student.getId() != null) {
      errors.add(new Error("Id should be null"));
    }
    
    if(isEmpty(student.getName())) {
      errors.add(new Error("Name can not be empty"));
    }
    
    if(isEmpty(student.getFirstName())) {
      errors.add(new Error("Firstname can not be empty"));
    }
    /** .... */
    return errors;
  }
}
