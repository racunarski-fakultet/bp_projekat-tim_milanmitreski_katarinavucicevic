package adapter;

import database.SQL.SQLQuery;
import observer.ISubscriber;

public class QueryAdapterImplementation implements QueryAdapter, ISubscriber {
    @Override
    public void queryConverter() { /// this is ging to convert sql queries to Mongo ones

    }

    @Override
    public void update(Object notification) {
        SQLQuery query = (SQLQuery) notification;
    }
}
