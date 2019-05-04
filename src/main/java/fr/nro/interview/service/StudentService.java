package fr.nro.interview.service;

import java.util.List;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import fr.nro.interview.dto.StudentDTO;
import fr.nro.interview.entity.Category;
import fr.nro.interview.entity.Student;
import fr.nro.interview.repository.StudentRepository;

@ApplicationScoped
public class StudentService {
  
  @Inject
  StudentRepository studentRepository;
  
  private Function<StudentDTO, Student> mapper = (studentDto) -> {
      Student st =  new Student();
      st.id = studentDto.getId();
      st.setFirstName(studentDto.getFirstName());
      st.setName(studentDto.getName());
      st.setEmail(studentDto.getEmail());
      
      //map category
      st.setCategory(Category.findById(studentDto.getCategory().getId()));
      
      return st;
  };
  
  public Student findById(Long id) {
    return Student.findById(id);
  }
  
  public List<Student> findAll() {
    return Student.findAll().list();
  }
  
  
  public void saveStudent(@Valid StudentDTO student) throws ConstraintViolationException{
    Student st  = mapper.apply(student);
    st.id = null;
    //st.persist();
    this.studentRepository.persist(st);
    student.setId(st.id);
  }
}
