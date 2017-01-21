package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Database {

	/**
	 * ricerca dei soci
	 * @return soci
	 */
	public ArrayList<Socio> listaSoci() {
		ArrayList<Socio> elenco = new ArrayList<Socio>();
		Connection cn;
		Statement st;
		ResultSet rs;
		String sql;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		try {
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsharing?user=root&password=");

			// ________________________________query
			sql = "SELECT * FROM soci;";
			System.out.println(sql); // stampa la query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			rs = st.executeQuery(sql); // faccio la query su uno statement
			while (rs.next() == true) {
				Socio s = new Socio(rs.getString("cf"), rs.getString("cognome"), rs.getString("nome"),
						rs.getString("indirizzo"), rs.getString("telefono"));
				elenco.add(s);
			}

			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
			e.printStackTrace();
		} // fine try-catch

		return elenco;
	}

	/**
	 * Cerca l'elenco dei noleggi dati un data d'inizio e una di fine
	 * 
	 * @param dataI
	 * @param dataF
	 * @param cf
	 * @return elenco
	 */
	public ArrayList<Noleggio> cercaNoleggio(Date dataI, Date dataF, String cf) {
		ArrayList<Noleggio> elenco = new ArrayList<Noleggio>();
		Connection cn;
		Statement st;
		ResultSet rs;
		String sql;

		Calendar data = Calendar.getInstance();
		data.setTime(dataI);
		String dataInizio = data.get(Calendar.YEAR) + "-" + (data.get(Calendar.MONTH) + 1) + "-"
				+ data.get(Calendar.DAY_OF_MONTH);
		data.setTime(dataF);
		String dataFine = data.get(Calendar.YEAR) + "-" + (data.get(Calendar.MONTH) + 1) + "-"
				+ data.get(Calendar.DAY_OF_MONTH);

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		try {
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsharing?user=root&password=");

			// ________________________________query
			sql = "SELECT * FROM noleggi WHERE socio='" + cf + "' AND inizio>='" + dataInizio + "' AND inizio<='"
					+ dataFine + "';";
			System.out.println(sql); // stampa la query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			rs = st.executeQuery(sql); // faccio la query su uno statement
			while (rs.next() == true) {
				Noleggio n = new Noleggio(rs.getInt("codice_noleggio"), rs.getString("auto"), rs.getString("socio"),
						rs.getDate("inizio"), rs.getDate("fine"), rs.getBoolean("auto_in_uso"),
						rs.getBoolean("auto_restituita"));
				elenco.add(n);
				System.out.println(n);
			}

			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
			e.printStackTrace();
		} // fine try-catch

		return elenco;
	}

	/**
	 * Cerca auto disponibili
	 * 
	 * @param dataI
	 * @param dataF
	 * @param cf
	 * @return elenco
	 */
	public ArrayList<Auto> cercaAuto(Date dataI, Date dataF, String cf) {
		ArrayList<Auto> elenco = new ArrayList<Auto>();
		ArrayList<String> targhe = new ArrayList<String>();
		Connection cn;
		Statement st;
		ResultSet rs;
		String sql;
		java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date datatemp = null;
		String datad = "";
		String temp = "";

		Calendar data = Calendar.getInstance();
		data.setTime(dataI);
		String dataInizio = data.get(Calendar.YEAR) + "-" + (data.get(Calendar.MONTH) + 1) + "-"
				+ data.get(Calendar.DAY_OF_MONTH);
		data.setTime(dataF);
		String dataFine = data.get(Calendar.YEAR) + "-" + (data.get(Calendar.MONTH) + 1) + "-"
				+ data.get(Calendar.DAY_OF_MONTH);
		try {
			datatemp = df.parse(dataInizio);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dataInizio = df.format(datatemp);
		try {
			datatemp = df.parse(dataFine);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dataFine = df.format(datatemp);

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		try {
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsharing?user=root&password=");

			// ________________________________query
			sql = "SELECT noleggi.auto FROM noleggi WHERE noleggi.auto_restituita=0 AND noleggi.auto_in_uso=1;";
			System.out.println(sql); // stampa la query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			rs = st.executeQuery(sql); // faccio la query su uno statement
			while (rs.next() == true) {
				targhe.add(rs.getString("auto"));
			}

			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
			e.printStackTrace();
		} // fine try-catch

		try {
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsharing?user=root&password=");

			// ________________________________query
			sql = "SELECT * FROM auto LEFT JOIN noleggi ON auto.targa=noleggi.auto WHERE noleggi.auto_restituita IS NULL OR (noleggi.auto_restituita=1 OR noleggi.auto_in_uso=0) GROUP BY auto.targa;";
			System.out.println(sql); // stampa la query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			rs = st.executeQuery(sql); // faccio la query su uno statement
			while (rs.next() == true) {
				for (int i = 0; i < targhe.size(); i++) {
					if (targhe.get(i).equals(rs.getString("targa"))) {
						break;
					} else if (i == (targhe.size() - 1)) {
						datad = rs.getString("inizio");
						temp = rs.getString("auto_restituita");
						if(datad != null) { // se la data nel database non è vuota controlla se è prenotabile
							if(datad.compareTo(dataInizio) <= 0 && temp.equals("0")) {
								break;
							}
							if(datad.compareTo(dataFine) <= 0 && temp.equals("0")) {
								break;
							}
						}
						Auto a = new Auto(rs.getString("targa"), rs.getString("marca"), rs.getString("modello"), rs.getDouble("costo_giornaliero"));
						elenco.add(a);
					}
				}
			}

			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
			e.printStackTrace();
		} // fine try-catch

		return elenco;
	}

	/**
	 * Inserisco un nuovo noleggio
	 * 
	 * @param targa
	 * @param cf
	 * @param dataInizio
	 * @param dataFine
	 */
	public void nuovoNoleggio(String targa, String cf, String dataInizio, String dataFine) {
		Connection cn;
		Statement st;
		String sql;

		// ________________________________connessione
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		try {
			// Creo la connessione al database
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsharing?user=root&password=");

			// ________________________________query
			sql = "INSERT INTO noleggi (auto, socio, inizio, fine, auto_in_uso, auto_restituita) VALUES ('" + targa
					+ "', '" + cf + "', '" + dataInizio + "', '" + dataFine + "', 0, 0);";
			System.out.println(sql);

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			st.execute(sql); // faccio la query su uno statement
			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
		} // fine try-catch
	}
	
	/**
	 * il socio conferma la prenotazione dell'auto
	 * @param targa
	 */
	public void confermaPrenotazione(String targa) {
		Connection cn;
		Statement st;
		String sql;

		// ________________________________connessione
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		try {
			// Creo la connessione al database
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsharing?user=root&password=");

			// ________________________________query
			sql = "UPDATE noleggi SET noleggi.auto_in_uso=true WHERE noleggi.auto='" + targa + "';";
			System.out.println(sql);

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			st.execute(sql); // faccio la query su uno statement
			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
		} // fine try-catch
	}
	
	/**
	 * Calcolo il costo del noleggio
	 * @param diff
	 * @param ritardo
	 * @param temp_codice_noleggio
	 * @param targa
	 * @param dataC
	 * @return
	 */
	public double consegnaAuto(int diff, int ritardo, String temp_codice_noleggio, String targa, String dataC) {
		double costo = 0;
		Connection cn;
		Statement st;
		String sql;

		// ________________________________connessione
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		try {
			// Creo la connessione al database
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsharing?user=root&password=");

			// ________________________________query
			// se la data di consegna è giusta consegna solo l'auto
			if(ritardo == 0) {
				sql = "UPDATE noleggi SET noleggi.auto_restituita=true WHERE noleggi.codice_noleggio='" + temp_codice_noleggio + "';";
			} else {
				// altrimenti consegna l'auto e modifica la data di fine noleggio
				sql = "UPDATE noleggi SET noleggi.auto_restituita=true, noleggi.fine='" + dataC + "' WHERE noleggi.codice_noleggio='" + temp_codice_noleggio + "';";
			}
			
			System.out.println(sql);

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			st.execute(sql); // faccio la query su uno statement
			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
		} // fine try-catch
		
		if(ritardo == 0) {
			switch (targa) {
			case "AA222DS":
				costo = diff * 27.00;
				break;

			case "AB009FG":
				costo = diff * 25.00;
				break;

			case "BB333EE":
				costo = diff * 50.00;
				break;

			case "BC111KL":
				costo = diff * 30.00;
				break;
			}
		} else {
			switch(targa) {
			case "AA222DS":
				costo = diff * 27.00 + ritardo * 10.00;
				break;
				
			case "AB009FG":
				costo = diff * 25.00 + ritardo * 10.00;
				break;
				
			case "BB333EE":
				costo = diff * 50.00 + ritardo * 10.00;
				break;
				
			case "BC111KL":
				costo = diff * 30.00 + ritardo * 10.00;
				break;
			}
		}
		
		return costo;
	}
	
	/**
	 * Ottengo la lista di tutte le auto esistenti
	 * @return
	 */
	public ArrayList<Auto> listaAuto() {
		ArrayList<Auto> elenco = new ArrayList<Auto>();
		Connection cn;
		Statement st;
		ResultSet rs;
		String sql;

		try {
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsharing?user=root&password=");

			// ________________________________query
			sql = "SELECT * FROM auto;";
			System.out.println(sql); // stampa la query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			rs = st.executeQuery(sql); // faccio la query su uno statement
			while (rs.next() == true) {
				Auto a = new Auto(rs.getString("targa"), rs.getString("marca"), rs.getString("modello"), rs.getDouble("costo_giornaliero"));
				elenco.add(a);
			}

			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
			e.printStackTrace();
		} // fine try-catch

		return elenco;
	}
	
	/**
	 * rimuovi la'uto selezionata e avverto i soci rispettivi
	 * @param targa
	 */
	public void rimuoviAuto(String targa) {
		ArrayList<String> nomi = new ArrayList<String>();
		ArrayList<Socio> soci = new ArrayList<Socio>();
		Connection cn;
		Statement st;
		String sql;
		ResultSet rs;
		
		// ottengo la lista dei soci
		soci = listaSoci();

		// cerco i soci di cui l'auto per il noleggio è stata eliminata
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		try {
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsharing?user=root&password=");

			// ________________________________query
			sql = "SELECT socio FROM noleggi WHERE auto='" + targa + "' AND auto_in_uso=0;";
			System.out.println(sql); // stampa la query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			rs = st.executeQuery(sql); // faccio la query su uno statement
			while (rs.next() == true) {
				String nome = rs.getString("socio");
				nomi.add(nome);
			}

			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
			e.printStackTrace();
		} // fine try-catch
		for(int i = 0; i < nomi.size(); i++) {
			for(int j = 0; j < soci.size(); j++) {
				if(soci.get(j).getCf().equals(nomi.get(i))) {
					// avverto il socio che il noleggio è stato cancellato
					System.out.println("Spiacente socio: " + soci.get(j).getNome() + " " + soci.get(j).getCognome() + ", ma il noleggio è stato cancellato in quanto l'auto non è più disponibile.");
				}
			}
		}
		
		// ________________________________connessione
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		} // fine try-catch

		try {
			// Creo la connessione al database
			cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carsharing?user=root&password=");

			// ________________________________query
			
			// elimino i noleggi con la targa dell'auto da rimuovere
			sql = "DELETE FROM noleggi WHERE auto='" + targa + "';";
			System.out.println(sql);

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			st.execute(sql); // faccio la query su uno statement
			
			// elimino l'auto dalla lista
			sql = "DELETE FROM auto WHERE targa='" + targa + "';";
			System.out.println(sql);

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			st.execute(sql); // faccio la query su uno statement
			
			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
		} // fine try-catch
	}
}
