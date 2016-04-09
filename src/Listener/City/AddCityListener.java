package Listener.City;

import Entity.City;
import Entity.Country;
import Repository.CityRepository;
import Repository.CountryRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Adds a new city when the addBtn is clicked and updates the GUI
 */
public class AddCityListener implements ActionListener
{
    private final JFrame frame;
    private final JComboBox<String> countryList;
    private final JTextField cityNameInput;
    private final DefaultTableModel tableCityModel;

    public AddCityListener(JFrame frame, JComboBox<String> countryList, JTextField cityNameInput, DefaultTableModel tableCityModel) {
        this.frame = frame;
        this.countryList = countryList;
        this.cityNameInput = cityNameInput;
        this.tableCityModel = tableCityModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // If no country selected
            if (countryList.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(frame, "Veuillez choisir un pays !");
                return;
            }
            // If at least one country is selected
            Country country = new CountryRepository().getCountryByName(String.valueOf(countryList.getSelectedItem()));
            City newCity = new City(cityNameInput.getText(), country);

            new CityRepository().insertCity(newCity);

            // Update the GUI
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Vector<String> vec = new Vector<>();
                    vec.add(String.valueOf(newCity.getCountry().getName()));
                    vec.add(newCity.getName());
                    tableCityModel.addRow(vec);
                    cityNameInput.setText(null);
                    cityNameInput.requestFocusInWindow();
                    countryList.setSelectedIndex(0);
                }
            });
        } catch (ClassNotFoundException | SQLException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
    }
}
