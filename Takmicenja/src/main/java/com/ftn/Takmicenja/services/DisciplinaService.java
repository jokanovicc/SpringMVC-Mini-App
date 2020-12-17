package com.ftn.Takmicenja.services;

import java.util.List;

import com.ftn.Takmicenja.model.Disciplina;

public interface DisciplinaService {
	
	Disciplina findOne(Long id);
	List<Disciplina> findAll();
	List<Disciplina> find(Long[] ids);
	Disciplina save(Disciplina disciplina);
	List<Disciplina>  save(List<Disciplina> discipline);
	Disciplina update(Disciplina disciplina);
	List<Disciplina> update(List<Disciplina> discipline);
	Disciplina delete(Long id);
	void delete(List<Long> ids);
	List<Disciplina> find(String naziv);	
	
	
	
	

}
