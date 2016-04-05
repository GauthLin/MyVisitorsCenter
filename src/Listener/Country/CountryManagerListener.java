package Listener.Country;

import Entity.Country;
import Listener.CloseFrameListener;
import Repository.CountryRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * Display a new window to manage countries when the button CountryManagerBtn is clicked
 */
public class CountryManagerListener implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e) {
        // New window
        JFrame frame = new JFrame("Administration des pays");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Panels
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        JPanel countriesPanel = new JPanel();

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));


        // HEADER Panel
        headerPanel.add(new JLabel("Nom du pays"));

        JTextField countryNameTF = new JTextField(15);
        headerPanel.add(countryNameTF);

        JButton addCountryBtn = new JButton("Ajouter");
        DefaultListModel<String> countryModelList = new DefaultListModel<>();
        addCountryBtn.addActionListener(new AddCountryListener(frame, countryNameTF, countryModelList));
        headerPanel.add(addCountryBtn);


        // COUNTRIES panel
        try {
            for (Country country :
                    new CountryRepository().getCountries()) {
                countryModelList.addElement(country.getName());
            }
        } catch (SQLException | ClassNotFoundException e1) {
            // Show a message and close the window if SQL error
            JOptionPane.showMessageDialog(frame, e1.getMessage());
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
        JList<String> countryList = new JList<>(countryModelList);
        countryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        countryList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        countriesPanel.add(countryList);


        // ACTION panel
        JButton deleteBtn = new JButton("Supprimer");
        deleteBtn.addActionListener(new DeleteCountryListener(frame, countryList, countryModelList));
        actionPanel.add(deleteBtn);

        JButton closeBtn = new JButton("Fermer la fenêtre");
        closeBtn.addActionListener(new CloseFrameListener(frame));
        actionPanel.add(closeBtn);

        // Layout of the frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(countriesPanel, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
