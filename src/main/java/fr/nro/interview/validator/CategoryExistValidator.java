package fr.nro.interview.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import fr.nro.interview.dto.Identifier;
import fr.nro.interview.entity.Category;

public class CategoryExistValidator implements ConstraintValidator<CategoryIdentifier, Identifier<Long>>{

  @Override
  public boolean isValid(Identifier<Long> value, ConstraintValidatorContext context) {
    return Category.findById(value.getId()) != null;
  }
}
