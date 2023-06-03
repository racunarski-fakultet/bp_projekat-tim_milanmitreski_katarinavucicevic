package database.SQL.condition;

import database.SQL.Column;

public class LikeCondition extends WGCondition{

    private Object referenceValue;

    public LikeCondition(Column conditionColumn, Object referenceValue) {
        super(conditionColumn);
        this.referenceValue = referenceValue;
    }

    public Object getReferenceValue() {
        return referenceValue;
    }
}
