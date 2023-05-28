package database.SQL;

public class FromClause extends SQLClause {
    private Table table;
    private boolean hasJoin;
    private Table joinTable;
    private JoinConditionType joinConditionType;
    private Column conditionColumn;
    private Column conditionColumnOn;

    public FromClause(SQLQuery query, Table table) {
        super(query);
        this.table = table;
        this.hasJoin = false;
        this.joinTable = null;
        this.joinConditionType = JoinConditionType.NONE;
        this.conditionColumn = null;
        this.conditionColumnOn = null;
    }

    public FromClause(SQLQuery query, Table table, Table joinTable, Column conditionColumn) {
        super(query);
        this.table = table;
        this.hasJoin = true;
        this.joinTable = joinTable;
        this.joinConditionType = JoinConditionType.USING;
        this.conditionColumn = conditionColumn;
        this.conditionColumnOn = null;
    }

    public FromClause(SQLQuery query, Table table, Table joinTable, Column conditionColumn, Column conditionColumnOn) {
        super(query);
        this.table = table;
        this.hasJoin = true;
        this.joinTable = joinTable;
        this.joinConditionType = JoinConditionType.ON;
        this.conditionColumn = conditionColumn;
        this.conditionColumnOn = conditionColumnOn;
    }

    public Table getTable() {
        return table;
    }

    public boolean isHasJoin() {
        return hasJoin;
    }

    public Table getJoinTable() {
        return joinTable;
    }

    public JoinConditionType getJoinConditionType() {
        return joinConditionType;
    }

    public Column getConditionColumn() {
        return conditionColumn;
    }

    public Column getConditionColumnOn() {
        return conditionColumnOn;
    }
}
