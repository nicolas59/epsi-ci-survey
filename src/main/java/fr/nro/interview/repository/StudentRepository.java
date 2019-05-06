package fr.nro.interview.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import fr.nro.interview.entity.Category;
import fr.nro.interview.entity.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student>{

  public List<Student> findByCategory(Category category){
    return Student.list("category", category);
  }
  
}
