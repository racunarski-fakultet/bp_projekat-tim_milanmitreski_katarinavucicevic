package adapter;

import database.mongo.MongoQuery;

public interface QueryAdapter {

    MongoQuery queryConverter(); // this method is probably going to have some arguments and different return value
}
