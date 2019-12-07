package com.example.democrud.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.democrud.commons.GenericServiceImpl;
import com.example.democrud.dao.api.UsuarioDaoAPI;
import com.example.democrud.model.Usuario;
import com.example.democrud.service.api.UsuarioServiceAPI;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, String> implements UsuarioServiceAPI, UserDetailsService{
	
	@Autowired
	private UsuarioDaoAPI usuarioDaoAPI;

	@Override
	public CrudRepository<Usuario, String> obtenerDao() {
		return usuarioDaoAPI;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Usuario> us = usuarioDaoAPI.findById(username);
		if(!us.isPresent())
			new UsernameNotFoundException("username");
		
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ADMIN"));
		
		UserDetails userDet = new User(us.get().getUsuario(),us.get().getPassword(),roles);
		
		return userDet;
	}
	
	
}
