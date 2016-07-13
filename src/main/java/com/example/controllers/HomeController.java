package com.example.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	private Logger logger =  LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		logger.info("Serve index page");
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		logger.info("Serve login page");
		return "login";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping(value = "/after", method = RequestMethod.GET)
	public String after(Principal principal) {
		if (principal.getName().equals("admin")) {
			logger.info("Serve admin page {}", principal.getName());
			return "admin";
		} else {
			logger.info("Serve user page {}", principal.getName());
			return "user";
		}
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String fourOthree() {
		logger.info("Serve error 403 page");
		return "403";
	}

	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		logger.info("Serve error page");
		return "error";
	}

}
