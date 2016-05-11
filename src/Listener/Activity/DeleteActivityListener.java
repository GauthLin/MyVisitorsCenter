package Listener.Activity;

import Entity.Activity;
import Repository.ActivityRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Delete the selected activity
 */
public class DeleteActivityListener implements ActionListener {
    private final JFrame frame;
    private final JTable activitiesTable;

    public DeleteActivityListener(JFrame frame, JTable activitiesTable) {
        super();
        this.frame = frame;
        this.activitiesTable = activitiesTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Delete the selected activities
        for (Integer i :
                activitiesTable.getSelectedRows()) {
            DefaultTableModel activitiesModel = (DefaultTableModel) activitiesTable.getModel();
            String activityId = (String) activitiesModel.getValueAt(i, 0);

            try {
                new ActivityRepository().deleteActivityById(activityId);
                activitiesModel.removeRow(i);
            } catch (ClassNotFoundException | SQLException e1) {
                JOptionPane.showMessageDialog(frame, e1.getMessage());
            }
        }
    }
}
