package database.SQL;

public class OrderClause extends SQLClause {

    private Column orderColumn;
    private OrderType orderType;
    public OrderClause(SQLQuery query, Column orderColumn, OrderType orderType) {
        super(query);
        this.orderColumn = orderColumn;
        this.orderType = orderType;
    }
}
