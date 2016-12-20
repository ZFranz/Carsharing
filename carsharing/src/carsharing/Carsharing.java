package carsharing;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	private ArrayList<Noleggio> risultati = new ArrayList<Noleggio>();
	java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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

	private void svuotaTabella() {
		table.removeAll();
	}

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
			item.setText(5, Boolean.toString(risultati.get(i).getAuto_restituita()));
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCarsharing = new Shell();
		shlCarsharing.setSize(575, 350);
		shlCarsharing.setText("Carsharing");

		Combo comboSocio = new Combo(shlCarsharing, SWT.NONE);

		Button btnNuovoNoleggio = new Button(shlCarsharing, SWT.NONE);
		btnNuovoNoleggio.setFont(SWTResourceManager.getFont("JasmineUPC", 20, SWT.NORMAL));
		Label lblDataInizio = new Label(shlCarsharing, SWT.NONE);
		DateTime dateInizio = new DateTime(shlCarsharing, SWT.BORDER);
		Label lblDataFine = new Label(shlCarsharing, SWT.NONE);
		DateTime dateFine = new DateTime(shlCarsharing, SWT.BORDER);
		Button btnCerca = new Button(shlCarsharing, SWT.NONE);
		btnCerca.setFont(SWTResourceManager.getFont("JasmineUPC", 20, SWT.NORMAL));
		table = new Table(shlCarsharing, SWT.BORDER | SWT.FULL_SELECTION);
		TableColumn tblCodiceNoleggio = new TableColumn(table, SWT.NONE);
		TableColumn tblAuto = new TableColumn(table, SWT.NONE);
		TableColumn tblSocio = new TableColumn(table, SWT.NONE);
		TableColumn tblInizio = new TableColumn(table, SWT.NONE);
		TableColumn tblFine = new TableColumn(table, SWT.NONE);
		TableColumn tblAutoRestituita = new TableColumn(table, SWT.NONE);

		comboSocio.setItems(new String[] { "ROSSI MARIO", "ROSSI LUCA", "BIANCHI OLGA", "VERDI ANNA", "ADAMI ALDO" });
		comboSocio.setBounds(10, 10, 350, 23);
		comboSocio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				switch (comboSocio.getSelectionIndex()) {
				case 0:
					cf = "RSSMRA19T54A000Z";
					break;

				case 1:
					cf = "RSSLCA21A78A000Q";
					break;

				case 2:
					cf = "BNCLGO68B80E111T";
					break;

				case 3:
					cf = "VRDNNA41C66S456W";
					break;

				case 4:
					cf = "DMALDA18D91A000A";
					break;
				}
			}
		});

		btnNuovoNoleggio.setBounds(366, 8, 183, 25);
		btnNuovoNoleggio.setText("Nuovo noleggio");
		btnNuovoNoleggio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isBlank(cf)) {
					MessageBox messageBox = new MessageBox(shlCarsharing);
					messageBox.setMessage("Nessun socio selezionato.");
					messageBox.setText("Alert");
					messageBox.open();
				} else {
					Date dataI = null;
					Date dataF = null;

					try {
						dataI = df
								.parse(dateInizio.getYear() + "-" + dateInizio.getMonth() + "-" + dateInizio.getDay());
						dataF = df.parse(dateFine.getYear() + "-" + dateFine.getMonth() + "-" + dateFine.getDay());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
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

		btnCerca.setBounds(366, 38, 183, 25);
		btnCerca.setText("Cerca");
		btnCerca.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (StringUtils.isBlank(cf)) {
					MessageBox messageBox = new MessageBox(shlCarsharing);
					messageBox.setMessage("Nessun socio selezionato.");
					messageBox.setText("Alert");
					messageBox.open();
				} else {
					Date dataI = null;
					Date dataF = null;

					try {
						dataI = df
								.parse(dateInizio.getYear() + "-" + dateInizio.getMonth() + "-" + dateInizio.getDay());
						dataF = df.parse(dateFine.getYear() + "-" + dateFine.getMonth() + "-" + dateFine.getDay());
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					risultati = d.cercaNoleggio(dataI, dataF, cf);

					if (risultati.isEmpty()) {
						MessageBox messageBox = new MessageBox(shlCarsharing);
						messageBox.setMessage("La ricerca non ha dato nessun risultato.");
						messageBox.setText("Alert");
						messageBox.open();
					} else {
						svuotaTabella();
						popolaTabella(risultati);
						cf = "";
					}
				}
			}
		});

		table.setBounds(10, 69, 539, 233);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tblCodiceNoleggio.setWidth(100);
		tblCodiceNoleggio.setText("codice noleggio");

		tblAuto.setWidth(70);
		tblAuto.setText("auto");

		tblSocio.setWidth(130);
		tblSocio.setText("socio");

		tblInizio.setWidth(75);
		tblInizio.setText("inizio");

		tblFine.setWidth(75);
		tblFine.setText("fine");

		tblAutoRestituita.setWidth(85);
		tblAutoRestituita.setText("auto restituita");
	}
}
