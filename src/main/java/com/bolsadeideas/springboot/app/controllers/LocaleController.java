package com.bolsadeideas.springboot.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//este controlador se encarga de manejar a traves del request 
//de obtener el parametro referer con todos los parametros get de la peticion
@Controller
public class LocaleController {

	@GetMapping("/locale")
	public String locale(HttpServletRequest request) {
		String ultimaUrl = request.getHeader("referer");
		
		return "redirect:".concat(ultimaUrl);
	}
}
