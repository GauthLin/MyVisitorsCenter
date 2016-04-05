package Listener.City;

import Entity.City;
import Repository.CityRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Deletes a city and updates the GUI
 */
public class DeleteCityListener implements ActionListener
{
    private final JFrame frame;
    private final JTable citiesTable;
    private final DefaultTableModel tableCityModel;

    public DeleteCityListener(JFrame frame, JTable citiesTable, DefaultTableModel tableCityModel) {
        this.frame = frame;
        this.citiesTable = citiesTable;
        this.tableCityModel = tableCityModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Integer i :
                citiesTable.getSelectedRows()) {
            try {
                CityRepository cityRepository = new CityRepository();

                City city = cityRepository.getCityByName(String.valueOf(tableCityModel.getValueAt(i, 1)));
                cityRepository.deleteCity(city);
                tableCityModel.removeRow(i);
            } catch (SQLException | ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(frame, e1.getMessage());
            }
        }
    }
}
