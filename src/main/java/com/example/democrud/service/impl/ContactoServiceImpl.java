package com.example.democrud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.example.democrud.commons.GenericServiceImpl;
import com.example.democrud.dao.api.ContactoDaoAPI;
import com.example.democrud.model.Contacto;
import com.example.democrud.service.api.ContactoServiceAPI;

@Service
public class ContactoServiceImpl extends GenericServiceImpl<Contacto, Long> implements ContactoServiceAPI {
	
	@Autowired
	private ContactoDaoAPI personaDaoApi;
	
	@Override
	public CrudRepository<Contacto, Long> obtenerDao() {
		return personaDaoApi;
	}

}
