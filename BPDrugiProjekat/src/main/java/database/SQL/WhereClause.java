package database.SQL;

import java.util.ArrayList;
import java.util.List;

public class WhereClause extends SQLClause {
    private List<WhereCondition> conditionList;
    public WhereClause(SQLQuery query) {
        super(query);
        this.conditionList = new ArrayList<>();
    }

    public void addCondition(WhereCondition condition) {
        this.conditionList.add(condition);
    }
}
