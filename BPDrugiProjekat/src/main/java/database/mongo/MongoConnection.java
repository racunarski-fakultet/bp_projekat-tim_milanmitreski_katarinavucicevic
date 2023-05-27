package database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import data.Row;
import database.settings.Settings;
import utils.Constants;
import java.util.Arrays;
import java.util.List;

public class MongoConnection {

    private static Settings settings;
    private static MongoClient mongoClient;

    public MongoConnection(Settings settings) {
        this.settings = settings;
    }

    public static MongoClient getConnection(){

        String ip = (String) settings.getParameter(Constants.IP);
        String userName = (String) settings.getParameter(Constants.USERNAME);
        String password = (String) settings.getParameter(Constants.PASSWORD);
        String database = (String) settings.getParameter(Constants.DATABASE);


        MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
        mongoClient = new MongoClient(new ServerAddress(ip, 27017), Arrays.asList(credential));

        System.out.println("MongoDB connection established");

        return mongoClient;
    }

    public static void closeConnection(){
        mongoClient.close();
    }  // svaki piut kad pokrenem query

    public List<Row> readData(String fromTable){
        return null;
    }
}
