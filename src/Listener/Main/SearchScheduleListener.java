package Listener.Main;

import Entity.Activity;
import Entity.City;
import Repository.ActivityRepository;
import Repository.CityRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Find the best schedule for the user
 */
public class SearchScheduleListener implements ActionListener {
    private final JFrame frame;
    private final JComboBox<String> cityList;
    private final JSpinner sNbJour;
    private final DefaultTableModel tableActivityModel;

    public SearchScheduleListener(JFrame frame, JComboBox<String> cityList, JSpinner sNbJour, DefaultTableModel tableActivityModel) {
        super();
        this.frame = frame;
        this.cityList = cityList;
        this.sNbJour = sNbJour;
        this.tableActivityModel = tableActivityModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            City city = new CityRepository().getCityByName(String.valueOf(cityList.getSelectedItem()));
            ActivityRepository activityRepository = new ActivityRepository();

            // Removes all rows of the table
            int rowCount = tableActivityModel.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                tableActivityModel.removeRow(i);
            }

            ArrayList<Activity> activities = activityRepository.getActivitiesByCity(city);

            // If there are activities
            if (activities.size() > 0) {
                int totalTime = 0;
                int maxTime = (int) sNbJour.getValue() * 10 * 60; // Max 10 hours of activity per day

                for (Activity activity :
                        activities) {
                    // Verification of the max time
                    int activityTime = activity.getTime().getTotalMinutes();
                    if (totalTime + activityTime >= maxTime) {
                        // Continue the loop to check if there are not shorter activities
                        continue;
                    }
                    totalTime += activityTime;

                    // If enough time continues
                    Vector<String> vector = new Vector<>();

                    vector.add(String.valueOf(activity.getId()));
                    vector.add(activity.getName());
                    vector.add(activity.getDescription());
                    vector.add(String.valueOf(activity.getTime().getTotalMinutes()));
                    vector.add(String.valueOf(activity.getRating()));

                    // Add the activities
                    tableActivityModel.addRow(vector);
                }
            } else { // If not, show a message
                JOptionPane.showMessageDialog(frame, "Aucun résultat pour cette ville.");
            }
        } catch (ClassNotFoundException | SQLException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
    }
}
