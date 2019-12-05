package com.example.democrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.democrud.model.Contacto;
import com.example.democrud.service.api.ContactoServiceAPI;

@Controller
public class AppController {

	@Autowired
	private ContactoServiceAPI contactoServiceAPI;
	
	@GetMapping({"/","/login"})
	public String index(Model model){
		return "index";
	}

	@RequestMapping("/contactos")
	public String contactos(Model model) {
		model.addAttribute("list", contactoServiceAPI.obtenerTodos());
		return "contactos";
	}

	@GetMapping("/save/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {
		if (id != null && id != 0) {
			model.addAttribute("contacto", contactoServiceAPI.obtener(id));
			model.addAttribute("accion", "Editar Contacto");
		}else {
			model.addAttribute("accion", "Agregar Contacto");
			model.addAttribute("contacto", new Contacto());
		}
		return "save";
	}

	@PostMapping("/save")
	public String save(Contacto persona, Model model) {
		contactoServiceAPI.guardar(persona);
		return "redirect:/contactos";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		contactoServiceAPI.eliminar(id);
		return "redirect:/contactos";
	}
}
