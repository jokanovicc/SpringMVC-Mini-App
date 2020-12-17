package com.ftn.Takmicenja.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ftn.Takmicenja.model.Disciplina;
import com.ftn.Takmicenja.model.Takmicenje;



public interface TakmicenjaService {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	Takmicenje findOne(Long id); 
	List<Takmicenje> findAll(); 
	Takmicenje save(Takmicenje takmicenje); 
	List<Takmicenje> save(List<Takmicenje> projekcije); 
	Takmicenje update(Takmicenje projekcija); 
	List<Takmicenje> update(List<Takmicenje> projekcije);
	Takmicenje delete(Long id); 
	void delete(List<Long> ids); 
	List<Takmicenje> findByNaziv(String naziv);
	List<Takmicenje> findByTipTakmicenja(String tipTakmicenja);
	List<Takmicenje> findByGrad(String grad);
	List<Takmicenje> findByDrzava(String drzava);
	List<Takmicenje> findByDatumPocetka(LocalDateTime datumPocetka);
	List<Takmicenje> findByDatumZavrsetka(LocalDateTime datumZavrsetka);	
	List<Takmicenje> findByDisciplina(List<Disciplina> disciplina);


}
