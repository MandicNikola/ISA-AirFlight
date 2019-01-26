package rs.ftn.isa.service;

import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Relation;

@Service
public interface RelationService {

	Relation saveRelation(Relation relation);
	void deleteRelation(Long id);
	Relation findOneById(Long id);
	
}
