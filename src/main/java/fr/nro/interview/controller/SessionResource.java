package fr.nro.interview.controller;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import fr.nro.interview.dto.session.AnswerDTO;
import fr.nro.interview.dto.session.SessionDTO;
import fr.nro.interview.service.SessionService;

@Path("/session")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class SessionResource {

  SessionService sessionService;

  public SessionResource() {

  }

  @Inject
  public SessionResource(SessionService sessionService) {
    super();
    this.sessionService = sessionService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response save(SessionDTO session) {
    this.sessionService.createSession(session);
    return Response.created(UriBuilder.fromPath("/session/{id}")
      .build(session.getId()))
      .build();
  }

  @POST
  @Path("/{id}/start")
  public Response startSession(@PathParam("id") Long sessionId) {
    this.sessionService.startSession(sessionId);
    return Response.ok()
      .build();
  }

  /**
   * Permet d'ajouter une classe à un sondage
   * 
   * @param session
   * @return
   */
  @POST
  @Path("/{sessionId}/category/{categoryId}")
  @Consumes(MediaType.APPLICATION_JSON)
  public void addGrade(@PathParam("sessionId") Long sessionId, @PathParam("categoryId") Long categoryId) {
    this.sessionService.addCategory(sessionId, categoryId);
  }

  @POST
  @Path("/{sessionId}/answer")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response postAnswer(@PathParam("sessionId") Long sessionId, AnswerDTO answer) {
    this.sessionService.answer(answer);
    return Response.ok()
      .build();
  }

  @GET
  @Path("/{sessionId}/uuid/{uuid}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response getSession(@NotNull @PathParam("sessionId") Long sessionId, 
      @NotBlank @PathParam("uuid") String uuid) {
    return Response.ok(this.sessionService.findExam(sessionId, uuid))
      .build();
  }

}
