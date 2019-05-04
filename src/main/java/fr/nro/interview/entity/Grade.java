package fr.nro.interview.entity;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Grade extends PanacheEntity{

  private String name;

}
