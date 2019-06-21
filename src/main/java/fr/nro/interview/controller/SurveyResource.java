package fr.nro.interview.controller;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import fr.nro.interview.dto.ErrorDTO;
import fr.nro.interview.dto.interview.QuestionDTO;
import fr.nro.interview.dto.interview.SurveyDTO;
import fr.nro.interview.service.SurveyService;

@Transactional
@Path("/survey")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("admin")
public class SurveyResource {

  @Inject
  SurveyService interviewService;

  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  public Response findAll() {
    return Response.ok(this.interviewService.findAll())
      .build();
  }

  @GET
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response findSurvey(@PathParam("id") @NotNull Long id) {
    SurveyDTO surveyDto = this.interviewService.findById(id);
    if (surveyDto == null) {
      throw new NotFoundException();
    }
    return Response.ok(surveyDto)
      .build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response save(SurveyDTO interviewDTO) {
    try {
      this.interviewService.save(interviewDTO);
      return Response.created(UriBuilder.fromUri("/interview/{id}")
        .build(interviewDTO.getId()))
        .build();
    } catch (ConstraintViolationException e) {
      return Response.status(422)
        .entity(ErrorDTO.create(e.getConstraintViolations()))
        .build();
    }
  }

  @POST
  @Path("{id}/question")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addQuestion(@NotNull @PathParam("id") Long identifier, QuestionDTO questionDTO) {
    try {
      this.interviewService.addQuestion(identifier, questionDTO);
      return Response.created(UriBuilder.fromUri("/interview/{id}/question/{questionId}")
        .build(identifier, questionDTO.getId()))
        .build();
    } catch (ConstraintViolationException e) {
      return Response.status(422)
        .entity(ErrorDTO.create(e.getConstraintViolations()))
        .build();
    }
  }

  @GET
  @Path("{id}/question/{questionId}")
  public Response getQuestion(@NotNull @PathParam("id") Long identifier, @NotNull @PathParam("questionId") Long questionId) {
    return Response.ok(this.interviewService.getQuestion(identifier, questionId))
      .build();
  }

}