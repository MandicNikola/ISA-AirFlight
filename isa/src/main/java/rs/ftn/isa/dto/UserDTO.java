package rs.ftn.isa.dto;

public class UserDTO {

		private String ime;
		private String prezime;
		private String mail;
		private int telefon;
		private String grad;
		private String lozinka;
		
		public UserDTO() {}

		public UserDTO(String ime, String prezime, String mail, int telefon, String grad, String lozinka) {
			super();
			this.ime = ime;
			this.prezime = prezime;
			this.mail = mail;
			this.telefon = telefon;
			this.grad = grad;
			this.lozinka = lozinka;
		}

		public String getIme() {
			return ime;
		}

		public void setIme(String ime) {
			this.ime = ime;
		}

		public String getPrezime() {
			return prezime;
		}

		public void setPrezime(String prezime) {
			this.prezime = prezime;
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}

		public int getTelefon() {
			return telefon;
		}

		public void setTelefon(int telefon) {
			this.telefon = telefon;
		}

		public String getGrad() {
			return grad;
		}

		public void setGrad(String grad) {
			this.grad = grad;
		}

		public String getLozinka() {
			return lozinka;
		}

		public void setLozinka(String lozinka) {
			this.lozinka = lozinka;
		}
		
		
		
		
}
