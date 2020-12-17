package com.ftn.Takmicenja.model;

public class Korisnik {
	String ime="", prezime="", korisnickoIme="", korisnickaSifra="";
	private Long id;
	boolean administrator = false;
	boolean ulogovan=false;
	
	
	public Korisnik() {
		super();
	}


	
	

	public Korisnik(String ime, String prezime, String korisnickoIme, String korisnickaSifra, Long id,
			boolean administrator, boolean ulogovan) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.korisnickaSifra = korisnickaSifra;
		this.id = id;
		this.administrator = administrator;
		this.ulogovan = ulogovan;
	}





	public Korisnik(String ime, String prezime, String korisnickoIme, String korisnickaSifra) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.korisnickaSifra = korisnickaSifra;
	}
	
	


	public Korisnik(String ime, String prezime, String korisnickoIme, String korisnickaSifra, boolean administrator) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.korisnickaSifra = korisnickaSifra;
		this.administrator = administrator;
	}



	

	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
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
		return administrator;
	}




	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}




	public boolean isUlogovan() {
		return ulogovan;
	}




	public void setUlogovan(boolean ulogovan) {
		this.ulogovan = ulogovan;
	}




	@Override
	public String toString() {
		return "Korisnik [ime=" + ime + ", prezime=" + prezime + ", korisnickoIme=" + korisnickoIme
				+ ", korisnickaSifra=" + korisnickaSifra;
	}
	
	
	
	
	
	
	
	
	

}
