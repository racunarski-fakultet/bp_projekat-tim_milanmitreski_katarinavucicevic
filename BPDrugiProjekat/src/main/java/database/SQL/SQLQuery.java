package database.SQL;

import data.Row;
import database.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLQuery implements Query {

    private Connection connection;
    private ResultSet rs;


    public void runQuery(String from){

        String query = "SELECT * FROM" + from;                                // query se pise isto (from ce biti departments u njenom primeru)
        try {                                                                 // resultset zivi samo dok je otvorena konekcija,
            rs = connection.createStatement().executeQuery(query);            // zato moramo da kopiramo redove iz resultseta (klasa row)
        } catch (SQLException e) {                                            // koje cemo prosledjivati JTable-u
            e.printStackTrace();
        }

    }


    public List<Row> saveResultSet(String fromTable) {

        runQuery(fromTable);

        List<Row> rows = new ArrayList<>();

        try {

            ResultSetMetaData resultSetMetaData = rs.getMetaData();  // podaci o podacima iz resultseta

            while(rs.next()){

                Row row = new Row();
                row.setName(fromTable);

                for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;

    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
