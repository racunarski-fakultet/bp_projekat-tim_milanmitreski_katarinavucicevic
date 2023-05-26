package database;

import data.Row;
import database.settings.Settings;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MYSQLDatabase implements DataBase{

    private Settings settings;
    private Connection connection;

    public MYSQLDatabase(Settings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException {
        String ip = (String) settings.getParameter("");
        String database = (String) settings.getParameter("");
        String username = (String) settings.getParameter("");
        String password = (String) settings.getParameter("");
        connection = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+database,username,password);
    }

    private void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = null;
        }
    }

    @Override
    public List<Row> getDataFromTable(String from) {

        List<Row> rows = new ArrayList<>();

        try {
            this.initConnection();

            String query = "SELECT * FROM" + from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            while(rs.next()){

                Row row = new Row();
                row.setName(from);

                for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }

        return rows;
    }
}
