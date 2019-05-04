package fr.nro.interview.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

public class ErrorDTO {

  private String field;

  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public static List<ErrorDTO> create(Set<ConstraintViolation<?>> violations) {
    return violations.stream()
      .map(violation -> {
        ErrorDTO error = new ErrorDTO();
        error.setMessage(violation.getMessage());
        error.setField(violation.getPropertyPath()
          .toString());
        return error;
      })
      .collect(Collectors.toList());
  }

}
