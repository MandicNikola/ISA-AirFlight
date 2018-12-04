package rs.ftn.isa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;


public class Filijala {
		
		private int id;		
		private String grad;
		private String ulica;
		/*
		@ManyToMany
	    @JoinTable(name = "pripadafilijali",
	               joinColumns = @JoinColumn(name="filijala_id", referencedColumnName="id"),
	               inverseJoinColumns = @JoinColumn(name="vehicle_id", referencedColumnName="id"))
		private Set<Vehicle> vehicles = new HashSet<Vehicle>();
		
*/
		
		
		public Filijala() {}
		

		public Filijala(String grad, String ulica, Set<Vehicle> vehicles) {
			super();
			this.grad = grad;
			this.ulica = ulica;
			//this.vehicles = vehicles;
		}
		public Filijala(int id, String grad, String ulica, Set<Vehicle> vehicles) {
			super();
			this.id = id;
			this.grad = grad;
			this.ulica = ulica;
			//this.vehicles = vehicles;
		}
		
		
		/*
		public Set<Vehicle> getVehicles() {
			return vehicles;
		}
		public void setVehicles(Set<Vehicle> vehicles) {
			this.vehicles = vehicles;
		}
		*/
		public int getId() {
			return id;
		}
		public void setId(int id) {
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
