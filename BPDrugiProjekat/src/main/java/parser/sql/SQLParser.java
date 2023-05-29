package parser.sql;

import database.SQL.*;
import parser.Parser;

import java.util.List;
import java.util.ListIterator;

public class SQLParser implements Parser {

    @Override
    public SQLQuery parse(String sQuery) {
        String sQueryLowerCase = sQuery.toLowerCase();
        List<String> tokens = List.of(sQueryLowerCase.split("[\\s]+"));
        ListIterator<String> stringIterator = tokens.listIterator();
        SQLQuery query = new SQLQuery();
        String next;
        Tokens token;
        // Pocinjemo sa SELECT
        if(stringIterator.hasNext() && yylex(stringIterator.next()) == Tokens.SELECT) {
            SelectClause selectClause = new SelectClause(query);
            while(stringIterator.hasNext()) {
                next = stringIterator.next();
                token = yylex(next);
                if(token == Tokens.AGGREGATE) {
                    String[] aggregateFunction = next.split("[(,)]");
                    Column c = new Column(aggregateFunction[1],aggregateFunction[0]);
                    selectClause.addColumn(c);
                } else if(token == Tokens.TABLECOLUMN) {
                    Column c = new Column(next);
                    selectClause.addColumn(c);
                } else {
                    stringIterator.previous();
                    break;
                }
            }
        } else {
            // ERROR
        }

        if(stringIterator.hasNext() && yylex(stringIterator.next()) == Tokens.FROM) {
            next = stringIterator.next();
            token = yylex(next);
            FromClause fromClause = new FromClause(query);
            if(token == Tokens.TABLE) {
                Table table = new Table(next);
                fromClause.setTable(table);
            } else {
                // ERROR
            }
            while(stringIterator.hasNext() && yylex(stringIterator.next()) == Tokens.JOIN) {
                next = stringIterator.next();
                token = yylex(next);
                if(token == Tokens.TABLE) {
                    Table table = new Table(next);
                    next = stringIterator.next();
                    token = yylex(next);
                    if(token == Tokens.USING) {
                        next = stringIterator.next();
                        token = yylex(next);
                        if(token == Tokens.USING_CONDITION) {
                            JoinCondition joinCondition = new JoinCondition(table, new Column(next.replaceAll("[()]", "")));
                            fromClause.addJoin(joinCondition);
                        } else {
                            // ERROR
                        }
                    } else if(token == Tokens.ON) {
                        next = stringIterator.next();
                        token = yylex(next);
                        if(token == Tokens.ON_CONDITION) {
                            String[] columns = next.split("[(=)]");
                            Column conditionColumn = new Column(columns[0]);
                            Column conditionColumnOn = new Column(columns[1]);
                            JoinCondition joinCondition = new JoinCondition(table, conditionColumn, conditionColumnOn);
                            fromClause.addJoin(joinCondition);
                        } else {
                            // ERROR
                        }
                    } else {
                        // ERROR
                    }
                } else {
                    // ERROR
                }
            }
            stringIterator.previous();
        }

        if(stringIterator.hasNext() && yylex(stringIterator.next()) == Tokens.WHERE) {
            // WHERE
        }
        if(stringIterator.hasNext() && yylex(stringIterator.next()) == Tokens.GROUP) {
            // GROUP
        }
        if(stringIterator.hasNext() && yylex(stringIterator.next()) == Tokens.ORDER) {
            // ORDER
        }
        System.out.println(query);
        return query;
    }

    private Tokens yylex(String next) {
        String lex = next.replaceAll(",$","");
        if(lex.matches("select"))
            return Tokens.SELECT;
        else if(lex.matches("from"))
            return Tokens.FROM;
        else if(lex.matches("join"))
            return Tokens.JOIN;
        else if(lex.matches("on"))
            return Tokens.ON;
        else if(lex.matches("using"))
            return Tokens.USING;
        else if(lex.matches("where"))
            return Tokens.WHERE;
        else if(lex.matches("group"))
            return Tokens.GROUP;
        else if(lex.matches("by"))
            return Tokens.BY;
        else if(lex.matches("between"))
            return Tokens.BETWEEN;
        else if(lex.matches("and"))
            return Tokens.AND;
        else if(lex.matches("or"))
            return Tokens.OR;
        else if(lex.matches("in"))
            return Tokens.IN;
        else if(lex.matches("not"))
            return Tokens.NOT;
        else if(lex.matches("like"))
            return Tokens.LIKE;
        else if(lex.matches("[0-9]+"))
            return Tokens.NUMBER;
        else if(lex.matches("'[^']+'"))
            return Tokens.STRING;
        else if(lex.matches("\\([0-9]+(,[0-9]+)*\\)") || lex.matches("\\('[^']+'(,'[^']+')*\\)"))
            return Tokens.LIST;
        else if(lex.matches("\\([a-z_]+\\)") || lex.matches("\\([a-z_]+\\.[a-z_]+\\.[a-z_]+\\)"))
            return Tokens.USING_CONDITION;
        else if(lex.matches("\\([a-z_]+=[a-z_]+\\)") || lex.matches("\\([a-z_]+\\.[a-z_]+\\.[a-z_]+=[a-z_]+\\.[a-z_]+\\.[a-z_]+\\)"))
            return Tokens.ON_CONDITION;
        else if(lex.matches(">"))
            return Tokens.GREATER;
        else if(lex.matches(">="))
            return Tokens.GREATER_EQUAL;
        else if(lex.matches("<"))
            return Tokens.LESS;
        else if(lex.matches("<="))
            return Tokens.LESS_EQUAL;
        else if(lex.matches("="))
            return Tokens.EQUALS;
        else if(lex.matches("having"))
            return Tokens.HAVING;
        else if(lex.matches("order"))
            return Tokens.ORDER;
        else if(lex.matches("asc"))
            return Tokens.ASC;
        else if(lex.matches("desc"))
            return Tokens.DESC;
        else if(lex.matches("[a-z_]+\\([a-z_]+\\)"))
            return Tokens.AGGREGATE;
        else if(lex.matches("[a-z_\\*]+") || lex.matches("[a-z_]+\\.[a-z_]+\\.[a-z_]+"))
            return Tokens.TABLECOLUMN;
        else if(lex.matches("[a-z_]+\\.[a-z_]+"))
            return Tokens.TABLE;
        // ERROR
        return null;
    }
}
