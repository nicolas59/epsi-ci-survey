package fr.nro.interview.repository;

import javax.enterprise.context.ApplicationScoped;

import fr.nro.interview.entity.session.Session;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SessionRepository implements PanacheRepository<Session>{

}
