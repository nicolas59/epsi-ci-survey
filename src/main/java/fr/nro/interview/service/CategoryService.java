package fr.nro.interview.service;

import java.util.function.Function;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import fr.nro.interview.dto.CategoryDTO;
import fr.nro.interview.entity.Category;
import fr.nro.interview.repository.CategoryRepository;

@ApplicationScoped
public class CategoryService {

  @Inject
  CategoryRepository categoryRepository;

  private Function<CategoryDTO, Category> mapper = (categoryDTO) ->{
    Category cat = new Category();
    cat.id = categoryDTO.getId();
    cat.setName(categoryDTO.getName());
    return cat;
  };
  
  public void save(@Valid CategoryDTO categoryDTO) throws ConstraintViolationException {
    Category cat = mapper.apply(categoryDTO);
    cat.id = null;
    this.categoryRepository.persist(cat);
    categoryDTO.setId(cat.id);
  }
  
}
