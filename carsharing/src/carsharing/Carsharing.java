package carsharing;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.wb.swt.SWTResourceManager;

public class Carsharing {

	protected Shell shlCarsharing;
	private Table table;
	private String cf = "";
	Database d = new Database();
	private ArrayList<Noleggio> risultatiNoleggio = new ArrayList<Noleggio>();
	private ArrayList<Auto> risultatiAuto = new ArrayList<Auto>();
	private ArrayList<Socio> risultatiSocio = new ArrayList<Socio>();
	java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	String temp_auto_in_uso = "";
	String temp_auto_restituita = "";
	String targa = "";
	String temp_dataInizio = "";
	String temp_dataFine = "";
	String temp_codice_noleggio = "";
	double costo = 0;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Carsharing window = new Carsharing();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlCarsharing.open();
		shlCarsharing.layout();
		while (!shlCarsharing.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * restituisce il codice fiscale dati in ingresso cognome e nome
	 * 
	 * @param cliente
	 * @return codiceFiscale
	 */
	private String ottieniCF(String cliente) {
		String codiceFiscale = "";
		String clienti[] = cliente.split(" ");
		for (int i = 0; i < risultatiSocio.size(); i++) {
			if (clienti[0].equals(risultatiSocio.get(i).getCognome())
					&& clienti[1].equals(risultatiSocio.get(i).getNome())) {
				codiceFiscale = risultatiSocio.get(i).getCf();
			}
		}
		return codiceFiscale;
	}

	/**
	 * Svuota la tabella
	 */
	private void svuotaTabella() {
		table.removeAll();
	}

	/**
	 * Riempie la tabella con i risultati della ricerca
	 * 
	 * @param risultati
	 */
	private void popolaTabella(ArrayList<Noleggio> risultati) {
		for (int i = 0; i < risultati.size(); i++) {
			String dataInizio = df.format(risultati.get(i).getInizio());
			String dataFine = df.format(risultati.get(i).getFine());
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, Integer.toString(risultati.get(i).getCodice_noleggio()));
			item.setText(1, risultati.get(i).getAuto());
			item.setText(2, risultati.get(i).getSocio());
			item.setText(3, dataInizio);
			item.setText(4, dataFine);
			item.setText(5, Boolean.toString(risultati.get(i).getAuto_in_uso()));
			item.setText(6, Boolean.toString(risultati.get(i).getAuto_restituita()));
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCarsharing = new Shell();
		shlCarsharing.setSize(650, 345);
		shlCarsharing.setText("Carsharing");

		// creo e inizializzo tutti gli elementi grafici, alcuni elementi sono creati all'inizio del programma poiché utilizzati anche da altre funzioni
		Combo comboSocio = new Combo(shlCarsharing, SWT.NONE);
		Label lblCodiceFiscale = new Label(shlCarsharing, SWT.NONE);
		lblCodiceFiscale.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		Button btnNuovoNoleggio = new Button(shlCarsharing, SWT.NONE);
		btnNuovoNoleggio.setFont(SWTResourceManager.getFont("JasmineUPC", 20, SWT.NORMAL));
		Label lblDataInizio = new Label(shlCarsharing, SWT.NONE);
		DateTime dateInizio = new DateTime(shlCarsharing, SWT.BORDER);
		Label lblDataFine = new Label(shlCarsharing, SWT.NONE);
		DateTime dateFine = new DateTime(shlCarsharing, SWT.BORDER);
		Button btnCerca = new Button(shlCarsharing, SWT.NONE);
		btnCerca.setFont(SWTResourceManager.getFont("JasmineUPC", 20, SWT.NORMAL));
		table = new Table(shlCarsharing, SWT.BORDER | SWT.FULL_SELECTION);
		TableColumn tblclmnCodiceNoleggio = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnAuto = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnSocio = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnInizio = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnFine = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnAutoInUso = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnAutoRestituita = new TableColumn(table, SWT.NONE);
		Button btnConfermaPrenotazione = new Button(shlCarsharing, SWT.NONE);
		DateTime dateConsegna = new DateTime(shlCarsharing, SWT.BORDER);
		dateConsegna.setEnabled(false);
		Label lblDataConsegna = new Label(shlCarsharing, SWT.NONE);
		lblDataConsegna.setEnabled(false);
		Button btnConsegnaAuto = new Button(shlCarsharing, SWT.NONE);
		Button btnEliminaAuto = new Button(shlCarsharing, SWT.NONE);

		// all'avvio ottengo la lista dei soci e li inserisco nella combo
		risultatiSocio = d.listaSoci();
		for (int i = 0; i < risultatiSocio.size(); i++) {
			comboSocio.add(risultatiSocio.get(i).getCognome() + " " + risultatiSocio.get(i).getNome());
		}
		comboSocio.setBounds(10, 10, 120, 23);
		comboSocio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// a seconda del socio selezionato restituisce il codice fiscale corrispondente
				switch (comboSocio.getSelectionIndex()) {
				case 0:
					cf = ottieniCF(comboSocio.getText());
					lblCodiceFiscale.setText("CF: " + cf);
					break;

				case 1:
					cf = ottieniCF(comboSocio.getText());
					lblCodiceFiscale.setText("CF: " + cf);
					break;

				case 2:
					cf = ottieniCF(comboSocio.getText());
					lblCodiceFiscale.setText("CF: " + cf);
					break;

				case 3:
					cf = ottieniCF(comboSocio.getText());
					lblCodiceFiscale.setText("CF: " + cf);
					break;

				case 4:
					cf = ottieniCF(comboSocio.getText());
					lblCodiceFiscale.setText("CF: " + cf);
					break;
				}
			}
		});

		lblCodiceFiscale.setBounds(139, 10, 221, 23);
		lblCodiceFiscale.setText("CF: ");

		// inserisco un nuovo noleggio
		btnNuovoNoleggio.setBounds(441, 7, 183, 25);
		btnNuovoNoleggio.setText("Nuovo noleggio");
		btnNuovoNoleggio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isBlank(cf)) {	// se non viene selezionato nessun socio, mostra un messaggio di allerta
					MessageBox messageBox = new MessageBox(shlCarsharing);
					messageBox.setMessage("Nessun socio selezionato.");
					messageBox.setText("Alert");
					messageBox.open();
				} else {
					// prendo le date del noleggio
					Date dataI = null;
					Date dataF = null;
					Date date = null;
					Calendar calendar = Calendar.getInstance();
					String anno = Integer.toString(calendar.get(Calendar.YEAR));
					String mese = Integer.toString(calendar.get(Calendar.MONTH) + 1);
					String giorno = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
					try {
						date = df.parse(anno + "-" + mese + "-" + giorno);
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					try {
						dataI = df.parse(
								dateInizio.getYear() + "-" + (dateInizio.getMonth() + 1) + "-" + dateInizio.getDay());
						dataF = df
								.parse(dateFine.getYear() + "-" + (dateFine.getMonth() + 1) + "-" + dateFine.getDay());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					Calendar data = Calendar.getInstance();
					data.setTime(dataI);
					String dataInizio = data.get(Calendar.YEAR) + "-" + (data.get(Calendar.MONTH) + 1) + "-"
							+ data.get(Calendar.DAY_OF_MONTH);
					data.setTime(dataF);
					String dataFine = data.get(Calendar.YEAR) + "-" + (data.get(Calendar.MONTH) + 1) + "-"
							+ data.get(Calendar.DAY_OF_MONTH);

					// controlli sulle date es: la data d'inizio non può essere più grande fi quella di fine
					if (dataI.before(dataF) || (dataI.equals(date) || dataI.after(date))) {
						if (dataI.after(dataF)) {	// se la data non è valida, mostra un messaggio di allerta
							MessageBox messageBox = new MessageBox(shlCarsharing);
							messageBox.setMessage("Data non valida.");
							messageBox.setText("Alert");
							messageBox.open();
						} else {
							// se non ci sono problemi, cerca l'elenco delle auto disponibili
							risultatiAuto = d.cercaAuto(dataI, dataF, cf);

							if (risultatiAuto.isEmpty()) {	// se la ricerca non ha dato risultati avverto il socio
								MessageBox messageBox = new MessageBox(shlCarsharing);
								messageBox.setMessage("La ricerca non ha dato nessun risultato.");
								messageBox.setText("Alert");
								messageBox.open();
							} else {
								// se la ricerca ha dato esito positivo, apre una nuova interfaccia con i dati del socio, le date, le auto disponibili per il nuovo noleggio
								NoleggiaAuto noleggiaAuto = new NoleggiaAuto(cf, dataInizio, dataFine, risultatiAuto,
										d);
								noleggiaAuto.open();
							}
						}
					} else {
						MessageBox messageBox = new MessageBox(shlCarsharing);
						messageBox.setMessage("Data non valida.");
						messageBox.setText("Alert");
						messageBox.open();
					}
				}
				// resetto i campi in cui visualizza il socio
				cf = "";
				comboSocio.setText("");
				lblCodiceFiscale.setText("CF:");
			}
		});

		lblDataInizio.setFont(SWTResourceManager.getFont("Microsoft Tai Le", 11, SWT.NORMAL));
		lblDataInizio.setBounds(10, 39, 75, 24);
		lblDataInizio.setText("Data Inizio");

		dateInizio.setBounds(90, 39, 80, 24);

		lblDataFine.setFont(SWTResourceManager.getFont("Microsoft Tai Le", 11, SWT.NORMAL));
		lblDataFine.setBounds(200, 39, 75, 24);
		lblDataFine.setText("Data Fine");

		dateFine.setBounds(280, 39, 80, 24);

		btnCerca.setBounds(441, 38, 183, 25);
		btnCerca.setText("Cerca");
		btnCerca.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isBlank(cf)) {	// se non viene selezionato nessun socio, mostra un messaggio di allerta
					MessageBox messageBox = new MessageBox(shlCarsharing);
					messageBox.setMessage("Nessun socio selezionato.");
					messageBox.setText("Alert");
					messageBox.open();
				} else {
					// prendo le date per la ricerca
					Date dataI = null;
					Date dataF = null;

					try {
						dataI = df.parse(
								dateInizio.getYear() + "-" + (dateInizio.getMonth() + 1) + "-" + dateInizio.getDay());
						dataF = df
								.parse(dateFine.getYear() + "-" + (dateFine.getMonth() + 1) + "-" + dateFine.getDay());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// cerca i noleggi che ha effettuato un socio tra le due date scelte
					risultatiNoleggio = d.cercaNoleggio(dataI, dataF, cf);

					if (risultatiNoleggio.isEmpty()) {	// se la ricerca non ha dato risultati, mostra un messaggio di allerta
						svuotaTabella();
						MessageBox messageBox = new MessageBox(shlCarsharing);
						messageBox.setMessage("La ricerca non ha dato nessun risultato.");
						messageBox.setText("Alert");
						messageBox.open();
					} else {
						// se la ricerca ha dato esito positivo, svuota la tabella e la riempie con i risultati della ricerca
						svuotaTabella();
						popolaTabella(risultatiNoleggio);
						// resetto i campi in cui visualizza il socio
						cf = "";
						comboSocio.setText("");
						lblCodiceFiscale.setText("CF:");
					}
				}
			}
		});

		table.setBounds(10, 69, 614, 200);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// quando viene selezionato un record nella tabella prende i dati di quel record
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem = table.getItem(table.getSelectionIndex());
				temp_codice_noleggio = tableItem.getText(0);
				targa = tableItem.getText(1);
				temp_dataInizio = tableItem.getText(3);
				temp_dataFine = tableItem.getText(4);
				temp_auto_in_uso = tableItem.getText(5);
				temp_auto_restituita = tableItem.getText(6);
				// a seconda dello stato dell'auto abilito o disabilito il prestito e la restituzione
				if (temp_auto_in_uso.equals("false")) {
					btnConfermaPrenotazione.setEnabled(true);
				} else {
					btnConfermaPrenotazione.setEnabled(false);
					if (temp_auto_restituita.equals("false")) {
						lblDataConsegna.setEnabled(true);
						dateConsegna.setEnabled(true);
						btnConsegnaAuto.setEnabled(true);
					} else {
						lblDataConsegna.setEnabled(false);
						dateConsegna.setEnabled(false);
						btnConsegnaAuto.setEnabled(false);
					}
				}
			}
		});

		tblclmnCodiceNoleggio.setWidth(100);
		tblclmnCodiceNoleggio.setText("codice noleggio");

		tblclmnAuto.setWidth(70);
		tblclmnAuto.setText("auto");

		tblclmnSocio.setWidth(130);
		tblclmnSocio.setText("socio");

		tblclmnInizio.setWidth(75);
		tblclmnInizio.setText("inizio");

		tblclmnFine.setWidth(75);
		tblclmnFine.setText("fine");

		tblclmnAutoInUso.setWidth(75);
		tblclmnAutoInUso.setText("auto in uso");

		tblclmnAutoRestituita.setWidth(85);
		tblclmnAutoRestituita.setText("auto restituita");

		btnConfermaPrenotazione.setEnabled(false);
		btnConfermaPrenotazione.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// il socio conferma la prenotazione
				d.confermaPrenotazione(targa);
				svuotaTabella();
				btnConfermaPrenotazione.setEnabled(false);
			}
		});
		btnConfermaPrenotazione.setBounds(10, 275, 150, 25);
		btnConfermaPrenotazione.setText("conferma prenotazione");

		dateConsegna.setBounds(260, 275, 80, 24);

		lblDataConsegna.setBounds(166, 280, 88, 15);
		lblDataConsegna.setText("Data consegna");

		btnConsegnaAuto.setBounds(346, 275, 135, 25);
		btnConsegnaAuto.setText("Consegna auto");
		btnConsegnaAuto.setEnabled(false);
		btnConsegnaAuto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// il socio restituisce l'auto
				// eseguo i controlli sulle date
				Date dataConsegna = null;
				Date data1 = null;
				Date data2 = null;
				int diff = 0;
				int ritardo = 0;

				try {
					dataConsegna = df.parse(
							dateConsegna.getYear() + "-" + (dateConsegna.getMonth() + 1) + "-" + dateConsegna.getDay());
					String dataC = df.format(dataConsegna);
					if (dataC.compareTo(temp_dataFine) < 0) {
						// se non è stata raggiunta la data di consegna, avverto il socio
						MessageBox messageBox = new MessageBox(shlCarsharing);
						messageBox.setMessage("Data di consegna non ancora raggiunta.");
						messageBox.setText("Alert");
						messageBox.open();
					} else if (dataC.compareTo(temp_dataFine) == 0) {
						// se la data di consegna corrisponde il socio paga l'importo per l'auto presa in prestito
						
						data1 = df.parse(temp_dataInizio);
						data2 = df.parse(temp_dataFine);
						
						// calcolo la differenza tra la data d'inizio e quella di fine
						diff = (int) (data2.getTime() - data1.getTime());
						diff = diff / 86400000;	
						costo = d.consegnaAuto(diff, ritardo, temp_codice_noleggio, targa, dataC);
						// visualizzo il costo del noleggio per l'auto corrispondente
						MessageBox messageBox = new MessageBox(shlCarsharing);
						messageBox.setMessage("Il costo del noleggio è: " + costo + "\ngiorni di ritardo: " + ritardo);
						messageBox.setText("Alert");
						messageBox.open();
						diff = 0;
						ritardo = 0;
					} else {
						// se la data di consegna eccede, il socio paga il prestito e un importo extra per ogni giorni di ritardo
						
						data1 = df.parse(temp_dataInizio);
						data2 = df.parse(temp_dataFine);
						
						// calcolo la differenza tra la data d'inizio e quella di fine
						diff = (int) (data2.getTime() - data1.getTime());
						diff = diff / 86400000;	
						
						ritardo = (int)(dataConsegna.getTime() - data2.getTime());
						ritardo = ritardo / 86400000;
						costo = d.consegnaAuto(diff, ritardo, temp_codice_noleggio, targa, dataC);
						// visualizzo il costo del noleggio per l'auto corrispondente più il costo per il ritardo
						MessageBox messageBox = new MessageBox(shlCarsharing);
						messageBox.setMessage("Il costo del noleggio è: " + costo + "\ngiorni di ritardo: " + ritardo);
						messageBox.setText("Alert");
						messageBox.open();
						diff = 0;
						ritardo = 0;
					}
					svuotaTabella();
					lblDataConsegna.setEnabled(false);
					dateConsegna.setEnabled(false);
					btnConsegnaAuto.setEnabled(false);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnEliminaAuto.setBounds(487, 275, 137, 25);
		btnEliminaAuto.setText("Elimina auto");
		btnEliminaAuto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EliminaAuto eliminaAuto = new EliminaAuto(d);
				eliminaAuto.open();
			}
		});
	}
}
