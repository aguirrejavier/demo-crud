package com.example.democrud;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.democrud.dao.api.UsuarioDaoAPI;
import com.example.democrud.model.Usuario;

@SpringBootTest
class DemoCrudApplicationTests {

	@Autowired
	private UsuarioDaoAPI userDao;
	@Autowired
	private BCryptPasswordEncoder ps;
	@Test
	void contextLoads() {
		
		Usuario nuevo = new Usuario();
		nuevo.setUsuario("admin2");
		nuevo.setPassword(ps.encode("123"));
		Usuario usuarioAlmacenado = userDao.save(nuevo);
		assertEquals(nuevo.getUsuario(), usuarioAlmacenado.getUsuario());
	}

}
