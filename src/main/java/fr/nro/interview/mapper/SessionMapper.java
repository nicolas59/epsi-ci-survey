package fr.nro.interview.mapper;

import static java.util.stream.Collectors.toList;

import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;

import fr.nro.interview.dto.Identifier;
import fr.nro.interview.dto.session.SessionDTO;
import fr.nro.interview.entity.Student;
import fr.nro.interview.entity.session.Session;
import fr.nro.interview.entity.session.StudentContext;

@ApplicationScoped
public class SessionMapper implements Function<Session, SessionDTO> {

  @Override
  public SessionDTO apply(Session t) {
    SessionDTO sessionDto = new SessionDTO();
    sessionDto.setId(t.id);
    sessionDto.setSurveyId(new Identifier<>(t.getSurvey().id));
    sessionDto.setDuration(t.getDuration());
    sessionDto.setStartDate(t.getStartDate());
    
    sessionDto.setStudentIds(t.getStudentContexts().stream()
        .map(StudentContext::getStudent)
        .map(Student::getId)
        .map(Identifier::new)
        .collect(toList()));
    return sessionDto;
  }

}
