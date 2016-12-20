package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import it.fabiobiscaro.database.crud.Amico;

public class Database {

	/**
	 * Cerca l'elenco dei noleggi dati un data d'inizio e una di fine
	 * @param dataI
	 * @param dataF
	 * @param cf
	 * @return
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

			sql = "SELECT * FROM noleggi WHERE socio='" + cf + "' AND inizio>='" + dataInizio + "' AND inizio<='" + dataFine + "';";
			System.out.println(sql); // stampa la query
			// ________________________________query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			rs = st.executeQuery(sql); // faccio la query su uno statement
			while (rs.next() == true) {
				Noleggio n = new Noleggio(rs.getInt("codice_noleggio"),rs.getString("auto"),rs.getString("socio"),rs.getDate("inizio"),rs.getDate("fine"),rs.getBoolean("auto_restituita"));
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
	 * @return
	 */
	public ArrayList<Auto> cercaAuto(Date dataI, Date dataF, String cf) {
		ArrayList<Auto> elenco = new ArrayList<Auto>();
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

			sql = "SELECT * FROM auto INNER JOIN noleggi ON auto.targa=noleggi.auto WHERE noleggi.auto_restituita=1 AND noleggi.fine<'" + dataInizio + "' GROUP BY targa;";
			System.out.println(sql); // stampa la query
			// ________________________________query

			st = cn.createStatement(); // creo sempre uno statement sulla
										// connessione
			rs = st.executeQuery(sql); // faccio la query su uno statement
			while (rs.next() == true) {
				Auto a = new Auto(rs.getString("targa"), rs.getString("marca"), rs.getString("modello"), rs.getDouble("costo_giornaliero"));
				elenco.add(a);
				System.out.println(a);
			}

			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
			e.printStackTrace();
		} // fine try-catch
		
		return elenco;
	}
	
	/**
	 * Noleggia una nuova auto
	 * @param cf
	 */
	public void noleggiaAuto(String cf) {
		
	}
	
}
