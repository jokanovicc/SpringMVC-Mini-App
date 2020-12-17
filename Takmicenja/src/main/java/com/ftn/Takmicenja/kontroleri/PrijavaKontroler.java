package com.ftn.Takmicenja.kontroleri;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.ftn.Takmicenja.model.Disciplina;
import com.ftn.Takmicenja.model.Korisnik;
import com.ftn.Takmicenja.model.Prijava;
import com.ftn.Takmicenja.model.Takmicenje;
import com.ftn.Takmicenja.services.DisciplinaService;
import com.ftn.Takmicenja.services.PrijavaService;
import com.ftn.Takmicenja.services.TakmicenjaService;


@Controller
@RequestMapping(value="/Prijave")
public class PrijavaKontroler {
	
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
	
	
	
	

	@SuppressWarnings("unchecked")
	@GetMapping
	public void Index(@RequestParam(name="takmicenjeID", required=false) Long takmicenjeID, HttpServletRequest request, HttpServletResponse response) throws IOException {

		


		//preuzimanje vrednosti iz sesije za klijenta
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute(PrijavaOdjavaController.KORISNIK_KEY);
		if(korisnik==null) {
			response.sendRedirect(bURL+"login.html");
			return;
		}
		
		
		if(takmicenjeID!=null && takmicenjeID<=0) {
			response.sendRedirect(bURL+"Takmicenje");
			return;
		}
		Takmicenje takmicenje = null;
		if(takmicenjeID!=null && takmicenjaService.findOne(takmicenjeID)!=null) {
			takmicenje = takmicenjaService.findOne(takmicenjeID);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		StringBuilder retVal = new StringBuilder();
		retVal.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
//				"	<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">\r\n" + 
//				"	<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\"> \r\n" + 
//				"	<META HTTP-EQUIV=\"Expires\" CONTENT=\"0\">\r\n" +
				"	<meta charset=\"UTF-8\">\r\n" + 
	    		"	<base href=\""+bURL+"\">\r\n" + 
				"	<title>Projekcije</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviTabela.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body> "+
				"	<div> Prijavljen je "+korisnik.getKorisnickoIme()+" "+ korisnik.getIme() +" "+ korisnik.getPrezime());
		if(takmicenje!=null) {
			retVal.append(
				"	<a href=\"Takmicenje/Details?id="+takmicenje.getId()+"\">"+takmicenje.getNaziv() + "</a></li>\r\n");
/*			if(korisnik.isAdministrator())
				retVal.append(
				"	<ul>\r\n" + 
				"		<li><a href=\"Prijave/Create?filmID="+takmicenje.getId()+"\">dodavanje projekcije za film</a></li>\r\n" + 
				"	</ul>\r\n");
		}*/
		retVal.append("Prijavljeni takmicari za Takmicenje: " + "<strong>"  + takmicenje.getNaziv()+ "</strong>" + " " + "<strong>"  + takmicenje.getDatumPocetka()+ "</strong>" + "<strong>"  + takmicenje.getGrad() + "</strong>" + " " + "<strong>"  + takmicenje.getDrzava()+ "</strong>");
				
		retVal.append(	
				"		<table>\r\n" + 
				"			<caption>Prijave</caption>\r\n" + 
				"			<tr bgcolor=\"DodgerBlue\">\r\n" + 
				"				<th>r. br.</th>\r\n" + 
				"				<th>Prijavljeni</th>\r\n" + 
				"				<th>Drzava</th>\r\n" + 
				"				<th>Disciplina</th>\r\n" +
				"				<th>Vreme</th>\r\n" + 
				"			</tr>\r\n");
		
		
		
		
		
		//OVDE PUCA
		
		
		
		
		List<Prijava> prijave;
		if(takmicenje==null) {
			prijave = prijavaService.findAll();
		}
		else {
			prijave = prijavaService.findByTakmicenje2(takmicenjeID);
			System.out.println("PRIJAVA " + prijave); //problem
		}
		
		for (int i=0; i < prijave.size(); i++) {
			
			
			
			retVal.append(
				"			<tr>\r\n" + 
				"				<td class=\"broj\">"+ (i+1) +"</td>\r\n" + 
				"<td>" + prijave.get(i).getKorisnik().getIme() + " " +prijave.get(i).getKorisnik().getPrezime() +      "</td>"+
				"<td>"  +prijave.get(i).getDrzava() +        "</td>" +
				"<td>"  +prijave.get(i).getDisciplina().getNaziv() +        "</td>" +
				"<td>"  +prijave.get(i).getDatum().format(formatter) +        "</td>" +
				"			</tr>\r\n");
		}
		retVal.append(
				"		</table>\r\n");
		
		

		retVal.append(
				"				</ul>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n");
				
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		response.getWriter().write(retVal.toString());
	}
	}
	

