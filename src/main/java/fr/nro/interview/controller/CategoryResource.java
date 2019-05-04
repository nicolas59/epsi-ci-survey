package fr.nro.interview.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import fr.nro.interview.dto.CategoryDTO;
import fr.nro.interview.dto.ErrorDTO;
import fr.nro.interview.entity.Category;
import fr.nro.interview.service.CategoryService;

@Transactional
@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

  @Inject
  CategoryService categoryService;
  
  @GET
  public List<Category> findAll() {
    return Category.findAll()
      .<Category>stream()
      .map(c -> new Category(c.id, c.getName()))
      .collect(Collectors.toList());
  }
  
  @GET
  @Path("{id}")
  public Category findById(@PathParam("id") Long id) {
    return Category.findById(id);
  }
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response save(CategoryDTO category) {
    try {
      this.categoryService.save(category);
      return Response.created(UriBuilder.fromUri("/category/{id}")
        .build(category.getId()))
        .build();
    }catch(ConstraintViolationException e) {
      return Response.status(422).entity(ErrorDTO.create(e.getConstraintViolations())).build();
    }
  }

}
