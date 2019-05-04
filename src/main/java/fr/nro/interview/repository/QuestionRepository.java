package fr.nro.interview.repository;

import javax.enterprise.context.ApplicationScoped;

import fr.nro.interview.entity.interview.Question;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class QuestionRepository implements PanacheRepository<Question>{

}
