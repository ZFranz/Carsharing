package carsharing;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class Carsharing {

	protected Shell shlCarsharing;
	private Table table;

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

		Combo combo = new Combo(shlCarsharing, SWT.NONE);
		DateTime dateTime = new DateTime(shlCarsharing, SWT.BORDER);
		Button btnCerca = new Button(shlCarsharing, SWT.NONE);
		table = new Table(shlCarsharing, SWT.BORDER | SWT.FULL_SELECTION);
		TableColumn tblCodiceNoleggio = new TableColumn(table, SWT.NONE);
		TableColumn tblAuto = new TableColumn(table, SWT.NONE);
		TableColumn tblSocio = new TableColumn(table, SWT.NONE);
		TableColumn tblInizio = new TableColumn(table, SWT.NONE);
		TableColumn tblFine = new TableColumn(table, SWT.NONE);
		TableColumn tblAutoRestituita = new TableColumn(table, SWT.NONE);

		combo.setItems(new String[] { "RSSMRA19T54A000Z", "RSSLCA21A78A000Q", "BNCLGO68B80E111T", "VRDNNA41C66S456W",
				"DMALDA18D91A000A" });
		combo.setBounds(10, 10, 193, 23);

		dateTime.setBounds(219, 10, 80, 24);

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
	}
}
