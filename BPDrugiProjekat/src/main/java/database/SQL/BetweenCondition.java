package database.SQL;

public class BetweenCondition extends WhereCondition{
    private int leftBound;
    private int rightBound;

    public BetweenCondition(Column conditionColumn, int leftBound, int rightBound) {
        super(conditionColumn);
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    public int getLeftBound() {
        return leftBound;
    }

    public int getRightBound() {
        return rightBound;
    }
}
