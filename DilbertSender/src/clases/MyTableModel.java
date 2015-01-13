package clases;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private List<String> columnNames = new ArrayList<String>();
	private List<List> data = new ArrayList();
	
	{
		columnNames.add("Nombre");
		columnNames.add("Email");
		columnNames.add("¿Mandar?");
	}

	public int getColumnCount() {
		return columnNames.size();
	}

	public int getRowCount() {
		return data.size();
	}
	
	public String getcolumnName(int col){
		return columnNames.get(col);
	}

	public Object getValueAt(int fila, int col) {
		return data.get(fila).get(col);
	}
	
	public void setValueAt(Object value, int row, int col){
		data.get(row).set(col, value);
		fireTableCellUpdated(row, col);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c){
		return getValueAt(0, c).getClass();
	}
	
	public boolean isCellEditable(int row, int col){
		if (col==2) return true;
		else return false;
	}
	
	public void addRow(List datos){
		data.add(datos);
		fireTableRowsInserted(data.size()-1,data.size()-1);
	}

	public List<List> getData() {
		return data;
	}

	public void setData(List<List> data) {
		this.data = data;
	}

}
