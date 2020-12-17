package com.ftn.Takmicenja.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Takmicenje {
	
	private Long id;
	private String naziv;
	private String tipTakmicenja;
	
	private String grad;
	private String drzava;
	private LocalDateTime datumPocetka;
	private LocalDateTime datumZavrsetka;
	private List<Disciplina> discipline = new ArrayList<>();
	

	





	public Takmicenje(Long id, String naziv, String tipTakmicenja, String grad, String drzava,
			LocalDateTime datumPocetka, LocalDateTime datumZavrsetka, List<Disciplina> discipline) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.tipTakmicenja = tipTakmicenja;
		this.grad = grad;
		this.drzava = drzava;
		this.datumPocetka = datumPocetka;
		this.datumZavrsetka = datumZavrsetka;
		this.discipline = discipline;
	}




	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTipTakmicenja() {
		return tipTakmicenja;
	}


	public void setTipTakmicenja(String tipTakmicenja) {
		this.tipTakmicenja = tipTakmicenja;
	}


	public String getGrad() {
		return grad;
	}


	public void setGrad(String grad) {
		this.grad = grad;
	}


	public String getDrzava() {
		return drzava;
	}


	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}




	public LocalDateTime getDatumPocetka() {
		return datumPocetka;
	}


	public void setDatumPocetka(LocalDateTime datumPocetka) {
		this.datumPocetka = datumPocetka;
	}


	public LocalDateTime getDatumZavrsetka() {
		return datumZavrsetka;
	}


	public void setDatumZavrsetka(LocalDateTime datumZavrsetka) {
		this.datumZavrsetka = datumZavrsetka;
	}


	public List<Disciplina> getDiscipline() {
		return discipline;
	}

	
	
	
	public ArrayList<Long> getDiscID(){
		ArrayList<Long> deoID = new ArrayList<Long>();
		for (Disciplina deo1 : discipline) {
			deoID.add(deo1.getId());
			
		}
		return deoID;
	}
	
	

	public void setDiscipline(List<Disciplina> discipline) {
		this.discipline = discipline;
	}




	@Override
	public String toString() {
		return "Takmicenje [id=" + id + ", naziv=" + naziv + ", tipTakmicenja=" + tipTakmicenja + ", grad=" + grad
				+ ", drzava=" + drzava + ", datumPocetka=" + datumPocetka + ", datumZavrsetka=" + datumZavrsetka
				+ ", discipline=" + discipline + "]";
	}



	
	
	
	

}
