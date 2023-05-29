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
        // Pocinjemo sa SELECT
        if(stringIterator.hasNext() && stringIterator.next().equals("select")) {
            SelectClause selectClause = new SelectClause(query);
            while(stringIterator.hasNext()) {
                String next = stringIterator.next();
                next = next.replaceAll(",", "");
                if(next.matches("[a-z_]+\\([a-z_]+\\)")) {
                    String[] aggregateFunction = next.split("[(,)]");
                    Column c = new Column(aggregateFunction[1],aggregateFunction[0]);
                    selectClause.addColumn(c);
                } else if(next.matches("[a-z_]+") || next.matches("[a-z_]+\\.[a-z_]+\\.[a-z_]+")) {
                    Column c = new Column(next);
                    selectClause.addColumn(c);
                } else {
                    stringIterator.previous();
                    break;
                }
            }
        } else {
            System.out.println("ERROR");
        }

        if(stringIterator.hasNext() && stringIterator.next().equals("from")) {
            String next = stringIterator.next();
            FromClause fromClause = new FromClause(query);
            if(next.matches("[a-z_]+\\.[a-z_]+")) {
                Table table = new Table(next);
                fromClause.setTable(table);
            } else {
                System.out.println("ERROR");
            }
            while(stringIterator.hasNext() && stringIterator.next().matches("join")) {
                next = stringIterator.next();
                if(next.matches("[a-z_]+\\.[a-z_]+")) {
                    Table table = new Table(next);
                    next = stringIterator.next();
                    if(next.equals("using")) {
                        next = stringIterator.next();
                        if(next.matches("\\([a-z_]+\\)") || next.matches("\\([a-z_]+\\.[a-z_]+\\.[a-z_]+\\)")) {
                            JoinCondition joinCondition = new JoinCondition(table, new Column(next.replaceAll("[()]", "")));
                            fromClause.addJoin(joinCondition);
                        } else {
                            // ERROR
                        }
                    } else if(next.equals("on")) {
                        next = stringIterator.next();
                        if(next.matches("\\([a-z_]+=[a-z_]+\\)") || next.matches("\\([a-z_]+\\.[a-z_]+\\.[a-z_]+=[a-z_]+\\.[a-z_]+\\.[a-z_]+\\)")) {
                            String[] columns = next.split("[(=)]");
                            Column conditionColumn = new Column(columns[0]);
                            Column conditionColumnOn = new Column(columns[1]);
                            JoinCondition joinCondition = new JoinCondition(table, conditionColumn, conditionColumnOn);
                            fromClause.addJoin(joinCondition);
                        } else {
                            System.out.println("ERROR");
                        }
                    } else {
                        System.out.println("ERROR");
                    }
                } else {
                    System.out.println("ERROR");
                }
            }
            stringIterator.previous();
        }

        if(stringIterator.hasNext() && stringIterator.next().equals("where")) {
            // WHERE
        } else { stringIterator.previous(); }
        if(stringIterator.hasNext() && stringIterator.next().equals("group")) {
            // GROUP
        } { stringIterator.previous(); }
        if(stringIterator.hasNext() && stringIterator.next().equals("order")) {
            if(stringIterator.next().equals("by")) {
                String next = stringIterator.next();
                if(next.matches("[a-z_]+") || next.matches("[a-z_]+\\.[a-z_]+\\.[a-z_]+")) {
                    Column column = new Column(next);
                    next = stringIterator.next();
                    if(next.equals("asc") || next.equals("desc")) {
                        OrderClause orderClause = new OrderClause(query, column, next);
                    } else {
                        System.out.println("ERROR");
                    }
                } else {
                    System.out.println("ERROR");
                }
            } else {
                System.out.println("ERROR");
            }
        }
        System.out.println("SUCCESS");
        return query;
    }
        /*

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
        else if(lex.matches("[a-z_]+\\([a-z_]+\\)"))
            return Tokens.AGGREGATE;
        else if(lex.matches("[a-z_\\*]+") || lex.matches("[a-z_]+\\.[a-z_]+\\.[a-z_]+"))
            return Tokens.TABLECOLUMN;
        else if(lex.matches("[a-z_]+\\.[a-z_]+"))
            return Tokens.TABLE;
         */
}
