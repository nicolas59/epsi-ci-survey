package fr.nro.interview.repository;

import javax.enterprise.context.ApplicationScoped;

import fr.nro.interview.entity.interview.Survey;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SurveyRepository implements PanacheRepository<Survey> {

}
