package fr.nro.interview.repository;

import javax.enterprise.context.ApplicationScoped;

import fr.nro.interview.entity.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student>{

}
