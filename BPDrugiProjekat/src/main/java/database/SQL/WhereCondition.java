package database.SQL;

public abstract class WhereCondition {
    private Column conditionColumn;

    public WhereCondition(Column conditionColumn) {
        this.conditionColumn = conditionColumn;
    }

    public Column getConditionColumn() {
        return conditionColumn;
    }
}
