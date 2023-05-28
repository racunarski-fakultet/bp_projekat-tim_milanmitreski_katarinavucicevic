package database.SQL;

public class SubQueryCondition extends WhereCondition{
    private SQLQuery subQuery;

    public SubQueryCondition(Column conditionColumn, SQLQuery subQuery) {
        super(conditionColumn);
        this.subQuery = subQuery;
    }

    public SQLQuery getSubQuery() {
        return subQuery;
    }
}
