package carsharing;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Button;

public class EliminaAuto {

	protected Shell shlEliminaAuto;
	private Database d;
	private Table table;
	private ArrayList<Auto> auto = new ArrayList<Auto>();
	private String targa = "";

	public EliminaAuto(Database d) {
		this.d = d;
	}

	public EliminaAuto() {

	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EliminaAuto window = new EliminaAuto();
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
		shlEliminaAuto.open();
		shlEliminaAuto.layout();
		while (!shlEliminaAuto.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlEliminaAuto = new Shell();
		shlEliminaAuto.setSize(315, 385);
		shlEliminaAuto.setText("Elimina auto");
		
		Button btnRimuovi = new Button(shlEliminaAuto, SWT.NONE);
		table = new Table(shlEliminaAuto, SWT.BORDER | SWT.FULL_SELECTION);
		TableColumn tblclmnTarga = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnMarca = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnModello = new TableColumn(table, SWT.NONE);
		TableColumn tblclmnPrezzoGiornaliero = new TableColumn(table, SWT.NONE);
		Label lblAuto = new Label(shlEliminaAuto, SWT.NONE);
		
		btnRimuovi.setBounds(10, 10, 280, 25);
		btnRimuovi.setText("Rimuovi");
		btnRimuovi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(StringUtils.isBlank(targa)) {
					MessageBox messageBox = new MessageBox(shlEliminaAuto);
					messageBox.setMessage("Seleziona un'auto.");
					messageBox.setText("Alert");
					messageBox.open();
				} else {
					System.out.println(targa);
					d.rimuoviAuto(targa);
					targa = "";
				}
				shlEliminaAuto.close();
			}
		});
		
		table.setBounds(10, 41, 280, 105);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem item = table.getItem(table.getSelectionIndex());
				targa = item.getText(0);
				switch(targa) {
				case "AA222DS":
					lblAuto.setImage(SWTResourceManager.getImage("img\\fiat_500.jpg"));
					break;
					
				case "AB009FG":
					lblAuto.setImage(SWTResourceManager.getImage("img\\seat_ibiza.jpg"));
					break;
					
				case "BB333EE":
					lblAuto.setImage(SWTResourceManager.getImage("img\\ford_escape.jpg"));
					break;
					
				case "BC111KL":
					lblAuto.setImage(SWTResourceManager.getImage("img\\seat_leon.jpg"));
					break;
				}
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
		
		auto = d.listaAuto();
		for (int i = 0; i < auto.size(); i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, auto.get(i).getTarga());
			item.setText(1, auto.get(i).getMarca());
			item.setText(2, auto.get(i).getModello());
			item.setText(3, Double.toString(auto.get(i).getCosto_giornaliero()));
		}
		lblAuto.setBounds(10, 152, 280, 186);
	}
}
