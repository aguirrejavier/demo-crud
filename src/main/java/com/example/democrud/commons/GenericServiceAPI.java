package com.example.democrud.commons;

import java.io.Serializable;
import java.util.List;


public interface GenericServiceAPI<T, ID extends Serializable> {
	
	T guardar(T entity);
	
	void eliminar(ID id);
	
	T obtener(ID id);
	
	List<T> obtenerTodos();

}
