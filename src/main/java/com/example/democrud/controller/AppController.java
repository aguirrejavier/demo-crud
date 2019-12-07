package com.example.democrud.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.democrud.model.Contacto;
import com.example.democrud.model.Usuario;
import com.example.democrud.service.api.ContactoServiceAPI;
import com.example.democrud.service.api.UsuarioServiceAPI;

@Controller
public class AppController {

	@Autowired
	private ContactoServiceAPI contactoServiceAPI;
	@Autowired
	private UsuarioServiceAPI usuarioServiceAPI;
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@GetMapping({ "/", "/login" })
	public String mostrarIndex(Model model) {
		return "index";
	}

	@GetMapping("/registrarse")
	public String mostrarRegistrar(Model model) {
		model.addAttribute("usuario", new Usuario()); // instancia un nuevo usuario para registrarse
		model.addAttribute("msgRegistro", "");
		return "registrarse";
	}

	@PostMapping("/registraruser")
	public String showRegistro(Usuario user, Model model) {
		if (!user.getUsuario().isEmpty() && !user.getPassword().isEmpty()) {
			if (!usuarioServiceAPI.existe(user.getUsuario())) {
				user.setPassword(bcrypt.encode(user.getPassword()));
				usuarioServiceAPI.guardar(user);
//				model.addAttribute("registroUser", "Usuario registrado correctamente");

				return "index";
			} else {
				model.addAttribute("msgRegistro", "Usuario ya existente");
			}
		} else
			model.addAttribute("msgRegistro", "User y password no pueden ser vacios");
		return "registrarse";
	}

	@RequestMapping("/contactos")
	public String contactos(Model model) {
		List<Contacto> listaFiltrada = new ArrayList<Contacto>();
		String user = getAuthentication().getName();

		for (Contacto contacto : contactoServiceAPI.obtenerTodos()) {
			if (contacto.getUsuario().equals(user)) {
				listaFiltrada.add(contacto);
			}
		}
		model.addAttribute("list", listaFiltrada);
		return "contactos";
	}

	@GetMapping("/save/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {
		
		String userlog = getAuthentication().getName(); 
		
		if (id != null && id != 0 ) {
			
			if(!userlog.equals(contactoServiceAPI.obtener(id).getUsuario())) {
				return "redirect:/contactos";
			}
			model.addAttribute("contacto", contactoServiceAPI.obtener(id));
			model.addAttribute("accion", "Editar Contacto");
		} else {
			model.addAttribute("accion", "Agregar Contacto");
			model.addAttribute("contacto", new Contacto());
		}
		return "save";
	}

	@PostMapping("/save")
	public String save(Contacto persona, Model model) {
		persona.setUsuario(getAuthentication().getName());
		contactoServiceAPI.guardar(persona);
		return "redirect:/contactos";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		contactoServiceAPI.eliminar(id);
		return "redirect:/contactos";
	}

	@GetMapping("/cerrarSesion")
	public String cerrarSesion(Model model) {
		getAuthentication().setAuthenticated(false); // borrar el logueo del usuario :o
		model.addAttribute("logout", true);
		return "index";
	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
