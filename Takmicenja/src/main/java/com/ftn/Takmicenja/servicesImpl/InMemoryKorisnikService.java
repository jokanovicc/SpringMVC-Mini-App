package com.ftn.Takmicenja.servicesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.ftn.Takmicenja.model.Korisnik;
import com.ftn.Takmicenja.services.KorisniciServis;


@Service
public class InMemoryKorisnikService implements KorisniciServis {
	
	HashMap<String, Korisnik> korisnici = new HashMap<String, Korisnik>();

	@PostConstruct
    private void iniDataForTesting() {
		
		save(new Korisnik("Petar", "Petrovic", "pero", "pero", true));
		save(new Korisnik("Jovan", "Petrovic", "jovo", "jovo", false));    
		}
	
	
	
	
	
	
	
	
	
	@Override
	public Korisnik findOne(String korisnickoIme) {
		return korisnici.get(korisnickoIme);
	}

	@Override
	public Korisnik findOne(String korisnickoIme, String korisnickaSifra) {
		Korisnik korisnik = findOne(korisnickoIme);
		if(korisnik!=null && korisnik.getKorisnickaSifra().equals(korisnickaSifra))
			return korisnik;
		return null;
	}

	@Override
	public List<Korisnik> findAll() {
		return new ArrayList<Korisnik>(korisnici.values());
	}

	@Override
	public Korisnik save(Korisnik korisnik) {
		//u slučaju da bazi nema korisnik za korisnickoIme
		//tada se radi o insert novog korisnika
		if (korisnici.get(korisnik.getKorisnickoIme()) == null) {
			korisnici.put(korisnik.getKorisnickoIme(), korisnik);
		}
		
		return korisnik;
	}

	@Override
	public List<Korisnik> save(List<Korisnik> korisnici) {
		List<Korisnik> ret = new ArrayList<>();

		for (Korisnik k : korisnici) {
			// za svaku prosleđenog korisnika pozivamo save
			// metodu koju smo već napravili i testirali -
			// ona će sepobrinuti i za dodelu ID-eva
			// ako je to potrebno
			Korisnik saved = save(k);

			// uspešno snimljene dodajemo u listu za vraćanje
			if (saved != null) {
				ret.add(saved);
			}
		}

		return ret;
	}

	@Override
	public Korisnik update(Korisnik korisnik) {
		//u slučaju da bazi postoji korisnik za korisnickoIme
		//tada se radi o update postojeceg korisnika
		if (korisnici.get(korisnik.getKorisnickoIme()) != null) {
			korisnici.put(korisnik.getKorisnickoIme(), korisnik);
		}
				
		// TODO Auto-generated method stub
		return korisnik;
	}

	@Override
	public List<Korisnik> update(List<Korisnik> korisnici) {
		List<Korisnik> ret = new ArrayList<>();

		for (Korisnik k : korisnici) {
			// za svaku prosleđenog korisnika pozivamo update
			// metodu koju smo već napravili i testirali -
			Korisnik updated = update(k);

			// uspešno azuriranje dodajemo u listu za vraćanje
			if (updated != null) {
				ret.add(updated);
			}
		}

		return ret;
	}

	@Override
	public Korisnik delete(String korisnickoIme) {
		if (!korisnici.containsKey(korisnickoIme)) {
			throw new IllegalArgumentException("tried to remove non existing projekcija");
		}
		Korisnik korisnik = korisnici.get(korisnickoIme);
		if (korisnik != null) {
			korisnici.remove(korisnickoIme);
		}
		return korisnik;
	}

	@Override
	public void delete(List<String> korisnickaImena) {
		for (String korisnickoIme : korisnickaImena) {
			// pozivamo postojeću metodu za svaki
			delete(korisnickoIme);
		}
		
	}

	@Override
	public List<Korisnik> findByIme(String ime) {
		List<Korisnik> ret = new ArrayList<>();

		for (Korisnik k : korisnici.values()) {
			if (ime.equals(k.getIme())) {
				ret.add(k);
			}
		}
		return ret;
	}

	@Override
	public List<Korisnik> findByPrezime(String prezime) {
		List<Korisnik> ret = new ArrayList<>();

		for (Korisnik k : korisnici.values()) {
			if (prezime.equals(k.getPrezime())) {
				ret.add(k);
			}
		}
		return ret;
	}

	@Override
	public List<Korisnik> findByAdministrator(boolean administrator) {
		List<Korisnik> ret = new ArrayList<>();

		for (Korisnik k : korisnici.values()) {
			if (administrator == k.isAdministrator()) {
				ret.add(k);
			}
		}
		return ret;
	}

	
}
