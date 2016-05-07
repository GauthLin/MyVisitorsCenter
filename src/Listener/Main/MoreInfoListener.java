package Listener.Main;

import Entity.Activity;
import Repository.ActivityRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Display all information for the activity selected in the list
 */
public class MoreInfoListener implements ActionListener{
    private final JFrame frame;
    private final JTable listActivities;

    public MoreInfoListener(JFrame frame, JTable listActivities) {
        super();
        this.frame = frame;
        this.listActivities = listActivities;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check if an activity is selected
        if (listActivities.getSelectedRow()== -1) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une activité.");
            return;
        }

        // If an activity is selected -> Display all information
        // Url to get data -> http://api.openweathermap.org/data/2.5/weather?q=bruxelles&apikey=00c682a40aa417713f53449f4e1804de
        DefaultTableModel activitiesModel = (DefaultTableModel) listActivities.getModel();
        String activityId = (String) activitiesModel.getValueAt(listActivities.getSelectedRow(), 0);
        try {
            Activity activity = new ActivityRepository().getActivityById(activityId);

        } catch (SQLException | ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
    }
}
