package Listener.Country;

import Entity.Country;
import Repository.CountryRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Adds the new country to the list and updates the GUI
 */
public class AddCountryListener implements ActionListener {
    private final JFrame frame;
    private final JTextField countryNameTF;
    private final DefaultListModel<String> countryModelList;

    public AddCountryListener(JFrame frame, JTextField countryNameTF, DefaultListModel<String> countryModelList) {
        super();
        this.frame = frame;
        this.countryNameTF = countryNameTF;
        this.countryModelList = countryModelList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String countryName = countryNameTF.getText();

        try {
            new CountryRepository().insertCountry(new Country(countryName));

            // Update the GUI
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    countryModelList.addElement(countryName);
                    countryNameTF.setText(null);
                    countryNameTF.requestFocusInWindow();
                }
            });
        } catch (SQLException | ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
    }
}
