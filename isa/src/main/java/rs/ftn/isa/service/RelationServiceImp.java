package rs.ftn.isa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ftn.isa.model.Relation;
import rs.ftn.isa.repository.RelationRepository;

@Service
public class RelationServiceImp implements RelationService{

	@Autowired
	RelationRepository repozitorijum;
	
	
	@Override
	public Relation saveRelation(Relation relation) {
		// TODO Auto-generated method stub
		repozitorijum.save(relation);
		return relation;
	}

	@Override
	public void deleteRelation(Long id) {
		// TODO Auto-generated method stub
		repozitorijum.deleteById(id);
		
	}

	@Override
	public Relation findOneById(Long id) {
		// TODO Auto-generated method stub
		return repozitorijum.findOneById(id);
	}

}
