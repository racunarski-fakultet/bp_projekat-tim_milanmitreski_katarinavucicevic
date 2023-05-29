package database.SQL;

public class JoinCondition {
    private JoinConditionType joinConditionType;
    private Table joinTable;
    private Column conditionColumn;
    private Column conditionColumnOn;

    public JoinCondition(Table joinTable, Column conditionColumn) {
        this.joinConditionType = JoinConditionType.USING;
        this.joinTable = joinTable;
        this.conditionColumn = conditionColumn;
        this.conditionColumnOn = null;
    }

    public JoinCondition(Table joinTable, Column conditionColumn, Column conditionColumnOn) {
        this.joinConditionType = JoinConditionType.USING;
        this.joinTable = joinTable;
        this.conditionColumn = conditionColumn;
        this.conditionColumnOn = conditionColumnOn;
    }
}
