package database.SQL.clause;

import database.SQL.Column;
import database.SQL.OrderType;
import database.SQL.SQLQuery;

public class OrderClause extends SQLClause {

    private Column orderColumn;
    private OrderType orderType;
    public OrderClause(SQLQuery query, Column orderColumn, String orderType) {
        super(query);
        this.orderColumn = orderColumn;
        this.orderType = OrderType.getOrderType(orderType);
    }

    @Override
    public String toString() {
        return "ORDER BY " + orderColumn.getColumnName() + " " + orderType.name();
    }

    public Column getOrderColumn() {
        return orderColumn;
    }

    public OrderType getOrderType() {
        return orderType;
    }
}
