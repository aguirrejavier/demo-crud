package com.example.democrud.dao.api;

import org.springframework.data.repository.CrudRepository;

import com.example.democrud.model.Contacto;

public interface ContactoDaoAPI extends CrudRepository<Contacto, Long> {
}
