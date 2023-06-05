package database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import data.Row;
import database.settings.Settings;
import gui.MainFrame;
import gui.table.TableModel;
import org.bson.Document;
import utils.Constants;

import java.util.ArrayList;
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

    public void readData(String fromTable){   /// List<Row> povratni tip

        getConnection();
        MongoDatabase database = mongoClient.getDatabase("bp_tim68");


        MongoCollection<Document> collection = database.getCollection("departments");


        MongoCursor<Document> cursor = collection.find().iterator();
        List<Document> documents = new ArrayList<>();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            documents.add(document);
        }

        cursor.close();

        String[] columnNames = {"Column 1", "Column 2", "Column 3"}; // Replace with your actual column names


        for (Document document : documents) {
            Object[] rowData = {
                    document.get(fromTable),
            }; // Replace field1, field2, field3 with the actual fields in your documents

            MainFrame.getInstance().getAppCore().getTableModel().addRow(rowData); // treba da se zove setRows i da se sve smesta u te rows, ovo sve se ne desava ovde
        }


        closeConnection();
    }
}
