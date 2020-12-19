package com.ftn.Takmicenja.services.Impl;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.ftn.Takmicenja.model.Takmicenje;
import com.ftn.Takmicenja.services.DisciplinaService;
import com.ftn.Takmicenja.services.TakmicenjaService;


@Service
@Primary
@Qualifier("fajloviTakmicenja")
public class TakmicenjeImpl implements TakmicenjaService {
	
    @Value("${takmicenja.pathToFile}")
    private String pathToFile;
    
    @Autowired
    private DisciplinaService disciplinaService;
    private DisciplinaImplService impleDisc;
    


    private Map<Long, Takmicenje> takmicenja;
    
    private Map<Long, Takmicenje> readFromFile() {

        Map<Long, Takmicenje> takmicenja = new HashMap<>();
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
                
                
                Long takmicenjeId = Long.parseLong(tokens[0]);

                String naziv = tokens[1];
                String tipTakmicenja = tokens[2];
                String grad = tokens[3];
                String drzava = tokens[4];
                LocalDateTime datumPocetka = LocalDateTime.parse(tokens[5], TakmicenjaService.formatter);
                LocalDateTime datumZavrsetka = LocalDateTime.parse(tokens[6], TakmicenjaService.formatter);
                
                
                ArrayList<Disciplina> discipline = new ArrayList<Disciplina>();
                String[] split = tokens[7].split(",");
                for (String disciplina : split) {
                	Long id = Long.parseLong(disciplina);
                	Disciplina d = disciplinaService.findOne(id);
                	discipline.add(d);
					
				}
                
       
				/*
				 * String[] disciplineSplit = discipline.split("|"); ArrayList<Disciplina> deo2
				 * = new ArrayList<Disciplina>(); for (String sif : disciplineSplit) {
				 * Disciplina d = nadjiDeo(sif); if(d != null) { deo2.add(d);
				 * 
				 * }
				 * 
				 * }
				 */
                

				/*
				 * Film film = filmService.findOne(filmId); if (film == null) continue;
				 */
				Takmicenje t = new Takmicenje(takmicenjeId,naziv, tipTakmicenja, grad, drzava, datumPocetka, datumZavrsetka, discipline);
				
				System.out.println(t);
				
                takmicenja.put(Long.parseLong(tokens[0]), t);
                if(nextId<takmicenjeId)
                    nextId=takmicenjeId;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return takmicenja;
    }

