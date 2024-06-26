package com.unla.grupo12.controller;

import com.unla.grupo12.entity.Permiso;
import com.unla.grupo12.helpers.ViewRouteHelper;
import com.unla.grupo12.model.UsuarioModel;
import com.unla.grupo12.model.PerfilModel;
import com.unla.grupo12.model.PermisoModel;
import com.unla.grupo12.model.PersonaModel;
import com.unla.grupo12.model.UsuarioModel;
import com.unla.grupo12.service.IPerfilService;
import com.unla.grupo12.service.IPermisoService;
import com.unla.grupo12.service.IPersonaService;
import com.unla.grupo12.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

//REALIZA UN MAPEO HACIA localhost:8080/

@Controller
@RequestMapping("")

public class HomeController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	@Qualifier("personaService")
	private IPersonaService personaService;

	@Autowired
	@Qualifier("permisoService")
	private IPermisoService permisoService;

	@PreAuthorize("hasAnyAuthority('Admin', 'Auditoria')")
	@GetMapping("/home")
	public String index() {

		return ViewRouteHelper.INDEX;

	}

	@GetMapping("/login")
	public String login(Model model, @RequestParam(name = "error", required = false) String error,
											@RequestParam(name = "logout", required = false) String logout) {

		model.addAttribute("error", error);
		model.addAttribute("logout", logout);

		return ViewRouteHelper.LOGIN;

	}

	@GetMapping("/logout")
	public String logout(Model model) {

		usuarioService.logoutUsuario();
		return ViewRouteHelper.LOGOUT;

	}

	
	@GetMapping("/loginprocess")
	public String logincProcess(Model model) {

		return "redirect:/loginsuccess";

	}
	
	@GetMapping("/loginsuccess")
	public String loginCheck(Model model) {

		return "redirect:/home";

	}




	@GetMapping("/lista-usuarios")
	public ModelAndView listaUsuario() {
		ModelAndView mov = new ModelAndView(ViewRouteHelper.USUARIOS);
		List<UsuarioModel> list = usuarioService.listUsuarios();
		mov.addObject("listaUsuarios", list);

		return mov;
	}


	@GetMapping(value = "/usuarios-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> usuarioReporte() throws IOException {
		ByteArrayInputStream pdf = usuarioService.generacionPdf();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=ListaUsuarios.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(pdf));
	}

	@GetMapping("/lista-personas")
	public ModelAndView listaPersonas() {
		ModelAndView mov = new ModelAndView();
		List<PersonaModel> list = personaService.listPersonaModel();
		mov.addObject("listaPersonas", list);
		
			mov.setViewName(ViewRouteHelper.PERSONAS);
		
		return mov;
	}

	@GetMapping("/verqr")
	public ModelAndView verQR(@RequestParam(name = "qr", required = false) String qr) {
		ModelAndView mov = new ModelAndView(ViewRouteHelper.QR);

		mov.addObject("qr", qr);

		return mov;
	}



	@PreAuthorize("hasAnyAuthority('Admin', 'Auditoria')")
	@GetMapping("/lista-permisos")
	public ModelAndView listaPermiso() {
		ModelAndView mov = new ModelAndView(ViewRouteHelper.PERMISO);
		List<Permiso> list = permisoService.listPermisos();
		mov.addObject("listaPermisos", list);

		return mov;
	}
}
