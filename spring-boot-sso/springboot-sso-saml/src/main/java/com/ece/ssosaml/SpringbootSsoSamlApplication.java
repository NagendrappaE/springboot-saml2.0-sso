package com.ece.ssosaml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("ssologin")
public class SpringbootSsoSamlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSsoSamlApplication.class, args);
	}
	
	@GetMapping("")
	public String ssoLogin(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
		
		System.out.println("logged in "+principal.getName());
		return "Loggin user"+principal.getName();
	}
	
	
	@GetMapping("serviceprovider")
	public String serviceProvider() {
		return "Service provider";
		
	}

}
