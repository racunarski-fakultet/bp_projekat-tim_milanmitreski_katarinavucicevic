package database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import database.Query;

public class MongoQuery implements Query {   ///executor

    private static MongoClient connection;
    //private MongoConnection mongoConnection;

    public MongoQuery(MongoClient connection) {
        MongoQuery.connection = connection;
    }

    @Override
    public void runQuery(String from){

        MongoConnection.getConnection();

        //MongoDatabase database = connection.getDatabase();

        MongoConnection.closeConnection();
    }
}
