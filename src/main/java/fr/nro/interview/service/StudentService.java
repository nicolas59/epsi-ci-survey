package fr.nro.interview.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import fr.nro.interview.dto.Identifier;
import fr.nro.interview.dto.StudentDTO;
import fr.nro.interview.entity.Category;
import fr.nro.interview.entity.Student;
import fr.nro.interview.repository.StudentRepository;

@ApplicationScoped
public class StudentService {

  @Inject
  StudentRepository studentRepository;

  private Function<StudentDTO, Student> mapper = (studentDto) -> {
    Student st = new Student();
    st.id = studentDto.getId();
    st.setFirstName(studentDto.getFirstName());
    st.setName(studentDto.getName());
    st.setEmail(studentDto.getEmail());

    st.setCategory(Category.findById(studentDto.getCategory()
      .getId()));

    return st;
  };

  private Function<Student, StudentDTO> mapperDto = (student) -> {
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
  };

  public Optional<StudentDTO> findById(Long id) {
    return Optional.ofNullable(mapperDto.apply(Student.findById(id)));
  }

  public List<StudentDTO> findAll() {
    return studentRepository.findAll()
      .list()
      .stream()
      .map(mapperDto)
      .collect(toList());
  }

  public List<StudentDTO> findByCategory(Long categoryId) {
    return this.studentRepository.findByCategory(Category.findById(categoryId))
      .stream()
      .map(mapperDto)
      .collect(toList());
  }

  public void saveStudent(@Valid StudentDTO student) throws ConstraintViolationException {
    Student st = mapper.apply(student);
    st.id = null;
    this.studentRepository.persist(st);
    student.setId(st.id);
  }
}
