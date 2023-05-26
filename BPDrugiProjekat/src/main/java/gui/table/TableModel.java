package gui.table;

import data.Row;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class TableModel extends DefaultTableModel {

    /// konvertuje redove u vektore, kako bi bio kompatibilan sa JTable
    private List<Row> rows;

    private void updateModel(){

        int columnCount = rows.get(0).getFields().keySet().size();  // koliko kolona imamo
                                                                    // keyset - nazivi kolona

        Vector columnVector = DefaultTableModel.convertToVector(rows.get(0).getFields().keySet().toArray());
        Vector dataVector = new Vector(columnCount);

        for(int i = 0; i < rows.size(); i++){
            dataVector.add(DefaultTableModel.convertToVector(rows.get(i).getFields().keySet().toArray()));
        }

        setDataVector(dataVector, columnVector);
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        updateModel();
    }
}
