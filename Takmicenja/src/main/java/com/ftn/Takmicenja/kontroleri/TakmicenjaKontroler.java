package com.ftn.Takmicenja.kontroleri;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftn.Takmicenja.model.Disciplina;
import com.ftn.Takmicenja.model.Korisnik;
import com.ftn.Takmicenja.model.Prijava;
import com.ftn.Takmicenja.model.Takmicenje;
import com.ftn.Takmicenja.services.DisciplinaService;
import com.ftn.Takmicenja.services.PrijavaService;
import com.ftn.Takmicenja.services.TakmicenjaService;
import com.ftn.Takmicenja.services.Impl.TakmicenjeImpl;

@Controller
@RequestMapping(value="/Takmicenje")
public class TakmicenjaKontroler {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	
	@Autowired
	private TakmicenjaService takmicenjaService;
	
	@Autowired
	private DisciplinaService discService;
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL;
	@Autowired
	private PrijavaService prijavaService;
	@Autowired
	private TakmicenjeImpl TakmicenjeImpl;
	
	
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath()+"/";	
	}
	
	

	@GetMapping
	@ResponseBody
	public String index(HttpSession session, HttpServletResponse response) throws IOException {	

		
		StringBuilder retVal = new StringBuilder();
		
		//preuzimanje vrednosti iz sesije za klijenta
		Korisnik korisnik = (Korisnik) session.getAttribute(PrijavaOdjavaController.KORISNIK_KEY);
		if(korisnik==null) {
			response.sendRedirect(bURL+"login.html");
			return "";
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
				"				<th>ID</th>\r\n" + 
				"				<th>Naziv</th>\r\n" + 
				"				<th>Tip</th>\r\n" + 
				"				<th>Grad</th>\r\n" +
				"				<th>Drzava</th>\r\n" + 
				"				<th>Datum Pocetka i Datum Zavrsetka</th>\r\n" +
				"				<th>Discipline</th>\r\n" +
				"				<th>Ucesnici</th>\r\n" +
				"				<th>PRIJAVI SE NA TURNIR</th>\r\n" +
				"			</tr>\r\n");
		
		
		List<Takmicenje> takmicenja;
		
		takmicenja = takmicenjaService.findAll();
		
		
		for (int i = 0; i < takmicenja.size(); i++) {
			if(takmicenja.get(i).getDatumZavrsetka().isAfter(LocalDateTime.now())) {   //Prikazuje samo datume koji predstojeci
				retVal.append(			
						"			<tr>\r\n" +
						"<td>" +takmicenja.get(i).getId()    +  "</td>" +

						"<td><a href=\"Takmicenje/Details?id="+takmicenja.get(i).getId()+"\">" + takmicenja.get(i).getNaziv() +"</a></td>\r\n" +
						"<td>" +takmicenja.get(i).getTipTakmicenja()    +  "</td>" +
						"<td>" +takmicenja.get(i).getGrad()    +  "</td>" +
						"<td>" +takmicenja.get(i).getDrzava()   +  "</td>" +
						"<td>" +takmicenja.get(i).getDatumPocetka().format(formatter) + " do " +takmicenja.get(i).getDatumZavrsetka().format(formatter)    +  "</td>" +
						"<td>" +takmicenja.get(i).getDiscipline().toString()   +  "</td>" +
						"				<td>\r\n"+				
						"					<a href=\"Prijave?takmicenjeID="+takmicenja.get(i).getId()+"\">Pogledaj sve Ucesnike</a>\r\n" + 
						"					<form action=\"Prijave\" method=\"get\">\r\n" + 
						"						<input type=\"hidden\" name=\"takmicenjeID\" value=\""+takmicenja.get(i).getId()+"\"/>\r\n" + 
						"					</form>\r\n" + 
						"				</td>");
				
						if (korisnik.isAdministrator()==false) {
							retVal.append("				<td>"+				
									"					<a href=\"Prijave/Create?takmicenjeID="+takmicenja.get(i).getId()+"\">Prijavi</a>" + 
									"					<form action=\"Prijave/Create\" method=\"get\">" + 
									"						<input type=\"hidden\" name=\"takmicenjeID\" value=\""+takmicenja.get(i).getId()+"\"/>" + 
									"					</form>" + 
									"				</td>");
							
						}

						retVal.append("</tr>");
			}

			
			if(korisnik.isAdministrator()) {
			//	retVal.append(
					 
			//		"<td><input type=\"submit\"></td>" +
			//				"<td><input type=\"submit\"></td>");
			}
		}
		
		retVal.append(
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n");
		
		
		if(korisnik.isAdministrator()==false) {
		retVal.append(				"	<ul>\r\n" + 
				"		<li><a href=\"PrijaveSvoje\">MOJE PRIJAVE</a></li>\r\n" + 
				"	</ul>\r\n");
		}
		if(korisnik.isAdministrator()) {
			retVal.append("<h4>ZA IZMENE/BRISANJE KLIKNITE NA NAZIV TAKMICENJA</h4>");
		//	retVal.append(
				 
		//		"<td><input type=\"submit\"></td>" +
		//				"<td><input type=\"submit\"></td>");
		}
		
				
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");	
		
		
		return retVal.toString();
	
	}
	
	

	@GetMapping(value="/Details")
	@ResponseBody
	public String details(@RequestParam Long id, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {	

		

		
		//preuzimanje vrednosti iz sesije za klijenta
		Korisnik korisnik = (Korisnik) session.getAttribute(PrijavaOdjavaController.KORISNIK_KEY);
		if(korisnik==null) {
			response.sendRedirect(bURL+"login.html");
			return "";
		}

		

		Takmicenje takmicenje = takmicenjaService.findOne(id);
		if(takmicenje==null) {
			response.sendRedirect(bURL+"Takmicenje");
			return "";
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
				"	<title>Film</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body>\r\n" +
				"	<div> Prijavljen je " +korisnik.getKorisnickoIme()+" "+ korisnik.getIme() +" "+ korisnik.getPrezime() +				
				"	<form method=\"post\" action=\"Takmicenje/Edit\">\r\n" + 
				"		<input type=\"hidden\" name=\"id\" value=\""+takmicenje.getId()+"\">\r\n" + 
				"		<table>\r\n" + 
				"			<caption>Takmicenje</caption>\r\n" + 
				"			<tr><th>naziv:</th><td><input type=\"text\" required "+
				 				"value=\""+takmicenje.getNaziv()+"\" name=\"naziv\" required/></td></tr>\r\n" + 
								"<tr><th>Grad:</th><td><input type=\"text\"required "+
				 				"value=\""+takmicenje.getGrad()+"\" name=\"grad\"/></td></tr>\r\n" +
								"	<tr><th>Drzava:</th><td><input type=\"text\" required "+
				 				"value=\""+takmicenje.getDrzava()+"\" name=\"drzava\"/></td></tr>\r\n"+
								"			<tr><th>Datum Poceetka:</th>"+
				 				"<td>" +
				 					"<input type=\"date\" value=\""+takmicenje.getDatumPocetka().toLocalDate()+"\" name=\"datum\"/>&nbsp;"+
				 					"<input type=\"time\" value=\""+takmicenje.getDatumPocetka().toLocalTime()+"\" name=\"vreme\"/>"+
				 				"</td>"+
				 			"</tr>\r\n"+
							"			<tr><th>Datum Zavrsetka:</th>"+
			 				"<td>" +
			 					"<input type=\"date\" value=\""+takmicenje.getDatumZavrsetka().toLocalDate()+"\" name=\"datum2\"/>&nbsp;"+
			 					"<input type=\"time\" value=\""+takmicenje.getDatumZavrsetka().toLocalTime()+"\" name=\"vreme2\"/>"+
			 				"</td>"+
			 			"</tr>\r\n");
		
		retVal.append(
				"			<tr>\r\n"+
				"				<th>disciplina:</th>\r\n"+
				"				<td>\r\n"+		
				"					<select name=\"disciplinaID\">\r\n");
		for (Disciplina disciplina : discService.findAll()) {
			retVal.append(
				"						<option value=\""+disciplina.getId()+"\" "+(disciplina.equals(takmicenje.getDiscipline())?"selected":"")+">"+disciplina.getNaziv()+"</option>\r\n");
		}
		
		retVal.append(
				"					</select>\r\n"+
				"				</td>\n\r"+
				"			</tr>\r\n");
		retVal.append(				"			<tr>\r\n" + 
				"				<th>tip:</th>\r\n" + 
				"				<td>\r\n" + 
				"					<select name=\"tip\">\r\n" + 
				"						<option value=\"Otvoreno\"" + (takmicenje.getTipTakmicenja().equals("Otvoreno")? " selected": "") + ">Otvoreno</option>\r\n" + 
				"						<option value=\"Zatvoreno\"" + (takmicenje.getTipTakmicenja().equals("Zatvoreno")? " selected": "") + ">Zatvoreno</option>\r\n" + 
				"					</select>\r\n" + 
				"				</td>\r\n" + 
				"			</tr>\r\n");
		
		if(korisnik.isAdministrator()) {
			retVal.append(
				"			<tr><th></th><td><input type=\"submit\" value=\"Izmeni\" /></td></tr>\r\n");
		}
		retVal.append(
				"		</table>\r\n" + 
				"	</form>\r\n" + 
				"	<br/>\r\n");
		
		if(korisnik.isAdministrator()) {
			retVal.append(
				"	<form method=\"post\" action=\"Takmicenje/Delete\">\r\n" + 
				"		<input type=\"hidden\" name=\"id\" value=\""+takmicenje.getId()+"\">\r\n" + 
				"		<table>\r\n" + 
				"			<tr><th></th><td><input type=\"submit\" value=\"Obriši\"></td></tr>\r\n" + 
				"		</table>\r\n" + 
				"	</form>\r\n");
		}


		retVal.append(
				"				</ul>\r\n" + 
				"			</td>\r\n" + 
				"		</tr>\r\n" + 
				"	</table>\r\n");
				
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}
	
	
	
	


	
	@PostMapping(value="/Edit")
	public void Edit(
			@RequestParam Long id, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vreme,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum2, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime vreme2, 
			@RequestParam String naziv, @RequestParam String grad, @RequestParam String drzava, @RequestParam String tip,
			
			HttpSession session, HttpServletResponse response) throws IOException {
		
	
		
		//preuzimanje vrednosti iz sesije za klijenta
		Korisnik korisnik = (Korisnik) session.getAttribute(PrijavaOdjavaController.KORISNIK_KEY);
		if(korisnik==null || korisnik.isAdministrator()==false) {
			response.sendRedirect(bURL+"login.html");
		}
		
		
		//discipline se ne mogu izmeniti jer nisam uspeo da implementiram zajedno sa listom
		
		

		// validacija
		Takmicenje takmicenje = takmicenjaService.findOne(id);
		if (takmicenje == null) {
			response.sendRedirect(bURL + "Projekcije");
			return;
		}
		
		LocalDateTime datumIVreme1 = LocalDateTime.of(datum, vreme);
		LocalDateTime datumIVreme2 = LocalDateTime.of(datum2, vreme2);
		
		
		if (datumIVreme1.isAfter(LocalDateTime.now())) {
			takmicenje.setDatumPocetka(datumIVreme1);
			
		}
		if (datumIVreme2.isAfter(LocalDateTime.now())) {
			takmicenje.setDatumPocetka(datumIVreme2);
			
		}
		


		takmicenje.setDrzava(drzava);
		takmicenje.setGrad(grad);
		takmicenje.setNaziv(naziv);
		takmicenje.setTipTakmicenja(tip);
		takmicenjaService.update(takmicenje);
	
		response.sendRedirect(bURL + "Takmicenje");
	}
	
	
	
	@PostMapping(value="/Delete")
	public void delete(@RequestParam Long id, HttpSession session, HttpServletResponse response) throws IOException {

		
		//preuzimanje vrednosti iz sesije za klijenta
		Korisnik korisnik = (Korisnik) session.getAttribute(PrijavaOdjavaController.KORISNIK_KEY);
		if(korisnik==null || korisnik.isAdministrator()==false) {
			response.sendRedirect(bURL+"login.html");
		}
		

		List<Prijava> listaZaBrisanje = prijavaService.findByTakmicenje2(id);		
		for (Prijava prijava : listaZaBrisanje) {
			prijavaService.delete(prijava.getId());
		}		
		//brisanje vrednosti preko Servisnog sloja
		Takmicenje deleted = TakmicenjeImpl.delete(id);
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
		
		
		response.sendRedirect(bURL+"Takmicenje");
	}
	
	
	
	
	
	
	
	
	
	
	}
	
	
	
	
	
	
	
	
	

			
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

