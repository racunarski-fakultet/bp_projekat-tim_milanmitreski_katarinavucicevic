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
    @Override
    public MongoCursor<Document> runQuery(){
        MongoClient connection = MongoConnection.getConnection();
        MongoDatabase database = connection.getDatabase(table); // bptim68
        MongoCursor<Document> cursor = database.aggregate(Arrays.asList(jsonQuery)).iterator();
        MongoConnection.closeConnection();


        return cursor;
    }

    @Override
    public void update(Object notification) {

    }
}
