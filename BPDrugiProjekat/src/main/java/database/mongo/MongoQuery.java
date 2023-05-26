package database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import database.Query;

public class MongoQuery implements Query {

    private MongoClient connection;
    private MongoConnection mongoConnection;

    public MongoQuery(MongoClient connection) {
        this.connection = connection;
    }

    @Override
    public void runQuery(String from){

        mongoConnection.getConnection();

        //MongoDatabase database = connection.getDatabase();
    }
}
