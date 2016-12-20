package carsharing;

import java.util.Date;

public class Noleggio {

	int codice_noleggio;
	String auto;
	String socio;
	Date inizio;
	Date fine;
	byte auto_restituita;

	public Noleggio(int codice_noleggio, String auto, String socio, Date inizio, Date fine, byte auto_restituita) {
		super();
		this.codice_noleggio = codice_noleggio;
		this.auto = auto;
		this.socio = socio;
		this.inizio = inizio;
		this.fine = fine;
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

	public byte getAuto_restituita() {
		return auto_restituita;
	}

	public void setAuto_restituita(byte auto_restituita) {
		this.auto_restituita = auto_restituita;
	}

	@Override
	public String toString() {
		return "Noleggio [codice_noleggio=" + codice_noleggio + ", auto=" + auto + ", socio=" + socio + ", inizio="
				+ inizio + ", fine=" + fine + ", auto_restituita=" + auto_restituita + "]";
	}
}
