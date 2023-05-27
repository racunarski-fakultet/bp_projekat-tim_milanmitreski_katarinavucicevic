package gui.queryPanel;

import data.Row;
import database.SQL.SQLQuery;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FilterTablePanel extends JFrame {

    private SQLQuery query;
    private List<Row> resultSet;

    public FilterTablePanel() {

        //String fromTable = "";   /// these model things, are not going to be here, in view
        //resultSet = query.saveResultSet(fromTable);   /// this is just a representatation of an idea

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTable newTable = new JTable();
        JScrollPane newScrollPane = new JScrollPane(newTable);

        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.add(newScrollPane, BorderLayout.CENTER);

        this.setContentPane(newPanel);
        this.setSize(400, 300);
        this.setVisible(true);
    }
}
