package com.example.democrud.commons;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
@Service
public abstract class GenericServiceImpl<T,ID extends Serializable> implements GenericServiceAPI<T, ID> {

	@Override
	public T guardar(T entity) {
		return obtenerDao().save(entity);
	}

	@Override
	public void eliminar(ID id) {
		obtenerDao().deleteById(id);
	}

	@Override
	public T obtener(ID id) {
		Optional<T> obj = obtenerDao().findById(id);
		if(obj.isPresent())
			return obj.get();
		return null;
	}

	@Override
	public List<T> obtenerTodos() {
		List<T> lista = new ArrayList<>();
		obtenerDao().findAll().forEach(obj -> lista.add(obj));
		return lista;
	}
	
	public abstract CrudRepository<T, ID> obtenerDao();
}
