package gui;

import gui.queryPanel.FilterTablePanel;
import start.AppCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private static MainFrame instance = null;
    private AppCore appCore;
    private JTable table;  //njegov model je TableModel, koji se definise preko vektora

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
        this.setMinimumSize(new Dimension(1000, 700));
        setLayout(new BorderLayout());


        JTextArea textArea = new JTextArea(20, 50);
        JButton button = new JButton("Submit");
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(button);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(textArea, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appCore.getParser().parse(textArea.getText());
                new FilterTablePanel();
            }
        });

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.table.setModel(appCore.getTableModel());
    }
}
