package Listener;


import Entity.City;
import Entity.Country;
import Repository.CountryRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Cette classe permet de mettre à jour la liste des villes en fonction du pays sélectionné
 */
public class ChangeCountryListener implements ActionListener
{
    private final JComboBox<String> countryBox;
    private final DefaultComboBoxModel<String> listCityBox;

    public ChangeCountryListener (JComboBox<String> countryBox, DefaultComboBoxModel<String> listCityBox) {
        super();
        this.countryBox = countryBox;
        this.listCityBox = listCityBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        listCityBox.removeAllElements();

        try {
            CountryRepository countryRepository = new CountryRepository();

            // If the selected country is a real country and not the info message
            if (countryBox.getSelectedIndex() > 0) {
                Country country = countryRepository.getCountryByName((String) countryBox.getSelectedItem());

                listCityBox.addElement("-- Veuillez choisir une ville --");
                for (City city :
                        country.getCities()) {
                    listCityBox.addElement(city.getName());
                }
            } else {
                listCityBox.addElement("-- Veuillez choisir un pays --");
            }
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
    }
}
