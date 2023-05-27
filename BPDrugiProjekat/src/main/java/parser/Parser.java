package parser;

import database.Query;

public interface Parser {

    Query parse(String sQuery);

}
