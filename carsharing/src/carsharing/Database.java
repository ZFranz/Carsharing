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

	public ArrayList<Noleggio> cercaNoleggio(Date dataI, Date dataF, String cf) {
		ArrayList<Noleggio> elenco = new ArrayList<Noleggio>();
		Connection cn;
		Statement st;
		ResultSet rs;
		String sql;
		
		Calendar data = Calendar.getInstance();
		data.setTime(dataI);
		String dataInizio = data.get(Calendar.YEAR) + "-" + data.get(Calendar.MONTH) + "-"
				+ data.get(Calendar.DAY_OF_MONTH);
		data.setTime(dataF);
		String dataFine = data.get(Calendar.YEAR) + "-" + data.get(Calendar.MONTH) + "-"
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
				Noleggio n=new Noleggio(rs.getInt("codice_noleggio"),rs.getString("auto"),rs.getString("socio"),rs.getDate("inizio"),rs.getDate("fine"),rs.getBoolean("auto_restituita"));
				elenco.add(n);
			}

			cn.close(); // chiusura connessione
		} catch (SQLException e) {
			System.out.println("errore:" + e.getMessage());
			e.printStackTrace();
		} // fine try-catch
		return elenco; 

	}
	
}
