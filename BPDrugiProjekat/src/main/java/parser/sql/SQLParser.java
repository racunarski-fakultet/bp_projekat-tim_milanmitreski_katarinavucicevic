package parser.sql;

import database.SQL.*;
import database.SQL.clause.*;
import database.SQL.condition.*;
import gui.MainFrame;
import observer.IPublisher;
import observer.ISubscriber;
import parser.Parser;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SQLParser implements Parser {

    private List<ISubscriber> subs;

    public SQLParser() {
        this.subs = new LinkedList<>();
    }

    /** parser je implementiran pre smanjenja funkcionalnosti
     ** parser uspesno parsira i podupite koji nisu u WHERE itd.
     **/

    public SQLQuery parse(String sQuery) {
        String sQueryLowerCase = sQuery.toLowerCase();
        List<String> tokens = List.of(sQueryLowerCase.split(" +"));
        ListIterator<String> listIterator = tokens.listIterator();
        SQLQuery sqlQuery = new SQLQuery();
        boolean hasSelect = false;
        boolean hasFrom = false;
        while (listIterator.hasNext()){
            String token = listIterator.next();
            switch (token) {
                case "select":
                    generateSelectClause(listIterator, sqlQuery);
                    hasSelect = true;
                    break;
                case "from":
                    if (!hasSelect) error("SELECT clause not found");
                    generateFromClause(listIterator, sqlQuery);
                    hasFrom = true;
                    break;
                case "where":
                    if (!hasFrom) error("FROM clause not found");
                    generateWhereClause(listIterator, sqlQuery);
                    break;
                case "group":
                    if (!hasFrom) error("FROM clause not found");
                    generateGroupClause(listIterator, sqlQuery);
                    break;
                case "order":
                    if (!hasFrom) error("FROM clause not found");
                    generateOrderClause(listIterator, sqlQuery);
                    break;
                default:
                    error("Valid clause not found");
                    break;
            }
        }
        notify(sqlQuery);
        return sqlQuery;
    }

    private void generateSelectClause(ListIterator<String> listIterator, SQLQuery sqlQuery) {
        SelectClause selectClause = new SelectClause(sqlQuery);
        boolean validSelect = false;
        while (listIterator.hasNext()) {
            String next = listIterator.next();
            next = next.replaceAll(",", "");
            boolean isKeyword = SQLKeyword.checkKeyword(next);
            if (next.matches("[a-z_]+\\([a-z_]+\\)")) {
                String[] aggregateFunction = next.split("[(,)]");
                Column c = new Column(aggregateFunction[1], aggregateFunction[0]);
                selectClause.addColumn(c);
            } else if (!isKeyword && (next.matches("[a-z_*]+") || next.matches("[a-z_]+\\.[a-z_]+\\.[a-z_]+"))) {
                Column c = new Column(next);
                selectClause.addColumn(c);
                if(next.equals("*")) {
                    if(!validSelect) {
                        validSelect = true;
                        break;
                    } else error("SELECT contains something besides *");
                }
            } else {
                listIterator.previous();
                break;
            }
            validSelect = true;
        }
        if(!validSelect) error("SELECT clause not valid");
    }

    private void generateFromClause(ListIterator<String> listIterator, SQLQuery sqlQuery) {
        if(!listIterator.hasNext()) error("FROM clause not valid");
        String next = listIterator.next();
        FromClause fromClause = new FromClause(sqlQuery);
        if (next.matches("[a-z_]+\\.[a-z_]+")) {
            Table table = new Table(next);
            fromClause.setTable(table);
        } else error("FROM not followed by TABLE");
        while (listIterator.hasNext()) {
            if(!listIterator.next().matches("join")) {
                listIterator.previous();
                break;
            }
            if(!listIterator.hasNext()) error("FROM clause not valid");
            next = listIterator.next();
            if (next.matches("[a-z_]+\\.[a-z_]+")) {
                Table table = new Table(next);
                if(!listIterator.hasNext()) error("FROM clause not valid");
                next = listIterator.next();
                if (next.equals("using")) {
                    if(!listIterator.hasNext()) error("FROM clause not valid");
                    next = listIterator.next();
                    if (next.matches("\\([a-z_]+\\)") || next.matches("\\([a-z_]+\\.[a-z_]+\\.[a-z_]+\\)")) {
                        JoinCondition joinCondition = new JoinCondition(table, new Column(next.replaceAll("[()]", "")));
                        fromClause.addJoin(joinCondition);
                    } else error("USING not followed by valid condition");
                } else if (next.equals("on")) {
                    if(!listIterator.hasNext()) error("FROM clause not valid");
                    next = listIterator.next();
                    if (next.matches("\\([a-z_]+=[a-z_]+\\)") || next.matches("\\([a-z_]+\\.[a-z_]+\\.[a-z_]+=[a-z_]+\\.[a-z_]+\\.[a-z_]+\\)")) {
                        String[] columns = next.split("[(=)]");
                        Column conditionColumn = new Column(columns[0]);
                        Column conditionColumnOn = new Column(columns[1]);
                        JoinCondition joinCondition = new JoinCondition(table, conditionColumn, conditionColumnOn);
                        fromClause.addJoin(joinCondition);
                    } else error("ON not followed by valid condition");
                } else error("JOIN not followed by valid condition (ON/USING)");
            } else error("JOIN not followed by valid TABLE name");
        }
    }
    private void generateWhereClause(ListIterator<String> listIterator, SQLQuery sqlQuery) {
        WhereClause whereClause = new WhereClause(sqlQuery);
        boolean validCondition = false;
        while(listIterator.hasNext()) {
            String next = listIterator.next();
            boolean isKeyword = SQLKeyword.checkKeyword(next);
            if (!isKeyword && (next.matches("[a-z_]+") || next.matches("[a-z_]+\\.[a-z_]+\\.[a-z_]+"))) {
                Column c = new Column(next);
                if (!listIterator.hasNext()) error("WHERE clause not valid");
                next = listIterator.next();
                if (next.equals("between")) {
                    if (!listIterator.hasNext()) error("WHERE clause not valid");
                    next = listIterator.next();
                    Object o1 = new Object();
                    Object o2 = new Object();
                    if (next.equals("(select")) {
                        String ssubQuery = locateSubQuery(listIterator);
                        o1 = parse(ssubQuery);
                    } else if (next.matches("[0-9]+")) {
                        o1 = Integer.valueOf(next);
                    } else error("BETWEEN doesn't have valid argument");
                    if (!listIterator.hasNext() || !listIterator.next().equals("and")) error("BETWEEN doesn't contain AND");
                    if (!listIterator.hasNext()) error("WHERE clause not valid");
                    next = listIterator.next();
                    if (next.equals("(select")) {
                        String ssubQuery = locateSubQuery(listIterator);
                        o2 = parse(ssubQuery);
                    } else if (next.matches("[0-9]+")) {
                        o2 = Integer.valueOf(next);
                    } else error("BETWEEN doesn't have valid argument");
                    BetweenCondition betweenCondition = new BetweenCondition(c, o1, o2);
                    whereClause.addCondition(betweenCondition);
                } else if (next.equals("like")) {
                    if (!listIterator.hasNext()) error("WHERE clause not valid");
                    next = listIterator.next();
                    if (next.equals("(select")) {
                        String ssubQuery = locateSubQuery(listIterator);
                        SQLQuery subQuery = parse(ssubQuery);
                        LikeCondition likeCondition = new LikeCondition(c, subQuery);
                        whereClause.addCondition(likeCondition);
                    } else if (next.matches("'[^']+'")) {
                        LikeCondition likeCondition = new LikeCondition(c, next);
                        whereClause.addCondition(likeCondition);
                    } else error("LIKE doesn't have a valid argument");
                } else if (next.matches("(>)|(<)|(>=)|(<=)|=")) {
                    String operator = next;
                    if (!listIterator.hasNext()) error("WHERE clause not valid");
                    next = listIterator.next();
                    if (next.equals("(select")) {
                        String ssubQuery = locateSubQuery(listIterator);
                        SQLQuery subQuery = parse(ssubQuery);
                        RelationCondition relationCondition = new RelationCondition(c, operator, subQuery);
                        whereClause.addCondition(relationCondition);
                    } else if (next.matches("[0-9]+")) {
                        RelationCondition relationCondition = new RelationCondition(c, operator, Integer.valueOf(next));
                        whereClause.addCondition(relationCondition);
                    } else error("Relation condition doesn't have a valid argument");
                } else if (next.equals("in")) {
                    if (!listIterator.hasNext()) error("WHERE clause not valid");
                    next = listIterator.next();
                    if (next.equals("(select")) {
                        String ssubQuery = locateSubQuery(listIterator);
                        SQLQuery subQuery = parse(ssubQuery);
                        InCondition inCondition = new InCondition(c, true);
                        inCondition.addValue(subQuery);
                        whereClause.addCondition(inCondition);
                    } else if (next.matches("\\([0-9]+(,[0-9]+)*\\)")) {
                        List<String> values = List.of(next.split("[(,)]"));
                        InCondition inCondition = new InCondition(c, true);
                        for (String value : values)
                            inCondition.addValue(Integer.valueOf(value));
                        whereClause.addCondition(inCondition);
                    } else if (next.matches("\\([a-z_]+\\.[a-z_]+\\.[a-z_]+\\)")) {
                        List<String> values = List.of(next.split("[(,)]"));
                        InCondition inCondition = new InCondition(c, true);
                        for (String value : values)
                            inCondition.addValue(value);
                        whereClause.addCondition(inCondition);
                    } else error("IN doesn't have a valid argument");
                } else error("WHERE isn't followed by valid condition");
                validCondition = true;
                if (!listIterator.hasNext()) break;
                next = listIterator.next();
                if (next.equals("and") || next.equals("or")) {
                    whereClause.addLogicalOperator(next);
                    validCondition = false;
                } else {
                    listIterator.previous();
                    break;
                }
            } else error("WHERE condition doesn't contain a valid column");
        }
        if(!validCondition) error("WHERE clause not valid");
    }

    private void generateGroupClause(ListIterator<String> listIterator, SQLQuery sqlQuery) {
        if (listIterator.hasNext() && listIterator.next().equals("by")) {
            GroupClause groupClause = new GroupClause(sqlQuery);
            boolean validGroup = false;
            while (listIterator.hasNext()) {
                String next = listIterator.next();
                next = next.replaceAll(",", "");
                boolean isKeyword = SQLKeyword.checkKeyword(next);
                if (!isKeyword && (next.matches("[a-z_]+") || next.matches("[a-z_]+\\.[a-z_]+\\.[a-z_]+"))) {
                    Column c = new Column(next);
                    groupClause.addGroupColumn(c);
                } else {
                    listIterator.previous();
                    break;
                }
                validGroup = true;
            }
            if(!validGroup) error("GROUP BY clause not valid");
            /* if(!listIterator.hasNext()) return;
            String next = listIterator.next();
            if(next.equals("having")) {
                boolean validCondition = false;
                while(listIterator.hasNext()) {
                    next = listIterator.next();
                    boolean isKeyword = SQLKeyword.checkKeyword(next);
                    if (!isKeyword && (next.matches("[a-z_]+") || next.matches("[a-z_]+\\.[a-z_]+\\.[a-z_]+") || next.matches("[a-z_]+\\([a-z_]+\\)"))) {
                        Column c;
                        if(next.matches("[a-z_]+") || next.matches("[a-z_]+\\.[a-z_]+\\.[a-z_]+"))
                            c = new Column(next);
                        else {
                            String[] aggregateFunction = next.split("[(,)]");
                            c = new Column(aggregateFunction[1], aggregateFunction[0]);
                        }
                        if (!listIterator.hasNext()) error();
                        next = listIterator.next();
                        if (next.equals("between")) {
                            if (!listIterator.hasNext()) error();
                            next = listIterator.next();
                            Object o1 = new Object();
                            Object o2 = new Object();
                            if (next.equals("(select")) {
                                String ssubQuery = locateSubQuery(listIterator);
                                o1 = parse(ssubQuery);
                            } else if (next.matches("[0-9]+")) {
                                o1 = Integer.valueOf(next);
                            } else error();
                            if (!listIterator.hasNext() || !listIterator.next().equals("and")) error();
                            if (!listIterator.hasNext()) error();
                            next = listIterator.next();
                            if (next.equals("(select")) {
                                String ssubQuery = locateSubQuery(listIterator);
                                o2 = parse(ssubQuery);
                            } else if (next.matches("[0-9]+")) {
                                o2 = Integer.valueOf(next);
                            } else error();
                            BetweenCondition betweenCondition = new BetweenCondition(c, o1, o2);
                            groupClause.addCondition(betweenCondition);
                        } else if (next.equals("like")) {
                            if (!listIterator.hasNext()) error();
                            next = listIterator.next();
                            if (next.equals("(select")) {
                                String ssubQuery = locateSubQuery(listIterator);
                                SQLQuery subQuery = parse(ssubQuery);
                                LikeCondition likeCondition = new LikeCondition(c, subQuery);
                                groupClause.addCondition(likeCondition);
                            } else if (next.matches("'[^']+'")) {
                                LikeCondition likeCondition = new LikeCondition(c, next);
                                groupClause.addCondition(likeCondition);
                            } else error();
                        } else if (next.matches("(>)|(<)|(>=)|(<=)|=")) {
                            String operator = next;
                            if (!listIterator.hasNext()) error();
                            next = listIterator.next();
                            if (next.equals("(select")) {
                                String ssubQuery = locateSubQuery(listIterator);
                                SQLQuery subQuery = parse(ssubQuery);
                                RelationCondition relationCondition = new RelationCondition(c, operator, subQuery);
                                groupClause.addCondition(relationCondition);
                            } else if (next.matches("[0-9]+")) {
                                RelationCondition relationCondition = new RelationCondition(c, operator, Integer.valueOf(next));
                                groupClause.addCondition(relationCondition);
                            } else error();
                        } else if (next.equals("in")) {
                            if (!listIterator.hasNext()) error();
                            next = listIterator.next();
                            if (next.equals("(select")) {
                                String ssubQuery = locateSubQuery(listIterator);
                                SQLQuery subQuery = parse(ssubQuery);
                                InCondition inCondition = new InCondition(c, true);
                                inCondition.addValue(subQuery);
                                groupClause.addCondition(inCondition);
                            } else if (next.matches("\\([0-9]+(,[0-9]+)*\\)")) {
                                List<String> values = List.of(next.split("[(,)]"));
                                InCondition inCondition = new InCondition(c, true);
                                for (String value : values)
                                    inCondition.addValue(Integer.valueOf(value));
                                groupClause.addCondition(inCondition);
                            } else if (next.matches("\\([a-z_]+\\.[a-z_]+\\.[a-z_]+\\)")) {
                                List<String> values = List.of(next.split("[(,)]"));
                                InCondition inCondition = new InCondition(c, true);
                                for (String value : values)
                                    inCondition.addValue(value);
                                groupClause.addCondition(inCondition);
                            } else error();
                        } else error();
                        validCondition = true;
                        if (!listIterator.hasNext()) break;
                        next = listIterator.next();
                        if (next.equals("and") || next.equals("or")) {
                            groupClause.addLogicalOperator(next);
                            validCondition = false;
                        } else {
                            listIterator.previous();
                            break;
                        }
                    } else error();
                }
                if(!validCondition) error();
            } else error(); */
        } else error("GROUP not followed by BY");
    }
    private void generateOrderClause(ListIterator<String> listIterator, SQLQuery sqlQuery) {
        if (listIterator.hasNext() && listIterator.next().equals("by")) {
            if(!listIterator.hasNext()) error("ORDER BY clause not valid");
            String next = listIterator.next();
            if (next.matches("[a-z_]+") || next.matches("[a-z_]+\\.[a-z_]+\\.[a-z_]+")) {
                Column column = new Column(next);
                if(!listIterator.hasNext()) error("ORDER BY clause not valid");
                next = listIterator.next();
                if (next.equals("asc") || next.equals("desc")) {
                    new OrderClause(sqlQuery, column, next);
                } else error("ORDER BY doesn't have a valid order type");
            } else error("ORDER BY doesn't have a valid column to sort by");
        } else error("ORDER not followed by BY");
    }

    private String locateSubQuery(ListIterator<String> listIterator) {
        int lp = 1;
        int rp = 0;
        StringBuilder result = new StringBuilder(listIterator.previous().replaceAll("\\(",""));
        listIterator.next();
        while (listIterator.hasNext() && lp > rp) {
            String next = listIterator.next();
            if(next.contains("("))
                lp++;
            if(next.contains(")")) {
                rp++;
                if(lp == rp)
                    next = next.replaceAll("\\)", "");
            }
            result.append(" ").append(next);
        }
        return result.toString();
    }

    private void error(String message) {
        MainFrame.getInstance().getAppCore().getMessageGenerator().getMessage(message);
    }

    @Override
    public void addSub(ISubscriber sub) {
        this.subs.add(sub);
    }

    @Override
    public void removeSub(ISubscriber sub) {
        this.subs.remove(sub);
    }

    @Override
    public void notify(Object notification) {
        for(ISubscriber sub : subs){
            sub.update(notification);
        }
    }
}
