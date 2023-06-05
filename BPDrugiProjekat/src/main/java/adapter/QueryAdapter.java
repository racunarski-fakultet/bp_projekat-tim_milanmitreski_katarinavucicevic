package adapter;

import database.mongo.MongoQuery;
import observer.IPublisher;
import observer.ISubscriber;

public interface QueryAdapter extends ISubscriber, IPublisher {

    void queryConverter(); // this method is probably going to have some arguments and different return value
}
