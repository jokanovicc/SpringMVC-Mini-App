package com.ftn.Takmicenja.kontroleri;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Takmicenja")
public class PrijavaKontroler {
	
	@GetMapping
	@ResponseBody
	 public void getLogin(HttpServletRequest request, HttpServletResponse response) {
			try {
				response.sendRedirect("login.html");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	

}
