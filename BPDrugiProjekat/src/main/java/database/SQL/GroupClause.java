package database.SQL;

import java.util.LinkedList;
import java.util.List;

public class GroupClause extends SQLClause{
    private List<Column> groupColumns;
    private boolean hasHaving;
    private List<WGCondition> conditionList;

    public GroupClause(SQLQuery query, boolean hasHaving) {
        super(query);
        this.groupColumns = new LinkedList<>();
        this.hasHaving = hasHaving;
        if(this.hasHaving)
            this.conditionList = new LinkedList<>();
        else
            this.conditionList = null;
    }

    public void addGroupColumn(Column column) {
        // OVDE TREBA GRESKA AKO KOLONA IMA FUNKCIJU AGREGACIJE
        this.groupColumns.add(column);
    }

    public void addCondition(WGCondition condition) {
        this.conditionList.add(condition);
    }
}
