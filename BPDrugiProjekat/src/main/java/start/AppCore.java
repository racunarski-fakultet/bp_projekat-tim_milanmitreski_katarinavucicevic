package start;

import database.DataBase;
import database.MYSQLDatabase;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.table.TableModel;
import utils.Constants;

public class AppCore {

    private TableModel tableModel;
    private DataBase dataBase;
    private Settings settings;

    public AppCore() {
        this.tableModel = new TableModel();
        this.settings = initSettings();
        this.dataBase = new MYSQLDatabase(this.settings);

    }

    private Settings initSettings(){
        Settings settingsImpl = new SettingsImplementation();
        settingsImpl.addParameter("", Constants.MYSQL_IP);
        settingsImpl.addParameter("", Constants.MYSQL_DATABASE);
        settingsImpl.addParameter("", Constants.MYSQL_USERNAME);
        settingsImpl.addParameter("", Constants.MYSQL_PASSWORD);
        return settingsImpl;
    }

    public void readDataFromTable(String fromTable){
        tableModel.setRows(this.dataBase.getDataFromTable(fromTable));
    }

    public TableModel getTableModel() {
        return tableModel;
    }
}