	/*
	 * public Disciplina nadjiDisc(Long id) { for (Disciplina disciplina : sve) {
	 * if(disciplina.getId()==id) { return disciplina; } } return null; }
	 */
	private Map<Long, Takmicenje> saveToFile(Map<Long, Takmicenje> takmicenja) {

        @SuppressWarnings("unchecked")
		Map<Long, Takmicenje> ret = new HashMap();

        try {
            Path path = Paths.get(pathToFile);
            System.out.println(path.toFile().getAbsolutePath());
            List<String> lines = new ArrayList<String>();

            for (Takmicenje takmicenje : takmicenja.values()) {
                String line = takmicenje.getId() + ";"+ takmicenje.getNaziv() +";"+ takmicenje.getTipTakmicenja() + ";" + takmicenje.getGrad() + ";" + takmicenje.getDrzava() + ";" + takmicenje.getDatumPocetka().format(TakmicenjaService.formatter) +";"+ takmicenje.getDatumZavrsetka().format(TakmicenjaService.formatter) +";"+ disciplineString(takmicenje.getDiscipline()); 
                lines.add(line);
                ret.put(takmicenje.getId(), takmicenje);
            }

            Files.write(path, lines, Charset.forName("UTF-8"));
            System.out.println(" 1 JEL STIGLO DOVDE?");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }
	
	
	
	public String disciplineString(List<Disciplina> discipline) {      //Metoda sluzi za upis disciplina u fajl, da bude , izmedju
		String discString = "";
		for (int i = 0; i < discipline.size(); i++) {
			discString += discipline.get(i).getId() + ",";
			
		}
		
		String disciplineString = discString.substring(0,discString.length()-1);
		
		
		return disciplineString ;
		
		
	
		
	}
	
	
	
	
	
	 private Long nextId(Map<Long, Takmicenje> takmicenja) {
	        Long nextId = 1L;
	        for (Long id : takmicenja.keySet()) {
	            if(nextId<id)
	                nextId=id;
	        }
	        return ++nextId;
	    }
	    
		@Override
		public Takmicenje findOne(Long id) {
	        Map<Long, Takmicenje> takmicenja = readFromFile();
	        return takmicenja.get(id);
		}

		@Override
		public List<Takmicenje> findAll() {
	        Map<Long, Takmicenje> takmicenja = readFromFile();
	        return new ArrayList<Takmicenje>(takmicenja.values());
		}

		@Override
		public Takmicenje save(Takmicenje takmicenje) {
			Map<Long, Takmicenje> takmicenja = readFromFile();
	        Long nextId = nextId(takmicenja);


	        takmicenja.put(takmicenje.getId(), takmicenje);
	        saveToFile(takmicenja);
	        return takmicenje;
		}

		@Override
		public List<Takmicenje> save(List<Takmicenje> takmicenjeLista) {
	        Map<Long, Takmicenje> takmicenja = readFromFile();
	        Long nextId = nextId(takmicenja);

	        List<Takmicenje> ret = new ArrayList<>();

	        for (Takmicenje takmicenje: takmicenjeLista) {


	            if (takmicenje.getId() == null) {
	                takmicenje.setId(nextId++);

	            }

	            takmicenja.put(takmicenje.getId(), takmicenje);
	            ret.add(takmicenje);
	        }

	        saveToFile(takmicenja);
	        return ret;
		}

		@Override
		public Takmicenje update(Takmicenje takmicenje) {
	        Map<Long, Takmicenje> takmicenja = readFromFile();
	        takmicenja.put(takmicenje.getId(), takmicenje);
	        saveToFile(takmicenja);
	        return takmicenje;
		}

		@Override
		public List<Takmicenje> update(List<Takmicenje> takmicenjeLista) {
	        Map<Long, Takmicenje> takmicenja = readFromFile();

	        List<Takmicenje> ret = new ArrayList<>();
	        for (Takmicenje takmicenje : takmicenjeLista) {
	            takmicenja.put(takmicenje.getId(), takmicenje);
	        }
	        takmicenja = saveToFile(takmicenja);
	        ret = new ArrayList<Takmicenje>(takmicenja.values());

	        return ret;
		}

		@Override
		public Takmicenje delete(Long id) {
	        Map<Long, Takmicenje> takmicenja = readFromFile();

	        if (!takmicenja.containsKey(id)) {
	            throw new IllegalArgumentException("tried to remove non existing");
	        }

	        Takmicenje takmicenje = takmicenja.get(id);
	        if (takmicenje != null) {
	        	takmicenja.remove(id);
	        }
	        saveToFile(takmicenja);
	        return takmicenje;
		}

		@Override
		public void delete(List<Long> ids) {
	        Map<Long, Takmicenje> takmicenja = readFromFile();
	        for (Long id : ids) {
	            if (!takmicenja.containsKey(id)) {
	                throw new IllegalArgumentException("tried to remove non existing");
	            }

	            Takmicenje takmicenje = takmicenja.get(id);
	            if (takmicenje != null) {
	            	takmicenja.remove(id);
	            }
	        }

	        saveToFile(takmicenja);
			
		}

		@Override
		public List<Takmicenje> findByNaziv(String naziv) {
	        Map<Long, Takmicenje> takmicenja = readFromFile();
	        List<Takmicenje> ret = new ArrayList<>();

	        for (Takmicenje t : takmicenja.values()) {
	            if (naziv == t.getNaziv()) {
	                ret.add(t);
	            }
	        }
	        return ret;
		}

		@Override
		public List<Takmicenje> findByTipTakmicenja(String tipTakmicenja) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Takmicenje> findByGrad(String grad) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Takmicenje> findByDrzava(String drzava) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Takmicenje> findByDatumPocetka(LocalDateTime datumPocetka) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Takmicenje> findByDatumZavrsetka(LocalDateTime datumZavrsetka) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Takmicenje> findByDisciplina(List<Disciplina> disciplina) {
			// TODO Auto-generated method stub
			return null;
		}
}
