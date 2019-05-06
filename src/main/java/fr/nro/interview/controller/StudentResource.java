package fr.nro.interview.controller;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
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
import fr.nro.interview.dto.StudentDTO;
import fr.nro.interview.service.StudentService;

@Path("/student")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class StudentResource {

  @Inject
  StudentService studentService;

  @Inject
  Validator validator;

  @GET
  @Path("{id}")
  public StudentDTO findStudent(@PathParam("id") Long id) {
    return this.studentService.findById(id)
      .orElseThrow(() -> new NotFoundException("Student not found"));
  }

  @GET
  public List<StudentDTO> findAll() {
    return this.studentService.findAll();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response save(StudentDTO student) {
    try {
      this.studentService.saveStudent(student);
      return Response.created(UriBuilder.fromUri("/student/{id}")
        .build(student.getId()))
        .build();
    } catch (ConstraintViolationException e) {
      return Response.status(422)
        .entity(ErrorDTO.create(e.getConstraintViolations()))
        .build();
    }
  }
}
