package rs.ftn.isa.model;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
@Table(name="filijale")
public class Filijala {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		@Column(name="grad", nullable = false)
		private String grad;

		@Column(name="ulica", nullable = false)
		private String ulica;
		
    	@ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "servis_id")
		private RentACar servis;
				
    	@OneToMany(mappedBy = "filijala", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JsonIgnore
    	private Set<Vehicle> vozila = new HashSet<Vehicle>();

    	
    	    	
		public Filijala() {
			super();
		}
		
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

		
		public Set<Vehicle> getVozila() {
			return vozila;
		}

		public void setVozila(Set<Vehicle> vozila) {
			this.vozila = vozila;
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

		public RentACar getServis() {
			return servis;
		}

		public void setServis(RentACar servis) {
			this.servis = servis;
		}
		
		
		
		
}
