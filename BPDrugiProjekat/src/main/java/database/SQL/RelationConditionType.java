package database.SQL;

public enum RelationConditionType {
    G,
    GE,
    L,
    LE,
    E;

    public static RelationConditionType getRelationConditionType(String relationConditionType) {
        if(relationConditionType.equalsIgnoreCase(">"))
            return G;
        else if(relationConditionType.equalsIgnoreCase(">="))
            return GE;
        else if(relationConditionType.equalsIgnoreCase("<"))
            return L;
        else if(relationConditionType.equalsIgnoreCase("<="))
            return LE;
        else if(relationConditionType.equalsIgnoreCase("="))
            return E;
        else
            return null;
    }

    @Override
    public String toString() {
        switch (this) {
            case E:
                return "=";
            case G:
                return ">";
            case GE:
                return ">=";
            case LE:
                return "<=";
            case L:
                return "<";
            default:
                return null;
        }
    }
}
