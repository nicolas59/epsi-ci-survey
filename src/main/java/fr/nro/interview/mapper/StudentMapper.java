package fr.nro.interview.mapper;

import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;

import fr.nro.interview.dto.Identifier;
import fr.nro.interview.dto.StudentDTO;
import fr.nro.interview.entity.Student;

@ApplicationScoped
public class StudentMapper implements Function<Student, StudentDTO>{

  @Override
  public StudentDTO apply(Student student) {
    if (student == null) {
      return null;
    }
    StudentDTO studentDTO = new StudentDTO();
    studentDTO.setId(student.id);
    studentDTO.setName(student.getName());
    studentDTO.setFirstName(student.getFirstName());
    studentDTO.setEmail(student.getEmail());
    studentDTO.setCategory(new Identifier<>(student.getCategory().id));
    return studentDTO;
  }

  
  
}
