import Entity.*;
import Listener.Category.CategoryManagerListener;
import Listener.City.CityManagerListener;
import Listener.Country.CountryManagerListener;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBManager dbmanager = new DBManager();

        JFrame frame = new JFrame("Centre de visiteurs");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null); // Centre la fenêtre

        // HEADER
        DefaultComboBoxModel<String> countryNameList = new DefaultComboBoxModel<>();
        countryNameList.addElement("-- Sélectionnez un pays --");
        for (Country country :
                new DBManager().getCountries()) {
            countryNameList.addElement(country.getName());
        }
        JComboBox countryList = new JComboBox(countryNameList);

        DefaultComboBoxModel<String> cityNameList = new DefaultComboBoxModel<>();
        cityNameList.addElement("-- Choisissez un pays svp --");
        JComboBox cityList = new JComboBox(cityNameList);
        cityList.setEnabled(false);

        JLabel labelNbJours = new JLabel("Nombre de jours");
        SpinnerNumberModel model1 = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner sNbJour = new JSpinner(model1);
        JButton bSearch = new JButton("Chercher");
        JPanel headerPanel = new JPanel();

        headerPanel.add(countryList);
        headerPanel.add(cityList);
        headerPanel.add(labelNbJours);
        headerPanel.add(sNbJour);
        headerPanel.add(bSearch);

        // Categories filters
        DefaultListModel<String> modelList = new DefaultListModel<>();
        JList<String> catList = new JList<>(modelList);
        for (Category category :
                dbmanager.getCategories()) {
            modelList.addElement(category.getName());
        }
        catList.setBorder(BorderFactory.createLineBorder(Color.black));
        catList.setPreferredSize(new Dimension(100, 400));

        // Activity list depending of the city chosen
        JList<Activity> listActivitiesPanel = new JList<>();

        // Admin panel
        JPanel adminPanel = new JPanel();

        JButton countryManagerBtn = new JButton("Gestion des pays");
        countryManagerBtn.addActionListener(new CountryManagerListener());
        adminPanel.add(countryManagerBtn);

        JButton cityManagerBtn = new JButton("Gestion des villes");
        cityManagerBtn.addActionListener(new CityManagerListener());
        adminPanel.add(cityManagerBtn);

        JButton activityManagerBtn = new JButton("Gestion des activités");
        adminPanel.add(activityManagerBtn);

        JButton categoryManagerBtn = new JButton("Gestion des catégories");
        categoryManagerBtn.addActionListener(new CategoryManagerListener());

        adminPanel.add(categoryManagerBtn);

        // Main window
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(catList, BorderLayout.WEST);
        frame.add(adminPanel, BorderLayout.SOUTH);
        frame.add(listActivitiesPanel, BorderLayout.CENTER);

        frame.setVisible(true); // Affiche la fenêtre
    }
}
