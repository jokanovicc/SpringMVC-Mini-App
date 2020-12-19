package com.ftn.Takmicenja.services.Impl;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ftn.Takmicenja.model.Korisnik;
import com.ftn.Takmicenja.services.KorisniciServis;


@Service
@Primary
@Qualifier("fajloviKorisnici")
public class KorisniciImplService implements KorisniciServis {
	
	
	
	

    @Value("${korisnici.pathToFile}")
    private String pathToFile;
	
	
	
    private Map<String, Korisnik> korisnici;
    
    
    private Map<String, Korisnik> readFromFile() {

        Map<String, Korisnik> korisnici = new HashMap<>();

        try {
            Path path = Paths.get(pathToFile);
            System.out.println(path.toFile().getAbsolutePath());
            List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

            for (String line : lines) {
                line = line.trim();
                if (line.equals("") || line.indexOf('#') == 0)
                    continue;
                String[] tokens = line.split(";");
                String korime = tokens[0];
                String sifra = tokens[1];
                String ime = tokens[2];
                String prezime = tokens[3];
                Boolean admin =  Boolean.parseBoolean(tokens[4]);
                Korisnik k = new Korisnik(ime, prezime, korime, sifra,admin);
                
            
                
                korisnici.put(korime, k);
                
                
                
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return korisnici;
    }
	
	
	
	

	@Override
	public Korisnik findOne(String korisnickoIme) {
		Map<String, Korisnik> korisnici = readFromFile();		
		return korisnici.get(korisnickoIme);
	}

	@Override
	public Korisnik findOne(String korisnickoIme, String korisnickaSifra) {
		Korisnik korisnik = findOne(korisnickoIme);
		if (korisnik != null && korisnik.getKorisnickaSifra().equals(korisnickaSifra))
			return korisnik;
		return null;
	}

	@Override
	public List<Korisnik> findAll() {
		Map<String, Korisnik> korisnici = readFromFile();
		// TODO Auto-generated method stub
		return new ArrayList<Korisnik>(korisnici.values());
	}


	@Override
	public Korisnik save(Korisnik korisnik) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Korisnik> save(List<Korisnik> korisnici) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Korisnik update(Korisnik korisnik) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Korisnik> update(List<Korisnik> korisnici) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Korisnik delete(String korisnickoIme) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(List<String> korisnickaImena) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Korisnik> findByIme(String ime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Korisnik> findByPrezime(String prezime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Korisnik> findByAdministrator(boolean administrator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Korisnik> findByUlogovan(boolean ulogovan) {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public Korisnik findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
