package com.ftn.Takmicenja.model;

import java.time.LocalDateTime;

public class Prijava {
	private Long id;
	private Korisnik korisnik;
	private String drzava;
	private Disciplina disciplina;
	private LocalDateTime datum;
	private Takmicenje takmicenje;
	





	public Prijava(Long id, Korisnik korisnik, String drzava, Disciplina disciplina, LocalDateTime datum,
			Takmicenje takmicenje) {
		super();
		this.id = id;
		this.korisnik = korisnik;
		this.drzava = drzava;
		this.disciplina = disciplina;
		this.datum = datum;
		this.takmicenje = takmicenje;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}


	
	
	public LocalDateTime getDatum() {
		return datum;
	}

	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	
	public Takmicenje getTakmicenje() {
		return takmicenje;
	}

	public void setTakmicenje(Takmicenje takmicenje) {
		this.takmicenje = takmicenje;
	}

	@Override
	public String toString() {
		return "Prijava [korisnik=" + korisnik + ", drzava=" + drzava + ", disciplina=" + disciplina + ", datum="
				+ datum + "]";
	}
	
	
	
	
	

}
