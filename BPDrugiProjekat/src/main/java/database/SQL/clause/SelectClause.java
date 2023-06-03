package database.SQL.clause;

import database.SQL.Column;
import database.SQL.SQLQuery;

import java.util.LinkedList;
import java.util.List;

public class SelectClause extends SQLClause {
    private List<Column> columns;

    public SelectClause(SQLQuery query) {
        super(query);
        this.columns = new LinkedList<>();
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public List<Column> getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        String result = "SELECT ";
        for(Column column: columns) {
            result += column.getColumnName() + ", ";
        }
        result = result.trim();
        result = result.replaceAll(",$", "");
        return result;
    }
}
