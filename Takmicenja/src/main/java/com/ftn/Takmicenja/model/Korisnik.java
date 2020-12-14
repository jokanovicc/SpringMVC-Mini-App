package com.ftn.Takmicenja.model;

public class Korisnik {
	
	private String ime;
	private String prezime;
	private String korisnickoIme;
	private String korisnickaSifra;
	private boolean isAdministrator;
	
	
	public Korisnik(String ime, String prezime, String korisnickoIme, String korisnickaSifra, boolean isAdministrator) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.korisnickaSifra = korisnickaSifra;
		this.isAdministrator = isAdministrator;
	}


	public String getIme() {
		return ime;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public String getPrezime() {
		return prezime;
	}


	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}


	public String getKorisnickoIme() {
		return korisnickoIme;
	}


	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}


	public String getKorisnickaSifra() {
		return korisnickaSifra;
	}


	public void setKorisnickaSifra(String korisnickaSifra) {
		this.korisnickaSifra = korisnickaSifra;
	}


	public boolean isAdministrator() {
		return isAdministrator;
	}


	public void setAdministrator(boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}


	@Override
	public String toString() {
		return "Korisnik [ime=" + ime + ", prezime=" + prezime + ", korisnickoIme=" + korisnickoIme
				+ ", korisnickaSifra=" + korisnickaSifra + ", isAdministrator=" + isAdministrator + "]";
	}
	
	
	
	
	
	
	
	
	

}
