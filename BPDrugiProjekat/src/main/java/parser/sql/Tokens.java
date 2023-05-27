package parser.sql;

public enum Tokens {
    /*
        Query -> SELECT ColumnList FROM Table Clauses
        ColumnList -> Column, ColumnList
                    | Column
        Column -> Aggregacy(TABLECOLUMN)
                | TABLECOLUMN
        Table -> TABLE Join
        Join -> JOIN TABLE JoinCondition
              | e
        JoinCondition -> ON (TABLECOLUMN=TABLECOLUMN)
                       | USING (TABLECOLUMN)
        Clauses -> WhereClause GroupClause OrderClause
        WhereClause -> WHERE ConditionList
                     | e
        GroupClause -> GROUP BY GroupColumnList HAVING ConditionList
                     | e
        ConditionList -> Condition LogicalOperator ConditionList
                       | Condition
        Condition -> TABLECOLUMN BETWEEN NUMBER AND NUMBER
                   | TABLECOLUMN = (Query)
                   | TABLECOLUMN RELATION NUMBER
                   | TABLECOLUMN LIKE STRING
        ArithmeticOperator -> >=
                            | >
                            | <=
                            | <
                            | =
        LogicalOperator -> AND
                         | OR
        GroupColumnList -> TABLECOLUMN, GroupColumnList
                         | TABLECOLUMN
        OrderClause -> ORDER BY Column Option
                     | e
        Option -> ASC
                | DESC
     */

    QUERY,
    COLUMN_LIST,
    TABLE,
    CLAUSES,
    AGGREGACY,
    TABLE_COLUMN,
    JOIN,
    JOIN_CONDITION,
    CONDITION,
    CONDITION_LIST,
    LOGICAL_OPERATOR,
    RELATION,
    WHERE_CLAUSE,
    GROUP_CLAUSE,
    ORDER_CLAUSE,
    GROUP_COLUMN_LIST,
    OPTION;
}
