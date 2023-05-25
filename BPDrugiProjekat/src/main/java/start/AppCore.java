package start;

import database.DataBase;
import gui.table.TableModel;

public class AppCore {

    private TableModel tableModel;
    private DataBase dataBase;

    public AppCore() {
        this.tableModel = new TableModel();
    }

    public void readDataFromTable(String fromTable){
        tableModel.setRows(this.dataBase.getDataFromTable(fromTable));
    }

    public TableModel getTableModel() {
        return tableModel;
    }
}
