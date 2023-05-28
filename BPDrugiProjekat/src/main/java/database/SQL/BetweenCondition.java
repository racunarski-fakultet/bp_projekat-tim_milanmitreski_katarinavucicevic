package database.SQL;

import java.util.concurrent.locks.Condition;

public class BetweenCondition extends WGCondition {
    private Object leftBound;
    private Object rightBound;

    public BetweenCondition(Column conditionColumn, Object leftBound, Object rightBound) {
        super(conditionColumn);
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public Object getLeftBound() {
        return leftBound;
    }

    public Object getRightBound() {
        return rightBound;
    }
}
