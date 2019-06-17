package fr.nro.interview.dto.session;

import fr.nro.interview.dto.StudentDTO;

public class ContextDTO {

  private StudentDTO student;

  private String uuid;

  public StudentDTO getStudent() {
    return student;
  }

  public void setStudent(StudentDTO student) {
    this.student = student;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  
  
}
