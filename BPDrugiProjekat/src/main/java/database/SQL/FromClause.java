package database.SQL;

import java.util.LinkedList;
import java.util.List;

public class FromClause extends SQLClause {
    private Table table;
    private boolean hasJoins;
    private List<JoinCondition> joins;

    public FromClause(SQLQuery query) {
        super(query);
        this.hasJoins = false;
        this.joins = null;
    }

    public void addJoin(JoinCondition joinCondition) {
        if(!hasJoins) {
            joins = new LinkedList<>();
        }
        joins.add(joinCondition);
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public String toString() {
        String result = "FROM " + table.getTableName();
        if(hasJoins) {
            for (JoinCondition joinCondition : joins) {
                result += joinCondition.toString();
            }
        }
        return result;
    }
}
