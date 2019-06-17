package fr.nro.interview.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.nro.interview.entity.session.StudentContext;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class StudentContextRepository implements PanacheRepository<StudentContext> {

  @Inject
  EntityManager em;

  public StudentContext findByUuid(String uuid) {
    return (StudentContext) em.createQuery("from StudentContext where uuid=:uuid")
      .setParameter("uuid", uuid)
      .getSingleResult();
  }
  
  
  public StudentContext findBySessionAndStudent(Long sessionId, Long studentId) {
    return (StudentContext) em.createQuery("from StudentContext sc where sc.session.id=:sessionId and sc.student.id=:studentId")
      .setParameter("sessionId", sessionId)
      .setParameter("studentId", studentId)
      .getSingleResult();
  }

}
