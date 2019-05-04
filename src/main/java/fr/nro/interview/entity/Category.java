package fr.nro.interview.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Category  extends PanacheEntity{

  private String name;
  
  
  public Category(String name) {
    super();
    this.name = name;
  }
  
  public Category(Long id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public Category() {
    super();
  }

  @OneToMany(mappedBy="category")
  private Collection<Student> students;

  public Collection<Student> getStudents() {
    return students;
  }

  public void setStudents(Collection<Student> students) {
    this.students = students;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  
}
