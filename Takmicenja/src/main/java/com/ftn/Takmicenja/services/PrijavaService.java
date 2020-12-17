package com.ftn.Takmicenja.services;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ftn.Takmicenja.model.Disciplina;
import com.ftn.Takmicenja.model.Korisnik;
import com.ftn.Takmicenja.model.Prijava;
import com.ftn.Takmicenja.model.Takmicenje;

public interface PrijavaService {

	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	
	
	Prijava findOne(Long id); 
	List<Prijava> findAll(); 
	Prijava save(Prijava prijava); 
	List<Prijava> save(List<Prijava> prijave); 
	Prijava update(Prijava prijava); 
	List<Prijava> update(List<Prijava> prijave);
	Prijava delete(Long id); 
	void delete(List<Long> ids);
	List<Prijava> findByTakmicenje2(Long takmicenjeID);
	List<Prijava> findByTakmicenje(Takmicenje takmicenje);
	List<Prijava> findByKorisnik(Korisnik korisnik);
	List<Prijava> findByDrzava(String drzava);
	List<Prijava> findByDisciplina(Disciplina disciplina);
	List<Prijava> findByDatum(SimpleDateFormat vreme);

	
	
	
	
	
}
