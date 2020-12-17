package com.ftn.Takmicenja.services;

import java.util.List;

import com.ftn.Takmicenja.model.Korisnik;


public interface KorisniciServis {

	Korisnik findOne(Long id); 
	Korisnik findOne(String korisnickoIme); 
	Korisnik findOne(String korisnickoIme, String korisnickaSifra);
	List<Korisnik> findAll(); 
	Korisnik save(Korisnik korisnik); 
	List<Korisnik> save(List<Korisnik> korisnici); 
	Korisnik update(Korisnik korisnik); 
	List<Korisnik> update(List<Korisnik> korisnici);
	Korisnik delete(String korisnickoIme); 
	void delete(List<String> korisnickaImena); 
	List<Korisnik> findByIme(String ime);
	List<Korisnik> findByPrezime(String prezime);
	List<Korisnik> findByAdministrator(boolean administrator);
	List<Korisnik> findByUlogovan(boolean ulogovan);
	
	
}
