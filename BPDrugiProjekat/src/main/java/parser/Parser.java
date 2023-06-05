package parser;

import database.SQL.SQLQuery;
import observer.IPublisher;

public interface Parser extends IPublisher {

    SQLQuery parse(String sQuery);  /// ovo je moralo da se promeni u SQLQuery povratni tip, jer SQL vise ne implementira Query

}
