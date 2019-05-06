package fr.nro.interview.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.apache.commons.collections4.CollectionUtils;

import fr.nro.interview.dto.Identifier;
import fr.nro.interview.dto.session.SessionDTO;
import fr.nro.interview.entity.Category;
import fr.nro.interview.entity.Student;
import fr.nro.interview.entity.interview.Survey;
import fr.nro.interview.entity.session.Session;
import fr.nro.interview.entity.session.StudentContext;
import fr.nro.interview.repository.CategoryRepository;
import fr.nro.interview.repository.SessionRepository;
import fr.nro.interview.repository.StudentRepository;
import fr.nro.interview.repository.SurveyRepository;

@ApplicationScoped
public class SessionService {
  
  @Inject
  SurveyRepository surveyRepository;
 
  @Inject
  StudentRepository studentRepository;
 
  @Inject
  SessionRepository sessionRepository;

  @Inject
  CategoryRepository categoryRepository;

  public SessionService() {
    super();
  }

  public SessionService(SurveyRepository surveyRepository, StudentRepository studentRepository, SessionRepository sessionRepository, CategoryRepository categoryRepository) {
    super();
    this.surveyRepository = surveyRepository;
    this.studentRepository = studentRepository;
    this.sessionRepository = sessionRepository;
    this.categoryRepository = categoryRepository;
  }

  public void createSession(@Valid SessionDTO sessionDto) {
    final Session session = new Session();
    session.setCreatedDate(LocalDate.now());
    session.setDuration(sessionDto.getDuration());

    final Survey survey = this.surveyRepository.findById(sessionDto.getSurveyId()
      .getId());
    if (survey == null) {
      throw new NotFoundException("Survey not found");
    }
    session.setSurvey(survey);

    if (sessionDto.getStudentIds() != null) {
      List<Optional<Student>> students = sessionDto.getStudentIds()
        .stream()
        .map(Identifier::getId)
        .map(studentId -> Optional.ofNullable(this.studentRepository.findById(studentId)))
        .collect(toList());

      if (!students.stream()
        .allMatch(Optional::isPresent)) {
        throw new NotFoundException("One or more students not found");
      }

      session.setStudentContexts(students.stream()
        .map(Optional::get)
        .map(student -> new StudentContext(student, session))
        .collect(toList()));
    }

    this.sessionRepository.persist(session);
    sessionDto.setId(session.id);
  }

  /**
   * Permet d'ajouter les étudiants lié à une classe à une session.
   * 
   * 
   * @param sessionId
   * @param gradeId
   */
  public void addCategory(Long sessionId, Long categoryId) {
    Session session = this.sessionRepository.findById(sessionId);
    if (session == null) {
      throw new NotFoundException("Survey not found");
    }

    Category category = this.categoryRepository.findById(categoryId);
    if (category == null) {
      throw new NotFoundException("category not found");
    }

    List<Student> students = this.studentRepository.findByCategory(category);

    List<Student> registeredStudents = CollectionUtils.emptyIfNull(session.getStudentContexts())
      .stream()
      .map(item -> item.getStudent())
      .collect(toList());

    students.removeAll(registeredStudents);

    List<StudentContext> newStudentContexts = students.stream()
      .map(student -> new StudentContext(student, session))
      .collect(toList());

    Collection<StudentContext> studentContexts = CollectionUtils.emptyIfNull(session.getStudentContexts());
    studentContexts.addAll(newStudentContexts);
    session.setStudentContexts(new ArrayList<>(studentContexts));
    this.sessionRepository.persist(session);

  }
}
