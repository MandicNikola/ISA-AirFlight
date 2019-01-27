package rs.ftn.isa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rs.ftn.isa.model.Hotel;
import rs.ftn.isa.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
//bice sve metode koje su napravljene vec za nas
		
	User findOneByMail(String mail);
	User findOneById(Long id);
	
	@Query("select u " + 
			"from User u  " + 
			"where u.prezime like ?2 AND u.ime LIKE ?1")
	List<User> findUsersByImeAndPrezime(String ime, String prezime);
	
//	@Query("select s from Student s where s.lastName = ?1")
	
	/*
	  @Query("update  user s set s.verifikovan = ?1 where s.mail = ?2")
	void updateUserInfoByMail(String verifikovan, String mail);
	
	*/
}