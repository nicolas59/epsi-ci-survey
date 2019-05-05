package fr.nro.interview.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import fr.nro.interview.dto.Identifier;
import fr.nro.interview.dto.session.SessionDTO;
import fr.nro.interview.entity.Student;
import fr.nro.interview.entity.interview.Survey;
import fr.nro.interview.entity.session.Session;
import fr.nro.interview.entity.session.StudentContext;
import fr.nro.interview.repository.SessionRepository;
import fr.nro.interview.repository.StudentRepository;
import fr.nro.interview.repository.SurveyRepository;

@Singleton
public class SessionService {

  private SurveyRepository surveyRepository;

  private StudentRepository studentRepository;
  
  private SessionRepository sessionRepository;

  @Inject
  public SessionService(SurveyRepository surveyRepository, StudentRepository studentRepository, SessionRepository sessionRepository) {
    super();
    this.surveyRepository = surveyRepository;
    this.studentRepository = studentRepository;
    this.sessionRepository = sessionRepository;
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

      if(!students.stream().allMatch(Optional::isPresent)) {
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
}
