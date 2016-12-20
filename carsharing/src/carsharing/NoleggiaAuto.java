package carsharing;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;

public class NoleggiaAuto {

	protected Shell shlNoleggiaAuto;
	private String cf = "";
	private String dataInizio = "";
	private String dataFine = "";
	private Table table;

	public NoleggiaAuto(String cf, String dataInizio, String dataFine) {
		super();
		this.cf = cf;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}

	public NoleggiaAuto() {

	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			NoleggiaAuto window = new NoleggiaAuto();
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
		shlNoleggiaAuto.open();
		shlNoleggiaAuto.layout();
		while (!shlNoleggiaAuto.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlNoleggiaAuto = new Shell();
		shlNoleggiaAuto.setSize(315, 300);
		shlNoleggiaAuto.setText("Noleggia");

		Label lblSocio = new Label(shlNoleggiaAuto, SWT.NONE);
		Label lblDataInizio = new Label(shlNoleggiaAuto, SWT.NONE);
		Label lblDataFine = new Label(shlNoleggiaAuto, SWT.NONE);
		table = new Table(shlNoleggiaAuto, SWT.BORDER | SWT.FULL_SELECTION);
		TableColumn tblclmnTarga = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnMarca = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnModello = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnPrezzoGiornaliero = new TableColumn(table, SWT.NONE);
		
		lblSocio.setBounds(10, 10, 280, 15);
		lblSocio.setText("Socio: " + cf);
		
		lblDataInizio.setBounds(10, 31, 280, 15);
		lblDataInizio.setText("Data inizio: " + dataInizio);
		
		lblDataFine.setBounds(10, 52, 280, 15);
		lblDataFine.setText("Data fine:" + dataFine);

		table.setBounds(10, 94, 280, 140);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tblclmnTarga.setWidth(60);
		tblclmnTarga.setText("targa");

		tblclmnMarca.setWidth(45);
		tblclmnMarca.setText("marca");

		
		tblclmnModello.setWidth(60);
		tblclmnModello.setText("modello");

		
		tblclmnPrezzoGiornaliero.setWidth(110);
		tblclmnPrezzoGiornaliero.setText("prezzo giornaliero");

		TableItem tableItem1 = new TableItem(table, SWT.NONE);
		tableItem1.setText(0, "AB009FG");
		tableItem1.setText(1, "SEAT");
		tableItem1.setText(2, "IBIZA");
		tableItem1.setText(3, "25.00");

		TableItem tableItem2 = new TableItem(table, SWT.NONE);
		tableItem2.setText(0, "BC111KL");
		tableItem2.setText(1, "SEAT");
		tableItem2.setText(2, "LEON");
		tableItem2.setText(3, "30.00");

		TableItem tableItem3 = new TableItem(table, SWT.NONE);
		tableItem3.setText(0, "AA222DS");
		tableItem3.setText(1, "FIAT");
		tableItem3.setText(2, "500");
		tableItem3.setText(3, "27.00");

		TableItem tableItem4 = new TableItem(table, SWT.NONE);
		tableItem4.setText(0, "BB333EE");
		tableItem4.setText(1, "FORD");
		tableItem4.setText(2, "ESPACE");
		tableItem4.setText(3, "50.00");
		
		Label lblAutoDisponibili = new Label(shlNoleggiaAuto, SWT.NONE);
		lblAutoDisponibili.setBounds(10, 73, 280, 15);
		lblAutoDisponibili.setText("Auto Disponibili");

	}
}
