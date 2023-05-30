package database.SQL;

public class RelationCondition extends WGCondition{

    private RelationConditionType relationConditionType;
    private Object referenceValue;
    public RelationCondition(Column conditionColumn, String relationConditionType, Object referenceValue) {
        super(conditionColumn);
        this.referenceValue = referenceValue;
        this.relationConditionType = RelationConditionType.getRelationConditionType(relationConditionType);
    }
}
