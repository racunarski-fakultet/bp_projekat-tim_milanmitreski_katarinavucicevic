package database.SQL;

import data.Row;
import database.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SQLQuery implements Query {

    private Connection connection;
    private ResultSet rs;
    private List<SQLClause> clauses;

    public SQLQuery() {
        this.clauses = new LinkedList<>();
    }

    public void runQuery(String from){
                                                                              // objasnjenje za ovu i metodu ispod
        String query = "SELECT * FROM" + from;                                // query se pise isto (from ce biti departments u njenom primeru)
        try {                                                                 // resultset zivi samo dok je otvorena konekcija,
            rs = connection.createStatement().executeQuery(query);            // zato moramo da kopiramo redove iz resultseta (klasa row)
        } catch (SQLException e) {                                            // koje cemo prosledjivati JTable-u
            e.printStackTrace();
        }

    }


    public List<Row> saveResultSet(String fromTable) {      /// kad se uradi query, ovde se cuvaju ti (filtrirani) podaci koji ce se prikazati

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

    public void addClause(SQLClause clause) {
        this.clauses.add(clause);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(SQLClause clause : clauses) {
            result.append(clause.toString()).append(" ");
        }
        return result.toString().trim();
    }
}
