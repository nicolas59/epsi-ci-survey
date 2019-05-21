package fr.nro.interview.interceptor;

import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

public class BuildInterceptor implements WriterInterceptor {
 
  @Override
  public void aroundWriteTo(WriterInterceptorContext context) {
    context.getHeaders().add("running-with", "Quarkus.io");
  }
}
