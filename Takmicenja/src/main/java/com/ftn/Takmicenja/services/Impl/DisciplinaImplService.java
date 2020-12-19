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

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ftn.Takmicenja.model.Disciplina;
import com.ftn.Takmicenja.services.DisciplinaService;

@Service
@Primary
@Qualifier("fajloviDiscipline")
public class DisciplinaImplService implements DisciplinaService {
	
	
    @Value("${discipline.pathToFile}")
    private String pathToFile;
	
    private Map<Long, Disciplina> discipline;
    
    
    
    
    private Map<Long, Disciplina> readFromFile() {

        Map<Long, Disciplina> discipline = new HashMap<>();
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
                Long discpilinaId = Long.parseLong(tokens[0]);
                String naziv = tokens[1];

                Disciplina disciplina = new Disciplina(discpilinaId, naziv);
                discipline.put(Long.parseLong(tokens[0]),disciplina);
                if(nextId<discpilinaId)
                    nextId=discpilinaId;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return discipline;
    }    
    
    
    
    
    
    private Map<Long, Disciplina> saveToFile(Map<Long, Disciplina> discipline) {

        Map<Long, Disciplina> ret = new HashMap();

        try {
            Path path = Paths.get(pathToFile);
            System.out.println(path.toFile().getAbsolutePath());
            List<String> lines = new ArrayList<String>();

            for (Disciplina disciplina : discipline.values()) {
                String line = disciplina.getId() + ";" +
                        disciplina.getNaziv();
                lines.add(line);
                ret.put(disciplina.getId(),disciplina);
            }

            Files.write(path, lines, Charset.forName("UTF-8"));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }
    
    
    
    
    
    private Long nextId(Map<Long, Disciplina> discipline) {
        Long nextId = 1L;
        for (Long id : discipline.keySet()) {
            if(nextId<id)
                nextId=id;
        }
        return ++nextId;
    }    
    
    
    
    
	
	
	
	
	
	

	@Override
	public Disciplina findOne(Long id) {
        Map<Long, Disciplina> discipline = readFromFile();
        return discipline.get(id);

	}
	

	public Disciplina findOne2(String naziv) {
        Map<Long, Disciplina> discipline = readFromFile();
        return discipline.get(naziv);

	}
	

	@Override
	public List<Disciplina> findAll() {
        Map<Long, Disciplina> discipline = readFromFile();
        return new ArrayList<Disciplina>(discipline.values());
	}

	@Override
	public List<Disciplina> find(Long[] ids) {
		if (ids == null) {
			return new ArrayList<Disciplina>();
		}
		List<Disciplina> rezultat = new ArrayList<>();
		for (Long itId: ids) {
			Disciplina dics = findOne(itId);
			rezultat.add(dics);
		}

		return rezultat;
	}

	@Override
	public Disciplina save(Disciplina disciplina) {
		Map<Long, Disciplina> discipline = readFromFile();
        Long nextId = nextId(discipline);


        if (disciplina.getId() == null) {
            disciplina.setId(nextId++);

        }
        discipline.put(disciplina.getId(), disciplina);
        saveToFile(discipline);
        return disciplina;	
		
	}

	@Override
	public List<Disciplina> save(List<Disciplina> disciplineLista) {
		Map<Long, Disciplina> discipline = readFromFile();
		
        Long nextId = nextId(discipline);

        List<Disciplina> ret = new ArrayList<>();

        for (Disciplina disciplina: disciplineLista) {


            if (disciplina.getId() == null) {
               disciplina.setId(nextId++);

            }

            discipline.put(disciplina.getId(), disciplina);
            ret.add(disciplina);
        }

        saveToFile(discipline);
        return ret;		

	}

	@Override
	public Disciplina update(Disciplina disciplina) {
		Map<Long, Disciplina> discipline = readFromFile();
        discipline.put(disciplina.getId(), disciplina);
        saveToFile(discipline);
        return disciplina;

	}

	@Override
	public List<Disciplina> update(List<Disciplina> disciplineLista) {
        Map<Long, Disciplina> discipline = readFromFile();
        
        List<Disciplina> ret = new ArrayList<>();
        for (Disciplina disciplina : disciplineLista) {
            discipline.put(disciplina.getId(), disciplina);
        }
        discipline = saveToFile(discipline);
        ret = new ArrayList<Disciplina>(discipline.values());

        return ret;       

	}

	@Override
	public Disciplina delete(Long id) {
        Map<Long, Disciplina> discipline = readFromFile();
        
        if (!discipline.containsKey(id)) {
            throw new IllegalArgumentException("tried to remove non existing");
        }

        Disciplina disciplina = discipline.get(id);
        if (disciplina != null) {
            discipline.remove(id);
        }
        saveToFile(discipline);
        return disciplina;        

	}

	@Override
	public void delete(List<Long> ids) {
        Map<Long, Disciplina> discipline = readFromFile();
        for (Long id : ids) {
            if (!discipline.containsKey(id)) {
                throw new IllegalArgumentException("tried to remove non existing");
            }

            Disciplina disciplina = discipline.get(id);
            if (disciplina != null) {
                discipline.remove(id);
            }
        }

        saveToFile(discipline);
		
	}

	@Override
	public List<Disciplina> find(String naziv) {
		Map<Long, Disciplina> discipline = readFromFile();
		 return (List<Disciplina>) discipline.get(naziv);
	}
	

}
