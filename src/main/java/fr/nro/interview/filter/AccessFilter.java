package fr.nro.interview.filter;

import java.io.IOException;
import java.util.Collections;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class AccessFilter implements ContainerRequestFilter {

  
  private static final Logger LOGGER = LoggerFactory.getLogger(AccessFilter.class);
  
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    LOGGER.info(">> Acces à l'uri :  {}", requestContext.getUriInfo().getPath());

    if(requestContext.getHeaders()!=null){
     requestContext.getHeaders().forEach((name, values) -> {
       LOGGER.info(name);
       values.stream().forEach(LOGGER::info);
     });
    }
  }

}
