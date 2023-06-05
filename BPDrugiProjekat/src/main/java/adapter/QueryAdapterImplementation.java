package adapter;

import com.mysql.cj.xdevapi.JsonParser;
import database.SQL.Column;
import database.SQL.OrderType;
import database.SQL.SQLQuery;
import database.SQL.clause.OrderClause;
import database.SQL.clause.SQLClause;
import database.SQL.clause.SelectClause;
import database.mongo.MongoQuery;
import observer.ISubscriber;
import org.bson.Document;
import org.bson.json.JsonWriter;
import parser.Parser;

import javax.print.Doc;
import java.io.BufferedWriter;
import java.io.Writer;

public class QueryAdapterImplementation implements QueryAdapter {

    private SQLQuery query;

    private Document selectConverted;
    private Document fromConverted;
    private Document whereConverted;
    private Document groupConverted;
    private Document orderConverted;

    @Override
    public void queryConverter() { /// this is ging to convert sql queries to Mongo ones
        MongoQuery mongoQuery = new MongoQuery();
        convertParameters();
        map();
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
    }

    private void convertGroup() {

    }

    private void convertFrom() {

    }

    private void convertSelect() {
        for(SQLClause clause : query.getClauses()) {
            if(clause instanceof SelectClause) {
                SelectClause selectClause = (SelectClause) clause;
                StringBuilder json = new StringBuilder("{ $project : {");
                for(Column c : selectClause.getColumns()) {
                    if(c.isAggregate()) {
                        json.append(c.getAggregateFunction().name().toLowerCase()).append("_").append(c.getColumnName()).append(": 1,");
                    } else {
                        json.append(c.getColumnName()).append(": 1,");
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
                    orderConverted = Document.parse("{ $sort : {" + orderClause.getOrderColumn().getColumnName() + ": 1}}");
                } else {
                    orderConverted = Document.parse("{ $sort : {" + orderClause.getOrderColumn().getColumnName() + ": -1}}");
                }
            }
        }
        orderConverted = null;
    }

    public void map() {

    }

    @Override
    public void addSub(ISubscriber sub) {

    }

    @Override
    public void removeSub(ISubscriber sub) {

    }

    @Override
    public void notify(Object notification) {

    }
}
