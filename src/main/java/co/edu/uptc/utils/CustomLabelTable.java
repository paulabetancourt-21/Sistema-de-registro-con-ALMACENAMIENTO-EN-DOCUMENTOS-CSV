package co.edu.uptc.utils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class CustomLabelTable {
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;

    public CustomLabelTable(String[] columns) {
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scrollPane = new JScrollPane(table);
    }

    public CustomLabelTable addRow(Object[] element) {
        model.addRow(element);
        return this;
    }

    public CustomLabelTable clearRows() {
        model.setRowCount(0);
        return this;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}