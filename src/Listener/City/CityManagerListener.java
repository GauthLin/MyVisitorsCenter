package Listener.City;


import Entity.City;
import Entity.Country;
import Listener.CloseFrameListener;
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
        // Window creation
        JFrame frame = new JFrame("Administration des villes");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);

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
        JComboBox<String> countryList = new JComboBox<>(countryModelList);
        newCityPanel.add(countryList);

        newCityPanel.add(new JLabel("Nom de la ville"));
        JTextField cityNameInput = new JTextField(15);
        newCityPanel.add(cityNameInput);

        JButton addCityBtn = new JButton("Ajouter");


        /*
         * The list for the categories
         */
        DefaultTableModel tableCityModel = new DefaultTableModel();
        tableCityModel.addColumn("Pays");
        tableCityModel.addColumn("Ville");
        addCityBtn.addActionListener(new AddCityListener(frame, countryList, cityNameInput, tableCityModel));
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
                vector.add(city.getCountry().getName());
                vector.add(city.getName());
                tableCityModel.addRow(vector);
            }
        } catch (ClassNotFoundException | SQLException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
        JScrollPane citiesScrollTable = new JScrollPane(citiesTable);
        citiesScrollTable.setPreferredSize(new Dimension(400, 350));
        citiesPanel.add(citiesScrollTable);


        // ACTION panel
        JButton delBtn = new JButton("Supprimer");
        delBtn.addActionListener(new DeleteCityListener(frame, citiesTable, tableCityModel));
        actionPanel.add(delBtn);

        JButton closeBt = new JButton("Fermer la fenêtre");
        closeBt.addActionListener(new CloseFrameListener(frame));
        actionPanel.add(closeBt);


        // Layout of the frame
        frame.add(newCityPanel, BorderLayout.NORTH);
        frame.add(citiesPanel, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
