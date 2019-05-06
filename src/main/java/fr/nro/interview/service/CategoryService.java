package fr.nro.interview.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
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

  private Function<CategoryDTO, Category> mapper = (categoryDTO) -> {
    Category cat = new Category();
    cat.id = categoryDTO.getId();
    cat.setName(categoryDTO.getName());
    return cat;
  };

  private Function<Category, CategoryDTO> mapperDto = (category) -> {
    if (category == null) {
      return null;
    }
    CategoryDTO cat = new CategoryDTO();
    cat.setId(category.id);
    cat.setName(category.getName());
    return cat;
  };

  public void save(@Valid CategoryDTO categoryDTO) throws ConstraintViolationException {
    Category cat = mapper.apply(categoryDTO);
    cat.id = null;
    this.categoryRepository.persist(cat);
    categoryDTO.setId(cat.id);
  }

  public List<CategoryDTO> findAll() {
    return this.categoryRepository.findAll()
      .stream()
      .map(mapperDto)
      .collect(toList());
  }

  public Optional<CategoryDTO> findById(Long id) {
    return Optional.ofNullable(this.mapperDto.apply(this.categoryRepository.findById(id)));
  }

}
