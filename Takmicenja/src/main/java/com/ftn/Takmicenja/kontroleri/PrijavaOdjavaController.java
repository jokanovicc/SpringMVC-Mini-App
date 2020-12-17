package com.ftn.Takmicenja.kontroleri;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ftn.Takmicenja.model.Korisnik;
import com.ftn.Takmicenja.services.KorisniciServis;


@Controller
@RequestMapping(value="/PrijavaOdjava")
public class PrijavaOdjavaController {
	
	public static final String KORISNIK_KEY = "korisnik";
	
	@Autowired
	private KorisniciServis korisnikService;
	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {	
		//Specify the base URL for all relative URLs in a document
		bURL = servletContext.getContextPath()+"/";			
	}
	/** obrada podataka forme za logovanje korisnika, get zahtev */
	@SuppressWarnings("unchecked")
	// GET: PrijavaOdjava/Login
	@GetMapping(value="/Login")
	public void getLogin(@RequestParam(required = false) String korisnickoIme, @RequestParam(required = false) String korisnickaSifra, HttpSession session, HttpServletResponse response) throws IOException {		
		postLogin(korisnickoIme, korisnickaSifra, session, response);
	}
	/** obrada podataka forme za logovanje korisnika, post zahtev */
	@SuppressWarnings("unchecked")
	// POST: PrijavaOdjava/Login
	@PostMapping(value="/Login")
	public void postLogin(@RequestParam(required = false) String korisnickoIme, @RequestParam(required = false) String korisnickaSifra, HttpSession session, HttpServletResponse response) throws IOException {	
		

		
		Korisnik korisnik = korisnikService.findOne(korisnickoIme);
		String greska = "";
		if(korisnik==null)
			greska="neispravno korisničko ime<br/>";
		else if (!korisnik.getKorisnickaSifra().equals(korisnickaSifra))
			greska="neispravna korisnika šifra<br/>";
		
		if(!greska.equals("")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;	
			out = response.getWriter();
			
			StringBuilder retVal = new StringBuilder();
			retVal.append(
					"<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
//					"	<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">\r\n" + 
//					"	<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\"> \r\n" + 
//					"	<META HTTP-EQUIV=\"Expires\" CONTENT=\"0\">\r\n" +
					"	<meta charset=\"UTF-8\">\r\n" + 
					"	<base href=\"/Takmicenja/\">	\r\n" + 
					"	<title>Prijava korisnika</title>\r\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n");
			if(!greska.equals(""))
				retVal.append(
					"	<div>"+greska+"</div>\r\n");
			retVal.append(
					"	<form method=\"post\" action=\"PrijavaOdjava/Login\">\r\n" + 
					"		<table>\r\n" + 
					"			<caption>Prijava korisnika na sistem</caption>\r\n" + 
					"			<tr><th>korisničko ime:</th><td><input type=\"text\" value=\"\" name=\"korisnickoIme\" required/></td></tr>\r\n" + 
					"			<tr><th>korisnicka šifra:</th><td><input type=\"password\" value=\"\" name=\"korisnickaSifra\" required/></td></tr>\r\n" + 
					"			<tr><th></th><td><input type=\"submit\" value=\"Prijavi se\" /></td>\r\n" + 
					"		</table>\r\n" + 
					"	</form>\r\n" + 
					"	<br/>\r\n" + 
					"</body>\r\n" + 
					"</html>");
			
			out.write(retVal.toString());
			return;
		}
		
		if (korisnik.isUlogovan()==true)
			greska="korisnik je prijavljen negde, potrebno je odjaviti se<br/>";
		else if (session.getAttribute(PrijavaOdjavaController.KORISNIK_KEY)!=null)
			greska="korisnik je već prijavljen na sistem morate se prethodno odjaviti<br/>";
		
		if(!greska.equals("")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;	
			out = response.getWriter();
			
			StringBuilder retVal = new StringBuilder();
			retVal.append(
					"<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
//					"	<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">\r\n" + 
//					"	<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\"> \r\n" + 
//					"	<META HTTP-EQUIV=\"Expires\" CONTENT=\"0\">\r\n" +
					"	<meta charset=\"UTF-8\">\r\n" + 
					"	<base href=\"/Takmicenja/\">	\r\n" + 
					"	<title>Prijava korisnika</title>\r\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n");
			if(!greska.equals(""))
				retVal.append(
					"	<div>"+greska+"</div>\r\n");
			retVal.append(
					"	<a href=\"index.html\">Povratak</a>\r\n" + 
					"	<br/>\r\n" + 
					"</body>\r\n" + 
					"</html>");
			
			out.write(retVal.toString());
			return;
		}
		
		korisnik.setUlogovan(true);
		session.setAttribute(PrijavaOdjavaController.KORISNIK_KEY, korisnik);
		
		
		response.sendRedirect(bURL+"Takmicenje");
	}
	

}














