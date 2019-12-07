package com.example.democrud.dao.api;



import org.springframework.data.repository.CrudRepository;

import com.example.democrud.model.Usuario;

public interface UsuarioDaoAPI extends CrudRepository<Usuario, String> {
}
