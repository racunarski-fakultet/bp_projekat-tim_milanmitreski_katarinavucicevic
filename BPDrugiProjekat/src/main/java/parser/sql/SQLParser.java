package parser.sql;

import database.SQL.SQLQuery;
import parser.Parser;

import java.util.Iterator;
import java.util.List;

public class SQLParser implements Parser {

    @Override
    public SQLQuery parse(String sQuery) {
        List<String> tokens = List.of(sQuery.split(" "));
        return null;
    }

    /*  GRAMATIKA ZA PREPOZNAVANJE SQL UPITA - Treba je osloboditi leve rekurzije i levo faktorisanih pravila
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
        Condition -> TABLECOLUMN BETWEEN ConditionElement AND ConditionElement
                   | TABLECOLUMN = ConditionElement
                   | TABLECOLUMN Relation ConditionElement
                   | TABLECOLUMN IN ConditionElement
                   | TABLECOLUMN NOT IN ConditionElement
                   | TABLECOLUMN LIKE ConditionElement
        ConditionElement -> (Query)
                         -> (Values)
                         -> NUMBER
                         -> STRING
        Relation -> >=
                  | >
                  | <=
                  | <
        LogicalOperator -> AND
                         | OR
        GroupColumnList -> TABLECOLUMN, GroupColumnList
                         | TABLECOLUMN
        OrderClause -> ORDER BY Column Option
                     | e
        Option -> ASC
                | DESC
     */

    private boolean check(List<String> tokens) {
        /*
         *  Ideja je napisati gramatiku koja prepoznaje SQL upite. Tokom prepoznavanja, ideja je da se formiraju delovi upita
         *  npr. SELECT department_id FROM hr.departments WHERE department_name LIKE 'S%' se rasclani na:
         *  SelectClause (List<Column> = [department_id])
         *  FromClause (Table = hr.departments; hasJoin = false; JoinTable = null; JoinCondition = null
         *  WhereClause (Condition = (Column=department_name; ConditionType=LIKE; Values = ['S%']))
         */
        Iterator<String> tokensIterator = tokens.iterator();
        while(tokensIterator.hasNext()) {
            // Implementacija sintaksnog analizatora
            tokensIterator.next();
        }
        return false;
    }
}
