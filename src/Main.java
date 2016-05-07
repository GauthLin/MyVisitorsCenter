import Entity.*;
import Listener.Activity.ActivityManagerListener;
import Listener.Category.CategoryManagerListener;
import Listener.ChangeCountryListener;
import Listener.City.CityManagerListener;
import Listener.Country.CountryManagerListener;
import Listener.Main.ClearActivitiesListener;
import Listener.Main.MoreInfoListener;
import Listener.Main.SearchScheduleListener;
import Repository.CategoryRepository;
import Repository.CountryRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        JFrame frame = new JFrame("Centre de visiteurs");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null); // Centre la fenêtre

        /*
         * PANELS
         */
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel listActivitiesPanel = new JPanel();
        listActivitiesPanel.setLayout(new BoxLayout(listActivitiesPanel, BoxLayout.Y_AXIS));
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));


        /*
         * HEADER
         */
        DefaultComboBoxModel<String> countryNameList = new DefaultComboBoxModel<>();
        countryNameList.addElement("-- Sélectionnez un pays --");
        CountryRepository countryRepository = new CountryRepository();
        for (Country country :
                countryRepository.getCountries()) {
            countryNameList.addElement(country.getName());
        }
        JComboBox<String> countryList = new JComboBox<>(countryNameList);

        DefaultComboBoxModel<String> cityNameList = new DefaultComboBoxModel<>();
        cityNameList.addElement("-- Choisissez un pays svp --");
        JComboBox<String> cityList = new JComboBox<>(cityNameList);

        countryList.addActionListener(new ChangeCountryListener(countryList, cityNameList));

        JLabel labelNbJours = new JLabel("Nombre de jours");
        SpinnerNumberModel model1 = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner sNbJour = new JSpinner(model1);
        JButton searchBtn = new JButton("Chercher");

        headerPanel.add(countryList);
        headerPanel.add(cityList);
        headerPanel.add(labelNbJours);
        headerPanel.add(sNbJour);
        headerPanel.add(searchBtn);


        /*
         * Categories filters
         */
        DefaultListModel<String> catModelList = new DefaultListModel<>();
        JList<String> catList = new JList<>(catModelList);
        for (Category category :
                new CategoryRepository().getCategories()) {
            catModelList.addElement(category.getName());
        }
        catList.setBorder(BorderFactory.createLineBorder(Color.black));
        catList.setPreferredSize(new Dimension(100, 400));


        /*
         * Activity list depending of the city chosen
         */
        DefaultTableModel tableActivityModel = new DefaultTableModel();
        tableActivityModel.addColumn("#");
        tableActivityModel.addColumn("Nom");
        tableActivityModel.addColumn("Description");
        tableActivityModel.addColumn("Durée");
        tableActivityModel.addColumn("Cotation");
        JTable listActivities = new JTable(tableActivityModel);
        listActivities.setColumnSelectionAllowed(false);
        listActivities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listActivitiesPanel.add(new JScrollPane(listActivities));

        JButton moreInfoBtn = new JButton("Plus d'info");
        moreInfoBtn.setToolTipText("Cliquez pour obtenir plus d'informations sur l'activité sélectionnée");
        moreInfoBtn.addActionListener(new MoreInfoListener(frame, listActivities));
        actionPanel.add(moreInfoBtn);

        JButton clearBtn = new JButton("Effacer");
        clearBtn.addActionListener(new ClearActivitiesListener(tableActivityModel));
        actionPanel.add(clearBtn);

        listActivitiesPanel.add(actionPanel);

        // Add listener to button
        searchBtn.addActionListener(new SearchScheduleListener(frame, cityList, sNbJour, tableActivityModel));


        /*
         * ADMIN PANEL
         */
        JButton countryManagerBtn = new JButton("Gestion des pays");
        countryManagerBtn.addActionListener(new CountryManagerListener());
        adminPanel.add(countryManagerBtn);

        JButton cityManagerBtn = new JButton("Gestion des villes");
        cityManagerBtn.addActionListener(new CityManagerListener());
        adminPanel.add(cityManagerBtn);

        JButton activityManagerBtn = new JButton("Gestion des activités");
        activityManagerBtn.addActionListener(new ActivityManagerListener());
        adminPanel.add(activityManagerBtn);

        JButton categoryManagerBtn = new JButton("Gestion des catégories");
        categoryManagerBtn.addActionListener(new CategoryManagerListener());

        adminPanel.add(categoryManagerBtn);


        /*
         * Main window
         */
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(catList, BorderLayout.WEST);
        frame.add(adminPanel, BorderLayout.SOUTH);
        frame.add(listActivitiesPanel, BorderLayout.CENTER);

        frame.setVisible(true); // Affiche la fenêtre
    }
}
