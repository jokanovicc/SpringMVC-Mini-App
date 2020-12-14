package com.ftn.Takmicenja.model;

import java.text.SimpleDateFormat;

public class Prijava {
	private Korisnik korisnik;
	private String drzava;
	private Disciplina disciplina;
	private SimpleDateFormat datum;
	
	public Prijava(Korisnik korisnik, String drzava, Disciplina disciplina, SimpleDateFormat datum) {
		super();
		this.korisnik = korisnik;
		this.drzava = drzava;
		this.disciplina = disciplina;
		this.datum = datum;
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

	public SimpleDateFormat getDatum() {
		return datum;
	}

	public void setDatum(SimpleDateFormat datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Prijava [korisnik=" + korisnik + ", drzava=" + drzava + ", disciplina=" + disciplina + ", datum="
				+ datum + "]";
	}
	
	
	
	
	

}
