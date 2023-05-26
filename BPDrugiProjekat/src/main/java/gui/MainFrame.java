package gui;

import start.AppCore;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static MainFrame instance = null;
    private AppCore appCore;
    private JTable table;

    private MainFrame(){

    }

    public static MainFrame getInstance() {
        if(instance == null){
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }

    public void initialise(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(500, 350));
        table.setFillsViewportHeight(true);
        this.add(new JScrollPane(table));

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.table.setModel(appCore.getTableModel());
    }
}
