package com.ftn.Takmicenja.model;

public class Disciplina {
	private Long id;
	private String naziv;
	
	
	public Disciplina(Long id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	@Override
	public String toString() {
		return  naziv;
	}
	
	
	
	
	

}
