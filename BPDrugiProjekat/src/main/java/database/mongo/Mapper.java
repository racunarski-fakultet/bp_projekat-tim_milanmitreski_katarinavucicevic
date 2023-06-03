package database.mongo;

import com.mysql.cj.xdevapi.JsonParser;
import com.mysql.cj.xdevapi.JsonString;
import database.mongo.mongoObjects.MongoKeyWord;
import parser.sql.SQLKeyword;

import java.util.HashMap;
import java.util.Map;

public class Mapper {

    private Map<SQLKeyword, String> map = new HashMap<>();

    public Map<SQLKeyword, String> mapIt(){
        map.put(SQLKeyword.WHERE, "find({");
        map.put(SQLKeyword.GROUP, "$group: {");
        map.put(SQLKeyword.AND, "#and: {");


        return map;
    }

    public JsonString jsonParser(){
        return null;
    }

}
