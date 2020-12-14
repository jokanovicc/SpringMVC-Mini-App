package com.ftn.Takmicenja.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Takmicenje {
	private String naziv;
	private enum Tip{
		DVORANA,
		OTVORENO
	}
	private Tip tip;
	
	private String grad;
	private String drzava;
	private SimpleDateFormat datumPocetka;
	private SimpleDateFormat datumZavrsetka;
	private List<Disciplina> discipline = new ArrayList<>();
	
	
	public Takmicenje(String naziv, Tip tip, String grad, String drzava, SimpleDateFormat datumPocetka,
			SimpleDateFormat datumZavrsetka, List<Disciplina> discipline) {
		super();
		this.naziv = naziv;
		this.tip = tip;
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


	public Tip getTip() {
		return tip;
	}


	public void setTip(Tip tip) {
		this.tip = tip;
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


	public SimpleDateFormat getDatumPocetka() {
		return datumPocetka;
	}


	public void setDatumPocetka(SimpleDateFormat datumPocetka) {
		this.datumPocetka = datumPocetka;
	}


	public SimpleDateFormat getDatumZavrsetka() {
		return datumZavrsetka;
	}


	public void setDatumZavrsetka(SimpleDateFormat datumZavrsetka) {
		this.datumZavrsetka = datumZavrsetka;
	}


	public List<Disciplina> getDiscipline() {
		return discipline;
	}


	public void setDiscipline(List<Disciplina> discipline) {
		this.discipline = discipline;
	}


	@Override
	public String toString() {
		return "Takmicenje [naziv=" + naziv + ", tip=" + tip + ", grad=" + grad + ", drzava=" + drzava
				+ ", datumPocetka=" + datumPocetka + ", datumZavrsetka=" + datumZavrsetka + ", discipline=" + discipline
				+ "]";
	}
	
	
	
	

}
