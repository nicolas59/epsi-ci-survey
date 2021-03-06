package fr.nro.interview.service;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.NotFoundException;

import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nro.interview.dto.Identifier;
import fr.nro.interview.dto.session.AnswerDTO;
import fr.nro.interview.dto.session.ContextDTO;
import fr.nro.interview.dto.session.ExamDTO;
import fr.nro.interview.dto.session.ExamDTO.Status;
import fr.nro.interview.dto.session.SessionDTO;
import fr.nro.interview.entity.Category;
import fr.nro.interview.entity.Student;
import fr.nro.interview.entity.interview.Question;
import fr.nro.interview.entity.interview.Survey;
import fr.nro.interview.entity.session.Session;
import fr.nro.interview.entity.session.SessionCtxQuestion;
import fr.nro.interview.entity.session.StudentContext;
import fr.nro.interview.mapper.QuestionMapper;
import fr.nro.interview.mapper.SessionMapper;
import fr.nro.interview.mapper.StudentMapper;
import fr.nro.interview.repository.CategoryRepository;
import fr.nro.interview.repository.SessionRepository;
import fr.nro.interview.repository.StudentContextRepository;
import fr.nro.interview.repository.StudentRepository;
import fr.nro.interview.repository.SurveyRepository;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

@ApplicationScoped
@Transactional
public class SessionService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SessionService.class);

  @Inject
  SurveyRepository surveyRepository;

  @Inject
  StudentRepository studentRepository;

  @Inject
  SessionRepository sessionRepository;

  @Inject
  CategoryRepository categoryRepository;

  @Inject
  StudentContextRepository studentCtxRepository;

  @Inject
  EntityManager em;

  @Inject
  QuestionMapper questionMapper;

  @Inject
  SessionMapper sessionMapper;

  @Inject
  StudentMapper studentMapper;

  @Inject
  Mailer mailer;

  String emailBody;

  @ConfigProperty(name = "interview.url", defaultValue = "http://localhost:4200/exam/@sessionId@/uuid/@uuid@")
  String url;

  @ConfigProperty(name = "interview.template", defaultValue = "file:///configuration/epreuve.html")
  String templateEmail;

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

  @PostConstruct
  public void init() throws IOException, URISyntaxException {
    URI url = null;
    if (templateEmail.startsWith("classpath:")) {
      url = SessionService.class.getResource(templateEmail.substring("classpath:".length()))
        .toURI();
    } else {
      url = URI.create(this.templateEmail);
    }

    this.emailBody = Files.readAllLines(Paths.get(url))
      .stream()
      .collect(Collectors.joining("\n"));
  }

  public void createSession(@Valid SessionDTO sessionDto) {
    final Session session = new Session();
    session.setCreatedDate(LocalDate.now());
    session.setDuration(sessionDto.getDuration());
    session.setStatus(Session.Status.CREATING);
    session.setName(sessionDto.getName());

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
  public void addCategory(@NotNull Long sessionId, @NotNull Long categoryId) {
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

  public void startSession(Long sessionId) {
    LOGGER.debug("Prepare session ...");
    Session session = this.sessionRepository.findById(sessionId);

    if (!Session.Status.CREATING.equals(session.getStatus())) {
      throw new RuntimeException("Session already started...");
    }

    List<Question> questions = session.getSurvey()
      .getQuestions();
    for (StudentContext ctx : session.getStudentContexts()) {
      Set<SessionCtxQuestion> set = new HashSet<>();
      for (int index = 0; index < questions.size(); index++) {
        Question question = questions.get(index);
        SessionCtxQuestion ctxQues = new SessionCtxQuestion(ctx, question, index);
        set.add(ctxQues);
      }
      ctx.setSessionCtxQuestion(set);
    }

    this.sessionRepository.persist(session);

  }

  public void answer(AnswerDTO answerDTO) {
    StudentContext stCtx = this.studentCtxRepository.findByUuid(answerDTO.getUuid());
    if (stCtx == null) {
      throw new NotFoundException("Context not found");
    }

    Question question = Question.findById(answerDTO.getQuestionId()
      .getId());

    SessionCtxQuestion sessionCtxQ = stCtx.getSessionCtxQuestion()
      .stream()
      .filter(item -> Objects.equals(item.getSessionCtxAsso()
        .getQuestionId(),
          answerDTO.getQuestionId()
            .getId()))
      .findFirst()
      .orElseGet(() -> {
        SessionCtxQuestion newCtxQuestion = new SessionCtxQuestion();
        newCtxQuestion.setQuestion(question);
        newCtxQuestion.setStudentContext(stCtx);
        newCtxQuestion.setPosition(stCtx.getSessionCtxQuestion()
          .size());
        return newCtxQuestion;
      });

    // TODO Verify session expiration.

    if (sessionCtxQ.getAnswer() == null) {
      sessionCtxQ.setAnswer(answerDTO.getAnswer());
    } else {
      throw new ConstraintViolationException("Answer already presents.", null);
    }
    this.em.persist(sessionCtxQ);

    stCtx.setLastQuestionAnswered(question);

    // verification si dernière question
    Long count = this.em.createQuery("select count(scq) from SessionCtxQuestion scq " + "where answer is not null and studentContext=:studentContext", Long.class)
      .setParameter("studentContext", stCtx)
      .getSingleResult();

    if (count == stCtx.getSession()
      .getSurvey()
      .getQuestions()
      .size()) {
      // derniere question
      stCtx.setEndDate(Calendar.getInstance());
      this.em.persist(stCtx);
    }
  }

  public ExamDTO findExam(Long sessionId, String uuid) {
    StudentContext stCtx = this.studentCtxRepository.findByUuid(uuid);
    if (stCtx == null) {
      throw new NotFoundException("Context not found");
    }

    ExamDTO examDTO = new ExamDTO();
    if (stCtx.getEndDate() != null) {
      examDTO.setStatus(Status.END);
    } else {
      if (stCtx.getStartedDate() == null) {
        stCtx.setStartedDate(Calendar.getInstance());
        if (stCtx.getSession()
          .getDuration() != null) {
          Calendar now = Calendar.getInstance();
          now.add(Calendar.MINUTE, stCtx.getSession()
            .getDuration() + 1);
          stCtx.setDeathLine(now);
        }
        this.studentCtxRepository.persist(stCtx);
      }

      long remain = ChronoUnit.MINUTES.between(LocalDateTime.now()
        .atZone(ZoneId.systemDefault()),
          stCtx.getDeathLine()
            .toInstant()
            .atZone(ZoneId.systemDefault()));

      if (remain <= 0) {
        examDTO.setStatus(Status.EXPIRED);
      } else {
        examDTO.setRemainingTimeInMinutes(remain > 0 ? remain : 0);
        examDTO.setStatus(Status.PENDING);
        examDTO.setQuestions(stCtx.getSession()
          .getSurvey()
          .getQuestions()
          .stream()
          .map(questionMapper)
          .collect(toList()));

        // find last question
        if (stCtx.getLastQuestionAnswered() != null) {
          examDTO.setLastQuestionAnswered(new Identifier<>(stCtx.getLastQuestionAnswered().id));
        }
      }

    }

    return examDTO;
  }

  public List<SessionDTO> findAll() {
    return this.sessionRepository.findAll()
      .stream()
      .map(this.sessionMapper)
      .collect(toList());
  }

  public SessionDTO findById(Long sessionId) {
    return this.sessionMapper.apply(this.sessionRepository.findById(sessionId));
  }

  public boolean isEquals(String item1, String item2) {
    if (item1 == null || item2 == null) {
      return false;
    }

    String[] item1Split = (item1 == null?"":item1).split(",");
    Arrays.sort(item1Split);

    String[] item2Split = (item2 == null?"":item2).split(",");
    Arrays.sort(item2Split);

    return Arrays.equals(item1Split, item2Split);
  }

  public ContextDTO findContext(Long sessionId, Long studentId) {

    StudentContext ctx = this.studentCtxRepository.findBySessionAndStudent(sessionId, studentId);
    if (ctx == null) {
      throw new NotFoundException("Contexte inexitant");
    }

    final ContextDTO context = new ContextDTO();
    context.setUuid(ctx.getUuid());
    context.setStudent(this.studentMapper.apply(ctx.getStudent()));
    if(ctx.getStartedDate() != null) {
    //  context.setStartedDate(LocalDateTime.ofInstant(ctx.getStartedDate().toInstant(), ZoneId.systemDefault()));
    }
    
    if(ctx.getStartedDate() != null) {
      context.setStartedDate(ctx.getStartedDate().getTime());
    }
    
    if(ctx.getEndDate() != null) {
      context.setEndDate(LocalDateTime.ofInstant(ctx.getEndDate().toInstant(), ZoneId.systemDefault()));
    }
    
    if(ctx.getDeathLine() != null) {
      context.setDeathLine(LocalDateTime.ofInstant(ctx.getDeathLine().toInstant(), ZoneId.systemDefault()));
    }
    
    

    if (ctx.getSessionCtxQuestion() != null) {
      context.setScore(ctx.getSessionCtxQuestion()
        .stream()
        .filter(item -> this.isEquals(item.getQuestion()
          .getResponse(), item.getAnswer()))
        .map(item -> 1)
        .reduce(Integer::sum)
        .orElse(0));
    } else {
      context.setScore(0);
    }

    return context;
  }

  public void sendEmail(Long sessionId) {
    Session session = this.sessionRepository.findById(sessionId);
    if (session == null) {
      throw new NotFoundException("Session inexitant");
    }
    List<Mail> emails = session.getStudentContexts()
      .stream()
      .map(this::generateEmail)
      .collect(toList());
    this.mailer.send(emails.toArray(new Mail[0]));
  }

  public void sendEmail(Long sessionId, Long studentId) {
    StudentContext ctx = this.studentCtxRepository.findBySessionAndStudent(sessionId, studentId);
    if (ctx == null) {
      throw new NotFoundException("Contexte inexitant");
    }
    LOGGER.debug("Send email to {}", ctx.getStudent()
      .getEmail());
    mailer.send(generateEmail(ctx));
  }

  private Mail generateEmail(StudentContext ctx) {
    String html = this.emailBody.replaceAll("%url%", this.url.replaceAll("@sessionId@", String.valueOf(ctx.getSession().id))
      .replaceAll("@uuid@", String.valueOf(ctx.getUuid())));
    return Mail.withHtml(ctx.getStudent()
      .getEmail(), "Acces au QCM", html);
  }

}
