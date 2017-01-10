package carsharing;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class NoleggiaAuto {

	protected Shell shlNoleggiaAuto;
	private String cf = "";
	private String dataInizio = "";
	private String dataFine = "";
	private ArrayList<Auto> auto = new ArrayList<Auto>();
	private Table table;
	private String targa = "";
	private Database d;
	private Boolean controllo;

	public NoleggiaAuto(String cf, String dataInizio, String dataFine, ArrayList<Auto> auto, Database d) {
		super();
		this.cf = cf;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.auto = auto;
		this.d = d;
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
		shlNoleggiaAuto.setSize(315, 275);
		shlNoleggiaAuto.setText("Noleggia");

		Label lblSocio = new Label(shlNoleggiaAuto, SWT.NONE);
		Label lblDataInizio = new Label(shlNoleggiaAuto, SWT.NONE);
		Label lblDataFine = new Label(shlNoleggiaAuto, SWT.NONE);
		Label lblAutoDisponibili = new Label(shlNoleggiaAuto, SWT.NONE);
		table = new Table(shlNoleggiaAuto, SWT.BORDER | SWT.FULL_SELECTION);
		TableColumn tblclmnTarga = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnMarca = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnModello = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnPrezzoGiornaliero = new TableColumn(table, SWT.NONE);
		Button btnNoleggia = new Button(shlNoleggiaAuto, SWT.NONE);

		lblSocio.setBounds(10, 10, 280, 15);
		lblSocio.setText("Socio: " + cf);

		lblDataInizio.setBounds(10, 31, 280, 15);
		lblDataInizio.setText("Data inizio:  " + dataInizio);

		lblDataFine.setBounds(10, 52, 280, 15);
		lblDataFine.setText("Data fine:  " + dataFine);

		lblAutoDisponibili.setBounds(10, 73, 280, 15);
		lblAutoDisponibili.setText("Auto Disponibili");

		table.setBounds(10, 94, 280, 105);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem item = table.getItem(table.getSelectionIndex());
				targa = item.getText(0);
				System.out.println(targa);
			}
		});

		tblclmnTarga.setWidth(60);
		tblclmnTarga.setText("targa");

		tblclmnMarca.setWidth(45);
		tblclmnMarca.setText("marca");

		tblclmnModello.setWidth(60);
		tblclmnModello.setText("modello");

		tblclmnPrezzoGiornaliero.setWidth(110);
		tblclmnPrezzoGiornaliero.setText("prezzo giornaliero");
		
		for (int i = 0; i < auto.size(); i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, auto.get(i).getTarga());
			item.setText(1, auto.get(i).getMarca());
			item.setText(2, auto.get(i).getModello());
			item.setText(3, Double.toString(auto.get(i).getCosto_giornaliero()));
		}
		
		btnNoleggia.setBounds(10, 205, 280, 25);
		btnNoleggia.setText("Noleggia");
		btnNoleggia.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				controllo = d.controllaDate(targa, dataFine);
				//d.nuovoNoleggio(targa, cf, dataInizio, dataFine);
				shlNoleggiaAuto.close();
			}
		});
	}
}
