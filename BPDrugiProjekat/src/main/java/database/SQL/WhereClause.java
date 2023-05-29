package database.SQL;

import java.util.ArrayList;
import java.util.List;

public class WhereClause extends SQLClause {
    private List<WGCondition> conditionList;
    public WhereClause(SQLQuery query) {
        super(query);
        this.conditionList = new ArrayList<>();
    }

    public void addCondition(WGCondition condition) {
        this.conditionList.add(condition);
    }
}
