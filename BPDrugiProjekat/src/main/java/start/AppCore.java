package start;

import database.Query;
import database.SQL.SQLQuery;
import database.mongo.MongoConnection;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.table.TableModel;
import utils.Constants;

public class AppCore {

    private TableModel tableModel;
    private MongoConnection mongoConnection;
    private Settings settings;
    private SQLQuery query;

    public AppCore() {
        this.tableModel = new TableModel();
        this.settings = initSettings();
        this.mongoConnection = new MongoConnection(this.settings);

    }

    private Settings initSettings(){
        Settings settingsImpl = new SettingsImplementation();
        settingsImpl.addParameter("134.209.239.154", Constants.IP);
        settingsImpl.addParameter("bp_tim_68", Constants.DATABASE);
        settingsImpl.addParameter("writer", Constants.USERNAME);
        settingsImpl.addParameter("ItjYs8WD4VajT56A", Constants.PASSWORD);
        return settingsImpl;
    }

    public void readDataFromTable(String fromTable){
        tableModel.setRows(query.saveResultSet(fromTable));
        //tableModel.setRows(this.dataBase.runBase(fromTable));
        // vraca rows (setrows pretvara u vektor)
    }

    public TableModel getTableModel() {
        return tableModel;
    }
}
