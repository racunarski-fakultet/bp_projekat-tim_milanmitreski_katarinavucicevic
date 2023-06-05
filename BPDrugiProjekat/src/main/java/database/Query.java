package database;

import com.mongodb.client.MongoCursor;
import observer.ISubscriber;
import org.bson.Document;

public interface Query extends ISubscriber {

    Object runQuery();
}
