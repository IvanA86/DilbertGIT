package gui;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import metodosAuxiliares.CRUDPersona;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import clases.MyTableModel;
import clases.Persona;

import com.toedter.calendar.JDateChooser;

public class PantallaPrincipal {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaPrincipal window = new PantallaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PantallaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblSeleccioneLaFecha = new JLabel("Seleccione la fecha");
		lblSeleccioneLaFecha.setBounds(10, 11, 91, 20);
		frame.getContentPane().add(lblSeleccioneLaFecha);

		final JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(111, 11, 95, 20);
		dateChooser.setDate(Calendar.getInstance().getTime());
		frame.getContentPane().add(dateChooser);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Date aux = dateChooser.getDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String aux2 = sdf.format(aux);
				mandarMail(aux2);
			}
		});
		btnEnviar.setBounds(10, 234, 91, 20);
		frame.getContentPane().add(btnEnviar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 414, 181);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		MyTableModel model = new MyTableModel();
		List<List> data = model.getData();
		data = leer();
		model.setData(data);
		table.setModel(model);
		TableColumnModel tcm = table.getColumnModel();
		JTableHeader th = table.getTableHeader();
		TableColumn tc = tcm.getColumn(0);
		tc.setHeaderValue("Nombre");
		tc = tcm.getColumn(1);
		tc.setHeaderValue("E-Mail");
		tc = tcm.getColumn(2);
		tc.setHeaderValue("¿Mandar?");
		th.repaint();
		scrollPane.setViewportView(table);

		JButton btnAddPersona = new JButton("A\u00F1adir Persona");
		btnAddPersona.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				mostrarDialogo();
			}
		});
		btnAddPersona.setBounds(111, 233, 137, 23);
		frame.getContentPane().add(btnAddPersona);
	}

	protected void mandarMail(String fecha) {
		String url = "http://www.dilbert.com/"+fecha;
		String id = "strip_enlarged_"+fecha;
		
		try {
			Document doc = Jsoup.connect(url).get();
			Element img = doc.getElementById(id);
			String link = img.attr("src");
			System.out.println(link);
		}
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	protected void mostrarDialogo() {
		JTextField name = new JTextField();
		JTextField email = new JTextField();
		JCheckBox mandar = new JCheckBox();

		Object[] message = { "Nombre: ", name, "Email: ", email, "¿Mandar?",
				mandar };

		int option = JOptionPane.showConfirmDialog(null, message,
				"Nueva Persona", JOptionPane.OK_CANCEL_OPTION);

		ArrayList<Persona> personas = new ArrayList<Persona>();
		if (option == JOptionPane.OK_OPTION) {
			Persona p = new Persona(name.getText(), email.getText(),
					mandar.isSelected());
			personas = CRUDPersona.addPersona(p);
			actualizaTabla(table, p);
		}
	}

	@SuppressWarnings("unchecked")
	private void actualizaTabla(JTable tabla, Persona p) {
		MyTableModel dtm = (MyTableModel) tabla.getModel();
		@SuppressWarnings("rawtypes")
		List data = new ArrayList<List>();

		data = Arrays.asList(p.getNombre(), p.getEmail(), p.isMandar());
		dtm.addRow(data);
		tabla.setModel(dtm);
	}

	@SuppressWarnings("unchecked")
	private List<List> leer() {

		final String NOM_FICHERO = "personas.dse";

		List<List> data = new ArrayList<List>();
		ArrayList<Persona> aux = new ArrayList<Persona>();

		try {
			InputStream file = new FileInputStream(NOM_FICHERO);
			InputStream buffer = new BufferedInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(buffer);

			aux = (ArrayList<Persona>) ois.readObject();

			ois.close();
			buffer.close();
			file.close();

			for (int i = 0; i < aux.size(); i++) {
				Persona p = (Persona) aux.get(i);
				List data_aux = new ArrayList();
				data_aux = Arrays.asList(p.getNombre(), p.getEmail(),
						p.isMandar());
				data.add(data_aux);
			}
		} catch (Exception e) {
			System.out.println("Error leyendo el fichero.\n"
					+ e.getLocalizedMessage());
		}
		return data;
	}
}
