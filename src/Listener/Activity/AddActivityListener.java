package Listener.Activity;

import Entity.Activity;
import Entity.Category;
import Entity.City;
import Entity.Country;
import Manager.DBManager;
import Repository.ActivityRepository;
import Repository.CategoryRepository;
import Repository.CityRepository;
import Repository.CountryRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class AddActivityListener implements ActionListener
{
    private final JFrame frame;
    private final JComboBox<String> countryList;
    private final JComboBox<String> cityList;
    private final JTextField activityName;
    private final JTextArea activityDescription;
    private final JComboBox<String> categoryList;
    private final JTextField activityDuration;
    private final JSpinner activityRating;
    private final DefaultTableModel tableActivityModel;

    public AddActivityListener(JFrame frame, JComboBox<String> countryList, JComboBox<String> cityList, JTextField activityName, JTextArea activityDescription, JComboBox<String> categoryList, JTextField activityDuration, JSpinner activityRating, DefaultTableModel tableActivityModel) {
        super();
        this.frame = frame;
        this.countryList = countryList;
        this.cityList = cityList;
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.categoryList = categoryList;
        this.activityDuration = activityDuration;
        this.activityRating = activityRating;
        this.tableActivityModel = tableActivityModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            City city = new CityRepository().getCityByName(String.valueOf(cityList.getSelectedItem()));
            Category category = new CategoryRepository().getCategoryByName(String.valueOf(categoryList.getSelectedItem()));

            Activity activity = new Activity(
                    activityName.getText(),
                    activityDescription.getText(),
                    Integer.parseInt(activityDuration.getText().split(":")[0]),
                    Integer.parseInt(activityDuration.getText().split(":")[1]),
                    category,
                    (Integer) activityRating.getValue(),
                    city
                );

            Activity newActivity = new ActivityRepository().insertActivity(activity);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Vector<String> vec = new Vector<>();

                    vec.add(String.valueOf(newActivity.getId()));
                    vec.add(city.getCountry().getName());
                    vec.add(city.getName());
                    vec.add(newActivity.getName());
                    vec.add(newActivity.getDescription());
                    vec.add(String.valueOf(newActivity.getTime().getTotalMinutes()));
                    vec.add(String.valueOf(newActivity.getRating()));
                    tableActivityModel.addRow(vec);

                    // Vide tous les champs
                    countryList.setSelectedIndex(0);
                    activityName.setText("");
                    activityDescription.setText("");
                    categoryList.setSelectedIndex(0);
                    activityDuration.setText("");
                    activityRating.setValue(0);
                }
            });
        } catch (SQLException | ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
    }
}
