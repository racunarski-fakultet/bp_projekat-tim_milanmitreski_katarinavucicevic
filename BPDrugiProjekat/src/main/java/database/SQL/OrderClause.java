package database.SQL;

public class OrderClause extends SQLClause {

    private Column orderColumn;
    private OrderType orderType;
    public OrderClause(SQLQuery query, Column orderColumn, String orderType) {
        super(query);
        this.orderColumn = orderColumn;
        this.orderType = OrderType.getOrderType(orderType);
    }
}
