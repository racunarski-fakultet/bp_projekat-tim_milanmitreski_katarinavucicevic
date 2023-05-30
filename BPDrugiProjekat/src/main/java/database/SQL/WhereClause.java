package database.SQL;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WhereClause extends SQLClause {
    private List<WGCondition> conditionList;
    private List<LogicalOperator> logicalOperators;
    public WhereClause(SQLQuery query) {
        super(query);
        this.conditionList = new LinkedList<>();
        this.logicalOperators = new LinkedList<>();
    }

    public void addCondition(WGCondition condition) {
        this.conditionList.add(condition);
    }

    public void addLogicalOperator(String logicalOperator) {this.logicalOperators.add(LogicalOperator.getLogicalOperator(logicalOperator)); }
}
