package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Database {

	/**
	 * 
	 * @return
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
				Socio s = new Socio(rs.getString("cf"), rs.getString("cognome"), rs.getString("nome"), rs.getString("indirizzo"), rs.getString("telefono"));
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
			sql = "SELECT * FROM noleggi WHERE socio='" + cf + "' AND inizio>='" + dataInizio + "' AND inizio<='" + dataFine + "';";
			System.out.println(sql); // stampa la query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			rs = st.executeQuery(sql); // faccio la query su uno statement
			while (rs.next() == true) {
				Noleggio n = new Noleggio(rs.getInt("codice_noleggio"), rs.getString("auto"), rs.getString("socio"), rs.getDate("inizio"), rs.getDate("fine"), rs.getBoolean("auto_in_uso") , rs.getBoolean("auto_restituita"));
				elenco.add(n);
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
		
		Calendar data = Calendar.getInstance();
		data.setTime(dataI);
		String dataInizio = data.get(Calendar.YEAR) + "-" + (data.get(Calendar.MONTH) + 1) + "-"
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
				for(int i = 0; i < targhe.size(); i++) {
					if(targhe.get(i).equals(rs.getString("targa"))) {
						break;
					} else if(i == (targhe.size() - 1)){
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
			sql = "INSERT INTO noleggi (auto, socio, inizio, fine, auto_in_uso, auto_restituita) VALUES ('" + targa + "', '" + cf + "', '" + dataInizio+ "', '" + dataFine + "', 0, 0);";
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
