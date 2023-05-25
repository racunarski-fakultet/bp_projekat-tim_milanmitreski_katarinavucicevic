package database;

import Data.Row;

import java.util.List;

public interface DataBase {

    List<Row> getDataFromTable(String from);
}
