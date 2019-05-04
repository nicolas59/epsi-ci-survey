package fr.nro.interview;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

public class ContraintViolationUtils {

  public static <T> ConstraintViolation<T> createConstraint(String path, String message, Class<T> rootBeanClass){
    return ConstraintViolationImpl.forParameterValidation(message, 
        null, 
        null, 
        null, 
        rootBeanClass, 
        null, 
        null, 
        null, 
        PathImpl.createPathFromString(path), 
        null, 
        null,
        null);
  }
}
