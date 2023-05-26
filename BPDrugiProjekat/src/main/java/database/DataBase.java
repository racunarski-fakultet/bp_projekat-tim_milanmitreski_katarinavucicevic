package database;

import data.Row;

import java.util.List;

public interface DataBase {

    List<Row> getDataFromTable(String from);
}
