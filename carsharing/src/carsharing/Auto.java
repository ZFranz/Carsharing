package carsharing;

public class Auto {

	String targa;
	String marca;
	String modello;
	Double costo_giornaliero;

	public Auto(String targa, String marca, String modello, Double costo_giornaliero) {
		super();
		this.targa = targa;
		this.marca = marca;
		this.modello = modello;
		this.costo_giornaliero = costo_giornaliero;
	}

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public Double getCosto_giornaliero() {
		return costo_giornaliero;
	}

	public void setCosto_giornaliero(Double costo_giornaliero) {
		this.costo_giornaliero = costo_giornaliero;
	}

	@Override
	public String toString() {
		return "Auto [targa=" + targa + ", marca=" + marca + ", modello=" + modello + ", costo_giornaliero="
				+ costo_giornaliero + "]";
	}
}
