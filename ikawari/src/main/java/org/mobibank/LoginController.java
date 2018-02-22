package org.mobibank;

import javax.servlet.http.HttpServletRequest;

import org.mobibank.backend.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@Autowired
	UtilisateurService utilisateurService;
	
	@RequestMapping(value = "login")
	public String signin(Model model, HttpServletRequest request) {
		return "login";
	}
	
	@RequestMapping(value = "otp")
	public String otp(Model model, HttpServletRequest request) {
		return "otp";
	}
}
