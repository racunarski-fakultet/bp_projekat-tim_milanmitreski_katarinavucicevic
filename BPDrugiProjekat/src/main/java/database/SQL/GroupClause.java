package database.SQL;

import java.util.LinkedList;
import java.util.List;

public class GroupClause extends SQLClause{
    private List<Column> groupColumns;
    private boolean hasHaving;
    private List<WGCondition> conditionList;
    private List<LogicalOperator> logicalOperators;

    public GroupClause(SQLQuery query) {
        super(query);
        this.groupColumns = new LinkedList<>();
        this.conditionList = new LinkedList<>();
        this.logicalOperators = new LinkedList<>();
    }

    public void addGroupColumn(Column column) {
        this.groupColumns.add(column);
    }
    public void addCondition(WGCondition condition) {
        this.conditionList.add(condition);
    }

    public void addLogicalOperator(String logicalOperator) {this.logicalOperators.add(LogicalOperator.getLogicalOperator(logicalOperator)); }

    public void setHasHaving(boolean hasHaving) {
        this.hasHaving = hasHaving;
    }
}
