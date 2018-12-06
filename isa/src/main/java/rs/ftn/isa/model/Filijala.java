package rs.ftn.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Filijala {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		private String grad;
		private String ulica;
		@ManyToMany
	    @JoinTable(name = "pripadafilijali",
	               joinColumns = @JoinColumn(name="filijala_id", referencedColumnName="id"),
	               inverseJoinColumns = @JoinColumn(name="vehicle_id", referencedColumnName="id"))
		private Set<Vehicle> vehicles = new HashSet<Vehicle>();
				
		public Filijala() {}
		
		public Filijala( String grad, String ulica) {
			super();
			this.grad = grad;
			this.ulica = ulica;
		
		}
		public Filijala(Long id, String grad, String ulica) {
			super();
			this.id = id;
			this.grad = grad;
			this.ulica = ulica;
		}



		public Set<Vehicle> getVehicles() {
			return vehicles;
		}
		public void setVehicles(Set<Vehicle> vehicles) {
			this.vehicles = vehicles;
		}
		
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getGrad() {
			return grad;
		}
		public void setGrad(String grad) {
			this.grad = grad;
		}
		public String getUlica() {
			return ulica;
		}
		public void setUlica(String ulica) {
			this.ulica = ulica;
		}
		
		
		
		
}
