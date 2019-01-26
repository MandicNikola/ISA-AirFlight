package rs.ftn.isa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ftn.isa.model.Relation;

public interface RelationRepository extends JpaRepository<Relation, Long>{

	Relation findOneById(Long id);
	
}
