package adapter;

import database.SQL.Column;
import database.SQL.LogicalOperator;
import database.SQL.OrderType;
import database.SQL.SQLQuery;
import database.SQL.clause.*;
import database.SQL.condition.*;
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
        convertFrom();
        convertWhere();
        convertGroup();
        convertOrder();
    }

    private void convertWhere() {
        for(SQLClause clause : query.getClauses()) {
            if(clause instanceof WhereClause) {
                WhereClause whereClause = (WhereClause) clause;
                StringBuilder match = new StringBuilder("{$match:");
                ListIterator<LogicalOperator> listIterator = whereClause.getLogicalOperators().listIterator();
                for (WGCondition condition : whereClause.getConditionList()) {
                    match.append("{");
                    if(listIterator.hasNext()) {
                        match.append("$").append(listIterator.next().name().toLowerCase()).append(":[{");
                    }

                    if(condition instanceof InCondition) {
                        InCondition inCondition = (InCondition) condition;
                        if(inCondition.isSubQuery()) {

                        } else {
                            match.append("$in:[");
                            for(Object value : inCondition.getValues()) {
                                match.append(value.toString()).append(",");
                            }
                            match.deleteCharAt(match.lastIndexOf(","));
                            match.append("]");
                        }
                    } else if(condition instanceof RelationCondition) {
                        RelationCondition relationCondition = (RelationCondition) condition;
                        if(relationCondition.getReferenceValue() instanceof SQLQuery) {

                        } else {
                            if(condition.getConditionColumn().getColumnName().contains(".")) {
                                match.append("\"").append("$").append(condition.getConditionColumn().getColumnName().split("\\.")[1]);
                                match.append(".").append(condition.getConditionColumn().getColumnName().split("\\.")[2]);
                            } else match.append("\"").append(condition.getConditionColumn().getColumnName()).append("\":{$");
                            match.append(relationCondition.getRelationConditionType().name().toLowerCase()).append(":");
                            match.append(relationCondition.getReferenceValue().toString()).append("}");
                        }
                    } else {
                        if(condition.getConditionColumn().getColumnName().contains(".")) {
                            match.append("\"").append("$").append(condition.getConditionColumn().getColumnName().split("\\.")[1]);
                            match.append(".").append(condition.getConditionColumn().getColumnName().split("\\.")[2]);
                        } else match.append("\"").append(condition.getConditionColumn().getColumnName()).append("\":/^");
                        match.append(((LikeCondition)condition).getReferenceValue().replace("%",".*").replace("_",".").replace("'",""));
                        match.append("$/");
                    }
                    match.append("},");
                }
                match.deleteCharAt(match.lastIndexOf(","));
                for(int i = 0; i < whereClause.getLogicalOperators().size(); i++) {
                    match.append("]}");
                }
                match.append("}");
                System.out.println(match);
                whereConverted = Document.parse(match.toString());
                System.out.println(whereConverted.toJson());
            }
        }
        whereConverted = null;
    }

    private void convertGroup() {
        for(SQLClause clause : query.getClauses()) {
            if(clause instanceof GroupClause) {
                GroupClause groupClause = (GroupClause) clause;
                StringBuilder group = new StringBuilder("{ $group: { _id: {");
                for(Column c : groupClause.getGroupColumns()) {
                    if(c.getColumnName().contains(".")) {
                        group.append(c.getColumnName().replace(".", "_")).append(":\"");
                        group.append("$").append(c.getColumnName().split("\\.")[1]).append(".").append(c.getColumnName().split("\\.")[2]).append("\",");
                    } else {
                        group.append(c.getColumnName()).append(":\"");
                        group.append("$").append(c.getColumnName()).append("\",");
                    }
                }
                group.deleteCharAt(group.lastIndexOf(",")).append("},");
                for(Column c : ((SelectClause) query.getClauses().get(0)).getColumns()) {
                    if(c.isAggregate()) {
                        if(c.getColumnName().contains(".")) {
                            group.append("\"").append(c.getAggregateFunction().name().toLowerCase());
                            group.append("_").append(c.getColumnName().replace(".","_")).append("\":");
                            group.append("{ $").append(c.getAggregateFunction().name().toLowerCase()).append(":\"");
                            group.append("$").append(c.getColumnName().split("\\.")[1]).append(".").append(c.getColumnName().split("\\.")[2]).append("\"},");
                        } else {
                            group.append("\"").append(c.getAggregateFunction().name().toLowerCase());
                            group.append("_").append(c.getColumnName()).append("\":");
                            group.append("{ $").append(c.getAggregateFunction().name().toLowerCase()).append(":\"");
                            group.append("$").append(c.getColumnName()).append("\"},");
                        }
                    } else {
                        if(c.getColumnName().contains(".")) {
                            group.append("\"").append(c.getColumnName().replace(".","_")).append("\":");
                            group.append("{ $first").append(":\"");
                            group.append("$").append(c.getColumnName().split("\\.")[1]).append(".").append(c.getColumnName().split("\\.")[2]).append("\"},");
                        } else {
                            group.append("\"").append(c.getColumnName()).append("\":");
                            group.append("{ $first").append(":\"");
                            group.append("$").append(c.getColumnName()).append("\"},");
                        }
                    }
                }
                group.deleteCharAt(group.lastIndexOf(",")).append("}}");
                System.out.println(group);
                groupConverted = Document.parse(group.toString());
            }
        }
        groupConverted = null;
    }

    private void convertFrom() {
        FromClause fromClause = (FromClause) query.getClauses().get(1);
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

    private void convertSelect() {
        SelectClause selectClause = (SelectClause) query.getClauses().get(0);
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
