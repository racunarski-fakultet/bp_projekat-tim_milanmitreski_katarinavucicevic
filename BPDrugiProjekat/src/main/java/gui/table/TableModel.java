package gui.table;

import data.Row;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class TableModel extends DefaultTableModel {

    /// konvertuje redove u vektore, kako bi bio kompatibilan sa JTable
    private List<Row> rows;

    private void updateModel(){

        Vector columnVector = new Vector(rows.get(0).getFields().keySet());
        Vector dataVector = new Vector();

        for (Row row : rows) {
            Vector rowVector = new Vector();
            for (Object value : row.getFields().values()) {
                rowVector.add(value);
            }
            dataVector.add(rowVector);
        }

        setDataVector(dataVector, columnVector);
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        updateModel();
    }
}
