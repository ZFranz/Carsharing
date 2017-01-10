package carsharing;

import java.util.Date;

public class Noleggio {

	int codice_noleggio;
	String auto;
	String socio;
	Date inizio;
	Date fine;
	Boolean auto_in_uso;
	Boolean auto_restituita;

	public Noleggio(int codice_noleggio, String auto, String socio, Date inizio, Date fine, Boolean auto_in_uso, Boolean auto_restituita) {
		super();
		this.codice_noleggio = codice_noleggio;
		this.auto = auto;
		this.socio = socio;
		this.inizio = inizio;
		this.fine = fine;
		this.auto_in_uso = auto_in_uso;
		this.auto_restituita = auto_restituita;
	}

	public int getCodice_noleggio() {
		return codice_noleggio;
	}

	public void setCodice_noleggio(int codice_noleggio) {
		this.codice_noleggio = codice_noleggio;
	}

	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}

	public String getSocio() {
		return socio;
	}

	public void setSocio(String socio) {
		this.socio = socio;
	}

	public Date getInizio() {
		return inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	public Date getFine() {
		return fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
	}

	public Boolean getAuto_in_uso() {
		return auto_in_uso;
	}

	public void setAuto_in_uso(Boolean auto_in_uso) {
		this.auto_in_uso = auto_in_uso;
	}

	public Boolean getAuto_restituita() {
		return auto_restituita;
	}

	public void setAuto_restituita(Boolean auto_restituita) {
		this.auto_restituita = auto_restituita;
	}

	@Override
	public String toString() {
		return "Noleggio [codice_noleggio=" + codice_noleggio + ", auto=" + auto + ", socio=" + socio + ", inizio="
				+ inizio + ", fine=" + fine + ", auto_in_uso=" + auto_in_uso + ", auto_restituita=" + auto_restituita
				+ "]";
	}
}
