package validator;

import database.SQL.Column;
import database.SQL.SQLQuery;
import database.SQL.clause.*;
import database.SQL.condition.WGCondition;
import observer.IPublisher;
import observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

public class Validator implements ISubscriber, IPublisher {

    private SQLQuery query;
    List<ISubscriber> subs = new ArrayList<>();

    @Override
    public void update(Object notification) {
        query = (SQLQuery) notification;

        if(!containsSelect()){
            System.out.println("select clause missing");
            return;
        }

        if(!containsFrom()){
            System.out.println("from clause missing");
            return;
        }

        if(aggInWhere()){
            System.out.println("you can't put aggregate function in where clause");
            return;
        }

        if(!aggAndGroup()){
            System.out.println("there are columns which are neither aggregated nor grouped");
            return;
        }

        notify(query);

    }

    public boolean containsSelect(){
        for(SQLClause clause : query.getClauses()){
            if(clause instanceof SelectClause){
                return true;
            }
        }
        return false;
    }

    public boolean containsFrom(){

        for(SQLClause clause : query.getClauses()){
            if(clause instanceof FromClause){
                return true;
            }
        }
        return false;
    }

    public boolean containsWhere(){

        for(SQLClause clause : query.getClauses()){
            if(clause instanceof WhereClause){
                return true;
            }
        }
        return false;
    }

    public boolean hasAgg(SQLQuery q){

        int  count = 0;
        for(Column c : ((SelectClause) q.getClauses().get(0)).getColumns()){
            if(c.isAggregate()){
                count++;
            }
        }
        if(count == ((SelectClause) q.getClauses().get(0)).getColumns().size()){
            return true;
        }

        return false;
    } /// uzasno resenje za proveravanje agregacije

    public boolean aggInWhere(){    /// u where ne sme da postoji agregacija

        if(!hasAgg(query)){
            return false;
        }

        for(SQLClause clause : query.getClauses()){
            if(clause instanceof WhereClause){
                for(WGCondition condition : ((WhereClause) clause).getConditionList()){
                    if(condition.getConditionColumn().isAggregate()){
                        return true;   /// aggregate se nalazi u where
                    }
                }
            }
        }
        return false;
    }

    public boolean aggAndGroup(){

        if(!hasAgg(query)){
            return false;
        }

        for(Column c : ((SelectClause) query.getClauses().get(0)).getColumns()){
            if(!c.isAggregate()){
                for(SQLClause clause : query.getClauses()){
                    if(clause instanceof GroupClause &&
                        !((GroupClause)clause).getGroupColumns().contains(c)){
                        return false;
                    }
                }
            }
        }

        return true;

    }

    @Override
    public void addSub(ISubscriber sub) {
        subs.add(sub);
    }

    @Override
    public void removeSub(ISubscriber sub) {
        subs.remove(sub);
    }

    @Override
    public void notify(Object notification) {
        for(ISubscriber s : subs){
            s.update(notification);
        }
    }
}
