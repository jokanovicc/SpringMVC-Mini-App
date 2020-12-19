package com.ftn.Takmicenja.services.Impl;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


import com.ftn.Takmicenja.model.Disciplina;
import com.ftn.Takmicenja.model.Korisnik;
import com.ftn.Takmicenja.model.Prijava;
import com.ftn.Takmicenja.model.Takmicenje;
import com.ftn.Takmicenja.services.DisciplinaService;
import com.ftn.Takmicenja.services.KorisniciServis;
import com.ftn.Takmicenja.services.PrijavaService;
import com.ftn.Takmicenja.services.TakmicenjaService;

@Service
@Primary
@Qualifier("fajloviPrijave")
public class PrijaveImplService implements PrijavaService {
	
	
    @Value("${prijave.pathToFile}")
    private String pathToFile;
    
    
    @Autowired
    private KorisniciServis korisniciServis;
    
    @Autowired
    private TakmicenjaService takmicenjaServis;
    @Autowired
    private DisciplinaService disciplinaServis;
    
    
    private Map<Long, Prijava> prijave;
    
    
    private Map<Long, Prijava> readFromFile() {

        Map<Long, Prijava> prijave = new HashMap<>();
        Long nextId = 1L;

        try {
            Path path = Paths.get(pathToFile);
            System.out.println(path.toFile().getAbsolutePath());
            List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

            for (String line : lines) {
                line = line.trim();
                if (line.equals("") || line.indexOf('#') == 0)
                    continue;
                String[] tokens = line.split(";");
                Long prijavaId = Long.parseLong(tokens[0]);
                String korisnik = tokens[1];
                Long takmicenjeID = Long.parseLong(tokens[2]);
                String drzava = tokens[3];
                Long disciplinaID = Long.parseLong(tokens[4]);
                LocalDateTime datumVreme = LocalDateTime.parse(tokens[5], PrijavaService.formatter);
                
                Korisnik k = korisniciServis.findOne(korisnik);
                
                Takmicenje t = takmicenjaServis.findOne(takmicenjeID);
                
                System.out.println("TAKMICENJE " + t);
                
                
                Disciplina d = disciplinaServis.findOne(disciplinaID);
                
                Prijava prijava = new Prijava(prijavaId, k, drzava, d, datumVreme, t);
               
                
                
                prijave.put(Long.parseLong(tokens[0]), prijava);
                if(nextId<prijavaId)
                    nextId=prijavaId;
                
                
                

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return prijave;
    }

    private Map<Long, Prijava> saveToFile(Map<Long, Prijava> prijave) {

        Map<Long, Prijava> ret = new HashMap();

        try {
            Path path = Paths.get(pathToFile);
            System.out.println(path.toFile().getAbsolutePath());
            List<String> lines = new ArrayList<String>();

            for (Prijava prijava : prijave.values()) {
                String line = prijava.getId() + ";" + prijava.getKorisnik().getKorisnickoIme()+ ";" +prijava.getTakmicenje().getId()+ ";" +prijava.getDrzava()+ ";" +prijava.getDisciplina().getId() + ";" +prijava.getDatum().format(PrijavaService.formatter);
                lines.add(line);
                ret.put(prijava.getId(), prijava);
            }

            Files.write(path, lines, Charset.forName("UTF-8"));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }
    
    private Long nextId(Map<Long, Prijava> prijave) {
        Long nextId = 1L;
        for (Long id : prijave.keySet()) {
            if(nextId<id)
                nextId=id;
        }
        return ++nextId;
    }   
    
    
    
	@Override
	public Prijava findOne(Long id) {
        Map<Long, Prijava> prijave = readFromFile();
		return prijave.get(id);
	}


	@Override
	public List<Prijava> findAll() {
        Map<Long, Prijava> prijave = readFromFile();
        return new ArrayList<Prijava>(prijave.values());

	}


	@Override
	public Prijava save(Prijava prijava) {
		Map<Long, Prijava> prijave = readFromFile();
		Long nextId = nextId(prijave);
		
        if (prijava.getId() == null) {
            prijava.setId(nextId++);

        }
		
        prijave.put(prijava.getId(), prijava);
        saveToFile(prijave);
        return prijava;
	}


	@Override
	public List<Prijava> save(List<Prijava> prijaveLista) {
		Map<Long, Prijava> prijave = readFromFile();
		Long nextId = nextId(prijave);
		List<Prijava> ret = new ArrayList<Prijava>();
		
        for (Prijava prijava: prijaveLista) {


            if (prijava.getId() == null) {
                prijava.setId(nextId++);

            }

            prijave.put(prijava.getId(), prijava);
            ret.add(prijava);
        }
		
        saveToFile(prijave);
        return ret;
	}


	@Override
	public Prijava update(Prijava prijava) {
		Map<Long, Prijava> prijave = readFromFile();
        prijave.put(prijava.getId(), prijava);
        saveToFile(prijave);
        return prijava;		
	}


	@Override
	public List<Prijava> update(List<Prijava> prijaveLista) {
		Map<Long, Prijava> prijave = readFromFile();
		List<Prijava> ret = new ArrayList<Prijava>();
		for (Prijava prijava : prijaveLista) {
			prijave.put(prijava.getId(), prijava);
			
		}
		prijave = saveToFile(prijave);
        ret = new ArrayList<Prijava>(prijave.values());

		return ret;
	}


	@Override
	public Prijava delete(Long id) {
		Map<Long, Prijava> prijave = readFromFile();
		
        if (!prijave.containsKey(id)) {
            throw new IllegalArgumentException("tried to remove non existing");
        }

        Prijava prijava = prijave.get(id);
        if (prijava != null) {
            prijave.remove(id);
        }
        saveToFile(prijave);
        return prijava;		
	}


	@Override
	public void delete(List<Long> ids) {
		Map<Long, Prijava> prijave = readFromFile();
		for (Long id : ids) {
            if (!prijave.containsKey(id)) {
                throw new IllegalArgumentException("tried to remove non existing");
            }
            Prijava prijava = prijave.get(id);
            if (prijava != null) {
                prijave.remove(id);
            }
			
		}
		saveToFile(prijave);
	}


	@Override
	public List<Prijava> findByKorisnik(Korisnik korisnik) {
		Map<Long, Prijava> prijave = readFromFile();
		List<Prijava> ret = new ArrayList<>();
		
        for (Prijava prijava : prijave.values()) {
            if (korisnik.equals(prijava.getKorisnik())) {
                ret.add(prijava);
            }
        }
        return ret;

	}


	@Override
	public List<Prijava> findByDrzava(String drzava) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Prijava> findByDisciplina(Disciplina disciplina) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Prijava> findByDatum(SimpleDateFormat vreme) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Prijava> findByTakmicenje(Takmicenje takmicenje) {
		Map<Long, Prijava> prijave = readFromFile();
		List<Prijava> ret = new ArrayList<>();
		
        for (Prijava prijava : prijave.values()) {
            if (takmicenje.equals(prijava.getTakmicenje())) {
                ret.add(prijava);
            }
        }
        return ret;
		
	}  

	@Override
	public List<Prijava> findByTakmicenje2(Long takmicenjeID) {
		Map<Long, Prijava> prijave = readFromFile();
		List<Prijava> ret = new ArrayList<>();
		
        for (Prijava prijava : prijave.values()) {
            if (takmicenjeID==prijava.getTakmicenje().getId()) {
                ret.add(prijava);
            }
        }
        return ret;
		
	}  

}
