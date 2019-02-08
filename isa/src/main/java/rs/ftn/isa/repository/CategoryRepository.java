package rs.ftn.isa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ftn.isa.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
    Category findOneByNaziv(String naziv);
    
   
    
	
}
