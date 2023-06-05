package database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import database.Query;
import observer.IPublisher;
import observer.ISubscriber;
import org.bson.Document;

import java.util.Arrays;

public class MongoQuery implements Query{   /// executor

    private String table;
    private Document jsonQuery;

    /** Ovako mi je mnogo logicnije **/
    public void runQuery(){
        MongoClient connection = MongoConnection.getConnection();
        MongoDatabase database = connection.getDatabase(table);
        MongoCursor<Document> cursor = database.aggregate(Arrays.asList(jsonQuery)).iterator();
        MongoConnection.closeConnection();
    }
}
