package gui.queryPanel;

import data.Row;
import database.mongo.MongoConnection;
import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FilterTablePanel extends JFrame{

    private Packager packager;

    public FilterTablePanel() {

        MongoConnection.getConnection();

        packager = MainFrame.getInstance().getAppCore().getPackager();

        JTable newTable = new JTable(packager.getFilteredModel());
        JScrollPane newScrollPane = new JScrollPane(newTable);
        packager.setFilteredData();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.add(newScrollPane, BorderLayout.CENTER);

        this.setContentPane(newPanel);
        this.setSize(400, 300);
        this.setVisible(true);

        MongoConnection.closeConnection();
    }

    public Packager getPackager() {
        return packager;
    }
}