	@GetMapping(value="/Create")
	public void Create(@RequestParam(name="tamID", required=false) Long tamID, HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setHeader("Cache-Control", "private, no-cache, no-store, must-revalidate"); // HTTP 1.1
//		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//		response.setDateHeader("Expires", 0); // Proxies.
		
		Long discID = 1L;

		//preuzimanje vrednosti iz sesije za klijenta
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute(PrijavaOdjavaController.KORISNIK_KEY);
		if(korisnik==null) {
			response.sendRedirect(bURL+"login.html");
			return;
		}
		

		if(tamID!=null && tamID<=0) {
			response.sendRedirect(bURL+"Takmicenje");
			return;
		}
		
		Disciplina disciplina = null;
		if (discID!= null && discService.findOne(discID)!=null) {
			disciplina = discService.findOne(discID);
		}
		
		Takmicenje takmicenje = null;
		if(tamID!=null && takmicenjaService.findOne(tamID)!=null) {
			takmicenje = takmicenjaService.findOne(tamID);
		}
		
		
		
		

		
		response.setContentType("text/html;charset=UTF-8");
		StringBuilder retVal = new StringBuilder();
		retVal.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
//				"	<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache\">\r\n" + 
//				"	<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\"> \r\n" + 
//				"	<META HTTP-EQUIV=\"Expires\" CONTENT=\"0\">\r\n" +
				"	<meta charset=\"UTF-8\">\r\n" + 
	    		"	<base href=\""+bURL+"\">\r\n" + 
				"	<title>Projekcija</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body>\r\n" + 
				"	<div> Prijavljen je "+korisnik.getKorisnickoIme()+" "+ korisnik.getIme() +" "+ korisnik.getPrezime() +			
				"	<form method=\"post\" action=\"Prijave/Create\">\r\n" + 
				"		<table>\r\n" + 
				"			<caption>Projekcija</caption>\r\n");
		retVal.append(
				"			<tr>\r\n"+
				"				<th>takmicenje:</th>\r\n"+
				"				<td>\r\n"+		
				"					<select name=\"tamID\">\r\n");	
		for (Takmicenje t : takmicenjaService.findAll()) {
			retVal.append(
				"						<option value=\""+t.getId()+"\" "+(t.equals(takmicenje)?"selected":"")+">"+t.getNaziv()+"</option>\r\n");    //nisam uspeo namapirati na post zahtev id takmicenja, pa sam alternativno ovako uradio
		}	
		retVal.append(
				"					</select>\r\n"+
				"				</td>\n\r"+
				"			</tr>\r\n");
		
		retVal.append(
				"			<tr>\r\n"+
				"				<th>Discipline:</th>\r\n"+
				"				<td>\r\n"+		
				"					<select name=\"discID\">\r\n");	
		for (Disciplina d : discService.findAll()) {
			retVal.append(
				"						<option value=\""+d.getId()+"\" "+(d.equals(disciplina)?"selected":"")+">"+d.getNaziv()+"</option>\r\n");
		}	
		retVal.append(
				"					</select>\r\n"+
				"				</td>\n\r"+
				"			</tr>\r\n");

		
		retVal.append(
				"			<tr><th>drzava:</th><td><input type=\"text\" maxlength=\"3\" minlength=\"3\" required  name=\"drzava\"/></td></tr>\r\n" + 
				"			<tr><th></th><td><input type=\"submit\" value=\"Dodaj\" /></td></tr>\r\n" + 
				"		</table>\r\n" + 
				"	</form>\r\n" +
				"	<br/>\r\n");
		
		retVal.append(
				"				</ul>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n");
	
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		response.getWriter().write(retVal.toString());
	}
	
	

	@PostMapping(value="/Create")
	public void Create(@RequestParam Long discID,
			@RequestParam Long tamID, @RequestParam String drzava,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		

		
		//preuzimanje vrednosti iz sesije za klijenta
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute(PrijavaOdjavaController.KORISNIK_KEY);
		if(korisnik==null) {
			response.sendRedirect(bURL+"login.html");
			return;
		}
		
		if(tamID!=null && tamID<=0) {
			response.sendRedirect(bURL+"Takmicenja");
			return;
		}
		Takmicenje takmicenje = null;
		if(tamID!=null && takmicenjaService.findOne(tamID)!=null) {
			takmicenje = takmicenjaService.findOne(tamID);
		}
		
		Disciplina disciplina = null;
		if(discID!=null && discService.findOne(discID)!=null) {
			disciplina = discService.findOne(discID);
		}
		
		
		if(tamID==null) {
			response.sendRedirect(bURL+"Takmicenja");
			return;
		}
		
		
		
		if(tamID==null) {
			response.sendRedirect(bURL+"Takmicenja");
			return;
		}
		
		
		
		
		
		
				
		LocalDateTime datumIVreme = LocalDateTime.now();
		
		Disciplina d = discService.findOne(discID);
		
	
		Prijava p = new Prijava(new Random().nextLong(), korisnik, drzava, disciplina, datumIVreme, takmicenje);
		
		System.out.println("**********************************************************************************************************");
		System.out.println(p);
		
		Prijava saved = prijavaService.save(p);
		
		response.sendRedirect(bURL+"Takmicenje");
	}
	
	
	
	
	
	
	

}
