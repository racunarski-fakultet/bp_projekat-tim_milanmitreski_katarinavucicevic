package gui.queryPanel;

import com.mongodb.client.MongoCursor;
import data.Row;
import database.mongo.MongoConnection;
import gui.MainFrame;
import gui.table.TableModel;
import observer.ISubscriber;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Packager implements ISubscriber {

    private TableModel filteredModel;
    private List<Row> rows;
    private  boolean errorFlag = false;

    public Packager() {
        this.filteredModel = new TableModel();
        rows = new ArrayList<>();
    }

    public void setFilteredData(){
        // dobijam listu dokumenata i treba od toga da nparavim redove
        filteredModel.setRows(rows);
    }

    public TableModel getFilteredModel() {
        return filteredModel;
    }

    @Override
    public void update(Object notification) {

        errorFlag = false;

        rows = new ArrayList<>();

        MongoCursor<Document> cursor = (MongoCursor<Document>) notification;

        List<Document> documents = new ArrayList<>();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            documents.add(document);
        }

        cursor.close();
        MongoConnection.closeConnection();

        if(!documents.isEmpty()) {
            List<String> columns = new ArrayList<>(documents.get(0).keySet());

            for (Document document : documents) {

                Row row = new Row();
                for (String column : columns) {
                    Object value = document.get(column);

                    row.addField(column, value);
                }

                rows.add(row);
            }
        } else {
            MainFrame.getInstance().getAppCore().getMessageGenerator().getMessage("List of documents is empty (probably a typo)");
            errorFlag = true;
            return;
        }

    }

    public boolean isErrorFlag() {
        return errorFlag;
    }
}
