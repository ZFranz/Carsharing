package carsharing;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Carsharing {

	protected Shell shlCarsharing;
	private Table table;
	private String cf = "";
	Database d=new Database();

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
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCarsharing = new Shell();
		shlCarsharing.setSize(570, 300);
		shlCarsharing.setText("Carsharing");

		Combo comboSocio = new Combo(shlCarsharing, SWT.NONE);
		DateTime dateInizio = new DateTime(shlCarsharing, SWT.BORDER);
		DateTime dateFine = new DateTime(shlCarsharing, SWT.BORDER);
		Button btnCerca = new Button(shlCarsharing, SWT.NONE);
		table = new Table(shlCarsharing, SWT.BORDER | SWT.FULL_SELECTION);
		TableColumn tblCodiceNoleggio = new TableColumn(table, SWT.NONE);
		TableColumn tblAuto = new TableColumn(table, SWT.NONE);
		TableColumn tblSocio = new TableColumn(table, SWT.NONE);
		TableColumn tblInizio = new TableColumn(table, SWT.NONE);
		TableColumn tblFine = new TableColumn(table, SWT.NONE);
		TableColumn tblAutoRestituita = new TableColumn(table, SWT.NONE);

		comboSocio.setItems(new String[] {"RSSMRA19T54A000Z\t|\tROSSI MARIO", "RSSLCA21A78A000Q\t|\tROSSI LUCA", "BNCLGO68B80E111T\t|\tBIANCHI OLGA", "VRDNNA41C66S456W\t|\tVERDI ANNA", "DMALDA18D91A000A\t|\tADAMI ALDO"});
		comboSocio.setBounds(10, 10, 193, 23);
		comboSocio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cf = comboSocio.getText();
			}
		});

		dateInizio.setBounds(219, 10, 80, 24);
		
		dateFine.setBounds(305, 9, 80, 24);

		btnCerca.setBounds(465, 8, 75, 25);
		btnCerca.setText("Cerca");

		table.setBounds(10, 39, 530, 144);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tblCodiceNoleggio.setWidth(100);
		tblCodiceNoleggio.setText("codice noleggio");

		tblAuto.setWidth(60);
		tblAuto.setText("auto");

		tblSocio.setWidth(130);
		tblSocio.setText("socio");

		tblInizio.setWidth(75);
		tblInizio.setText("inizio");

		tblFine.setWidth(75);
		tblFine.setText("fine");

		tblAutoRestituita.setWidth(85);
		tblAutoRestituita.setText("auto restituita");
		
		btnCerca.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date dataI = null;
				Date dataF = null;
				
				try {
					dataI = df.parse(dateInizio.getYear() + "-" + dateInizio.getMonth() + "-" + dateInizio.getDay());
					dataF = df.parse(dateFine.getYear() + "-" + dateFine.getMonth() + "-" + dateFine.getDay());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				d.cercaNoleggio(dataI, dataF, cf);
			}
		});
	}
}
