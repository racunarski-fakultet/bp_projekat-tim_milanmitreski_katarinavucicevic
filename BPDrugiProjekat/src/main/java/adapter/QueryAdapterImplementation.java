package adapter;

import database.SQL.Column;
import database.SQL.OrderType;
import database.SQL.SQLQuery;
import database.SQL.clause.FromClause;
import database.SQL.clause.OrderClause;
import database.SQL.clause.SQLClause;
import database.SQL.clause.SelectClause;
import database.SQL.condition.JoinCondition;
import database.SQL.condition.JoinConditionType;
import database.mongo.MongoQuery;
import observer.ISubscriber;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class QueryAdapterImplementation implements QueryAdapter {

    private SQLQuery query;
    private List<ISubscriber> subs;
    private String table;
    private Document selectConverted;
    private Document fromConverted;
    private Document whereConverted;
    private Document groupConverted;
    private Document orderConverted;

    public QueryAdapterImplementation() {
        this.subs = new LinkedList<>();
    }

    @Override
    public void queryConverter() { /// this is ging to convert sql queries to Mongo ones
        convertParameters();
        MongoQuery mongoQuery = new MongoQuery(table);
        map(mongoQuery);
        notify(mongoQuery);
    }

    @Override
    public void update(Object notification) {
        query = (SQLQuery) notification;
        queryConverter();
    }

    public void convertParameters() {
        convertSelect();
        System.out.println(selectConverted.toJson());
        convertFrom();
        System.out.println(fromConverted.toJson());
        convertWhere();
        convertGroup();
        convertOrder();
        System.out.println(orderConverted.toJson());
    }

    private void convertWhere() {
    }

    private void convertGroup() {

    }

    private void convertFrom() {
        for(SQLClause clause : query.getClauses()) {
            if(clause instanceof FromClause) {
                FromClause fromClause = (FromClause) clause;
                table = fromClause.getTable().getTableName().split("\\.")[1];
                if(fromClause.isHasJoins()) {
                    StringBuilder joins = new StringBuilder();
                    for (JoinCondition joinCondition : fromClause.getJoins()) {
                        joins.append("{ $lookup :{");
                        joins.append("from:\"").append(joinCondition.getJoinTable().getTableName().split("\\.")[1]).append("\"");
                        String conditionColumn = (joinCondition.getConditionColumn().getColumnName().contains(".")) ? joinCondition.getConditionColumn().getColumnName().split("\\.")[0] : joinCondition.getConditionColumn().getColumnName();
                        joins.append(",localField:").append("\"").append(conditionColumn).append("\"");
                        if(joinCondition.getJoinConditionType() == JoinConditionType.ON) {
                            String conditionColumnOn = (joinCondition.getConditionColumnOn().getColumnName().contains(".")) ? joinCondition.getConditionColumnOn().getColumnName().split("\\.")[0] : joinCondition.getConditionColumnOn().getColumnName() ;
                            joins.append(",foreignField:").append("\"").append(conditionColumnOn).append("\"");
                        } else {
                            joins.append(",foreignField:").append("\"").append(conditionColumn).append("\"");;
                        }
                        joins.append("as: \"").append(joinCondition.getJoinTable().getTableName().split("\\.")[1]).append("\"");
                        joins.append("}},{ $unwind:\"$").append(joinCondition.getJoinTable().getTableName().split("\\.")[1]).append("\"},");
                    }
                    joins.deleteCharAt(joins.lastIndexOf(","));
                    joins.append("}");
                    fromConverted = Document.parse(joins.toString());
                }
            }
        }
    }

    private void convertSelect() {
        for(SQLClause clause : query.getClauses()) {
            if(clause instanceof SelectClause) {
                SelectClause selectClause = (SelectClause) clause;
                StringBuilder json = new StringBuilder("{ $project : {");
                for(Column c : selectClause.getColumns()) {
                    if(!c.getColumnName().contains(".")) {
                        if (c.isAggregate()) {
                            json.append("\"").append(c.getAggregateFunction().name().toLowerCase()).append("_").append(c.getColumnName()).append("\": 1,");
                        } else {
                            json.append("\"").append(c.getColumnName()).append("\": 1,");
                        }
                    } else {
                        json.append("\"$").append(c.getColumnName().split("\\.")[1]).append(".");
                        if (c.isAggregate()) {
                            json.append(c.getAggregateFunction().name().toLowerCase()).append("_").append(c.getColumnName().split("\\.")[2]).append("\": 1,");
                        } else {
                            json.append(c.getColumnName().split("\\.")[2]).append("\": 1,");
                        }
                    }
                }
                json.append("_id:0}}");
                selectConverted = Document.parse(json.toString());
            }
        }
    }

    public void convertOrder() {
        for(SQLClause clause : query.getClauses()) {
            if(clause instanceof OrderClause) {
                OrderClause orderClause = (OrderClause) clause;
                if(orderClause.getOrderType() == OrderType.ASC) {
                    orderConverted = Document.parse("{ $sort : {\"" + orderClause.getOrderColumn().getColumnName() + "\": 1}}");
                    return;
                } else {
                    orderConverted = Document.parse("{ $sort : {\"" + orderClause.getOrderColumn().getColumnName() + "\": -1}}");
                    return;
                }
            }
        }
        orderConverted = null;
    }

    public void map(MongoQuery mongoQuery) {
        mongoQuery.addJsonQuery(whereConverted);
        mongoQuery.addJsonQuery(fromConverted);
        mongoQuery.addJsonQuery(groupConverted);
        mongoQuery.addJsonQuery(selectConverted);
        mongoQuery.addJsonQuery(orderConverted);
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
        for(ISubscriber sub : subs) {
            sub.update(notification);
        }
    }
}
