package adapter;

import database.SQL.SQLQuery;
import database.mongo.MongoQuery;
import observer.ISubscriber;

public class QueryAdapterImplementation implements QueryAdapter, ISubscriber {
    @Override
    public MongoQuery queryConverter() { /// this is ging to convert sql queries to Mongo ones

        MongoQuery mongoQuery = null;


        return mongoQuery;
    }

    @Override
    public void update(Object notification) {
        SQLQuery query = (SQLQuery) notification;
    }
}
