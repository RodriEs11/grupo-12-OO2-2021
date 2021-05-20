package com.unla.grupo12.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupo12.entity.Perfil;
import com.unla.grupo12.entity.Usuario;
import com.unla.grupo12.helpers.ViewRouteHelper;
import com.unla.grupo12.model.UsuarioModel;
import com.unla.grupo12.service.IPerfilService;
import com.unla.grupo12.service.IUsuarioService;



@Controller
@RequestMapping("usuarios")
public class UsuariosController {
	
	
	@Autowired
	@Qualifier("usuarioService")
	private IUsuarioService usuarioService;

	@Autowired
	@Qualifier("perfilService")
	private IPerfilService perfilService;

	
	
	/*  EN LA PESTAÑA USUARIOS SE MUESTRAN EN UNA TABLA 
	 *  TODOS LOS USUARIOS Y PERFILES QUE HAY EN LA BASE DE DATOS
	 *  
	 */
	
	@GetMapping("")
	public ModelAndView index() {
		
		ModelAndView modelo = new ModelAndView(ViewRouteHelper.USUARIOS);
		
		List<Usuario> usuarios = usuarioService.getAll();
		List<Perfil> perfiles = perfilService.getAll();
		
		modelo.addObject("usuarios", usuarios);
		modelo.addObject("perfiles", perfiles);
		
		
		
		return modelo;

	}
	
	
	
	
	/* PESTAÑA AGREGAR USUARIO A BD
	 * SE TRAEN, A TRAVES DEL SERVICIO DE PERFIL, TODOS LOS PERFILES CARGADOS EN LA BD,
	 * SE AGREGAN AL ModelAndView PARA MOSTRARLOS EN UNA LISTA DESPLEGABLE
	 * 
	 * 
	 */
	
	@GetMapping("agregar")
	public ModelAndView agregar() {

		ModelAndView modelo = new ModelAndView(ViewRouteHelper.USUARIOS_AGREGAR);
		
		List<Perfil> perfiles = perfilService.getAll();
		
		modelo.addObject("perfiles", perfiles);
		modelo.addObject("usuario", new Usuario());
		
		return modelo;

	}

	
	
	/* UNA VEZ REALIZADA LA SOLICITUD POST
	 * SE AGREGA EL UsuarioModel A LA BD
	 * 
	 */
	
	@PostMapping("agregar")
	public ModelAndView agregarUsuarioABd(@ModelAttribute("usuario") UsuarioModel usuarioModel) {
		
		ModelAndView modelo = new ModelAndView(ViewRouteHelper.USUARIOS_AGREGAR);
		
		
		usuarioService.agregarOActualizar(usuarioModel);
		
		
		return modelo;
	}
	

}