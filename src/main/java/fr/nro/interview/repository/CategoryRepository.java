package fr.nro.interview.repository;

import javax.enterprise.context.ApplicationScoped;

import fr.nro.interview.entity.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category>{

}
