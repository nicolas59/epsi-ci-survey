package fr.nro.interview.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessFilter implements ContainerRequestFilter {

  
  private static final Logger LOGGER = LoggerFactory.getLogger(AccessFilter.class);
  
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    LOGGER.info("Acces à l'uri :  {}", requestContext.getUriInfo().getPath());
  }

}
