package com.ftn.Takmicenja.kontroleri;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftn.Takmicenja.model.Korisnik;
import com.ftn.Takmicenja.model.Prijava;
import com.ftn.Takmicenja.model.Takmicenje;
import com.ftn.Takmicenja.services.DisciplinaService;
import com.ftn.Takmicenja.services.PrijavaService;
import com.ftn.Takmicenja.services.TakmicenjaService;

@Controller
@RequestMapping(value="/PrijaveSvoje")
public class SvojePrijaveKontroler {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Autowired
	 private PrijavaService prijavaService;
	
	@Autowired
	private TakmicenjaService takmicenjaService;
	
	@Autowired
	private DisciplinaService discService;	
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL;
	
	
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {
		//Specify the base URL for all relative URLs in a document
		bURL = servletContext.getContextPath()+"/";
		
	
		
		
		
	}
	
	
	
	

	@GetMapping
	@ResponseBody
	public String Index(HttpSession session, HttpServletResponse response) throws IOException {

		StringBuilder retVal = new StringBuilder();
		
		//preuzimanje vrednosti iz sesije za klijenta
		Korisnik korisnik = (Korisnik) session.getAttribute(LoginController.KORISNIK_KEY);
		if(korisnik==null) {
			response.sendRedirect(bURL+"login.html");
		}
		retVal.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
//				"	<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">\r\n" + 
//				"	<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\"> \r\n" + 
//				"	<META HTTP-EQUIV=\"Expires\" CONTENT=\"0\">\r\n" +
				"	<meta charset=\"UTF-8\">\r\n" + 
	    		"	<base href=\""+bURL+"\">\r\n" + 
				"	<title>Takmicenja</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviTabela.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body> "+
				"	<div> Prijavljen je "+ korisnik.getIme() +" "+ korisnik.getPrezime());
			
		retVal.append(	
				"		<table>\r\n" + 
				"			<caption>Takmicenja</caption>\r\n" + 
				"			<tr bgcolor=\"DodgerBlue\">\r\n" + 
				"				<th>Takmicenje</th>\r\n" + 
				"				<th>Prijavljeni</th>\r\n" + 
				"				<th>Drzava</th>\r\n" + 
				"				<th>Disciplina</th>\r\n" +
				"				<th>Vreme</th>\r\n" + 
				"			</tr>\r\n");
		
		
		List<Prijava> prijave;
		
		prijave = prijavaService.findAll();
		
		
		for (int i = 0; i < prijave.size(); i++) {
			if(korisnik.getKorisnickoIme().equals(prijave.get(i).getKorisnik().getKorisnickoIme())) {
				retVal.append(
						"			<tr>\r\n" + 
								"<td>"  +prijave.get(i).getTakmicenje().getNaziv() +        "</td>" +

								"<td>" + prijave.get(i).getKorisnik().getIme() + " " +prijave.get(i).getKorisnik().getPrezime() +      "</td>"+
								"<td>"  +prijave.get(i).getDrzava() +        "</td>" +
								"<td>"  +prijave.get(i).getDisciplina().getNaziv() +        "</td>" +
								"<td>"  +prijave.get(i).getDatum().format(formatter) +        "</td>" +
								"			</tr>\r\n");
			}

			
		}
		
		retVal.append(
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n");
				
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");	
		
		
		return retVal.toString();

			
			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}