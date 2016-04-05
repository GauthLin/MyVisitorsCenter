package Listener.City;


import Entity.City;
import Entity.Country;
import Repository.CityRepository;
import Repository.CountryRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Open the manager window of city. On this windows, cities can be created and deleted.
 *
 * All cities are listed.
 */
public class CityManagerListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // The list for the categories
        DefaultTableModel tableCityModel = new DefaultTableModel();
        tableCityModel.addColumn("Pays");
        tableCityModel.addColumn("Ville");

        // Window creation
        JFrame frame = new JFrame("Administration des villes");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Panels
        JPanel newCityPanel = new JPanel();
        newCityPanel.setLayout(new FlowLayout());

        JPanel citiesPanel = new JPanel();
        citiesPanel.setLayout(new FlowLayout());

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // HEADER panel
        DefaultComboBoxModel<String> countryModelList = new DefaultComboBoxModel<>();
        try {
            countryModelList.addElement("-- Choisissez un pays --");
            for (Country country :
                    new CountryRepository().getCountries()) {
                countryModelList.addElement(country.getName());
            }
        } catch (SQLException | ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
        JComboBox countryList = new JComboBox(countryModelList);
        newCityPanel.add(countryList);

        newCityPanel.add(new JLabel("Nom de la ville"));
        JTextField cityNameInput = new JTextField(15);
        newCityPanel.add(cityNameInput);
        JButton addCityBtn = new JButton("Ajouter");
        addCityBtn.addActionListener(new ActionListener() {
            @Override
            // Insert the new city in the database and update the GUI
            public void actionPerformed(ActionEvent e) {
                try {
                    // If no country selected
                    if (countryList.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(frame, "Veuillez choisir un pays !");
                        return;
                    }
                    // If a country is selected
                    Country country = new CountryRepository().getCountryByName(String.valueOf(countryList.getSelectedItem()));
                    City newCity = new City(cityNameInput.getText(), country.getId());

                    new CityRepository().insertCity(newCity);

                    // Update the GUI
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            Vector<String> vec = new Vector<>();
                            vec.add(String.valueOf(newCity.getCountryId()));
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
        });
        newCityPanel.add(addCityBtn);

        // List all the cities
        JTable citiesTable = new JTable(tableCityModel);
        citiesTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        citiesTable.setColumnSelectionAllowed(false);
        try {
            // Parcours toutes les villes afin de les afficher
            for (City city :
                    new CityRepository().getCities()) {
                Vector<String> vector = new Vector<>();
                vector.add(String.valueOf(city.getCountryId()));
                vector.add(city.getName());
                tableCityModel.addRow(vector);
            }
        } catch (ClassNotFoundException | SQLException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
        citiesPanel.add(citiesTable);

        // ACTION panel
        JButton delBtn = new JButton("Supprimer");
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Integer i :
                        citiesTable.getSelectedRows()) {
                    try {
                        City city = new CityRepository().getCityByName(String.valueOf(tableCityModel.getValueAt(i, 1)));
                        new CityRepository().deleteCity(city);
                        tableCityModel.removeRow(i);
                    } catch (SQLException | ClassNotFoundException e1) {
                        JOptionPane.showMessageDialog(frame, e1.getMessage());
                    }
                }
            }
        });
        actionPanel.add(delBtn);

        // Layout of the frame
        frame.add(newCityPanel, BorderLayout.NORTH);
        frame.add(citiesPanel, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
