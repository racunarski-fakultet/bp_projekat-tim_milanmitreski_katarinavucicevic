package database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import database.Query;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

public class MongoQuery implements Query{   /// executor

    private String table;
    private List<Document> jsonQuery;

    /** Ovako mi je mnogo logicnije **/

    public MongoQuery(String table) {
        this.jsonQuery = new LinkedList<>();
        this.table = table;
    }

    @Override
    public MongoCursor<Document> runQuery(){
        MongoClient connection = MongoConnection.getConnection();
        MongoDatabase database = connection.getDatabase(table);
        MongoCursor<Document> cursor = database.aggregate(jsonQuery).iterator();
        MongoConnection.closeConnection();


        return cursor;
    }

    public void addJsonQuery(Document jsonQuery) {
        this.jsonQuery.add(jsonQuery);
    }

    @Override
    public void update(Object notification) {

    }
}
