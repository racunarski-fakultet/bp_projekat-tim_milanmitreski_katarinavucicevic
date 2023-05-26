package gui.queryPanel;

import javax.swing.*;
import java.awt.*;

public class FilterTablePanel extends JFrame {

    public FilterTablePanel() {

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
