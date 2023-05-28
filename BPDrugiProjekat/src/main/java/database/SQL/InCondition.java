package database.SQL;

import java.util.ArrayList;
import java.util.List;

public class InCondition extends WhereCondition{
    public List<Object> values;
    public boolean isNot;
    public InCondition(Column conditionColumn, boolean isNot) {
        super(conditionColumn);
        this.isNot = isNot;
        this.values = new ArrayList<>();
    }

    public boolean isNot() {
        return isNot;
    }

    public List<Object> getValues() {
        return values;
    }
}
