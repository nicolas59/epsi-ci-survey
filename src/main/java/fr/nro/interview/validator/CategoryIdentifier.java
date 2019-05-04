package fr.nro.interview.validator;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryExistValidator.class)
@Documented
public @interface CategoryIdentifier {
  
  String message() default "Category do not exist.";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
