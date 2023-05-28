package database.SQL;

public class Column {
    private String columnName;
    private boolean isAggregate;
    private AggregateFunction aggregateFunction;

    public Column(String columnName) {
        this.columnName = columnName;
        this.isAggregate = false;
        this.aggregateFunction = AggregateFunction.NONE;
    }

    public Column(String columnName, AggregateFunction aggregateFunction) {
        this.columnName = columnName;
        this.isAggregate = true;
        this.aggregateFunction = aggregateFunction;
    }

    public boolean isAggregate() {
        return isAggregate;
    }

    public String getColumnName() {
        return columnName;
    }

    public AggregateFunction getAggregateFunction() {
        return aggregateFunction;
    }
}
