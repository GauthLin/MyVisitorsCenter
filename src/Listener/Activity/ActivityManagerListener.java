package Listener.Activity;

import Entity.Activity;
import Entity.Category;
import Entity.Country;
import Listener.ChangeCountryListener;
import Listener.CloseFrameListener;
import Repository.ActivityRepository;
import Repository.CategoryRepository;
import Repository.CountryRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class ActivityManagerListener implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e) {
        // Window creation
        JFrame frame = new JFrame("Administration des activités");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);


        // Panels
        JPanel newActivityPanel = new JPanel();
        newActivityPanel.setLayout(new BorderLayout());

        JPanel activitiesPanel = new JPanel();
        activitiesPanel.setLayout(new FlowLayout());

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // NEW ACTIVITY PANEL
        // First line of the new activity panel
        JPanel firstLine = new JPanel();
        firstLine.setLayout(new BoxLayout(firstLine, BoxLayout.X_AXIS));
        newActivityPanel.add(firstLine, BorderLayout.NORTH);

        // List of countries
        DefaultComboBoxModel<String> countryNameList = new DefaultComboBoxModel<>();
        countryNameList.addElement("-- Sélectionnez un pays --");
        try {
            CountryRepository countryRepository = new CountryRepository();
            for (Country country :
                    countryRepository.getCountries()) {
                countryNameList.addElement(country.getName());
            }
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
        JComboBox<String> countryList = new JComboBox<>(countryNameList);
        firstLine.add(countryList);

        // List of cities
        DefaultComboBoxModel<String> cityNameList = new DefaultComboBoxModel<>();
        cityNameList.addElement("-- Veuillez choisir un pays --");
        JComboBox<String> cityList = new JComboBox<>(cityNameList);
        firstLine.add(cityList);

        // Mise à jour des villes en fonction du pays choisi
        countryList.addActionListener(new ChangeCountryListener(countryList, cityNameList));

        // Activity name
        JTextField activityName = new JTextField();
        activityName.setToolTipText("Nom de la nouvelle activité");
        firstLine.add(activityName);

        // Second line of the new activity panel
        JPanel secondLine = new JPanel(new BorderLayout());
        newActivityPanel.add(secondLine, BorderLayout.CENTER);

        // Activity description
        JTextArea activityDescription = new JTextArea();
        activityDescription.setPreferredSize(new Dimension(300, 100));
        activityDescription.setToolTipText("Description de l'activité");
        JScrollPane activityDescriptionScroll = new JScrollPane(activityDescription);
        secondLine.add(activityDescriptionScroll, BorderLayout.CENTER);

        // More info panel over the new activity
        JPanel moreInfoPanel = new JPanel();
        moreInfoPanel.setLayout(new BoxLayout(moreInfoPanel, BoxLayout.Y_AXIS));
        secondLine.add(moreInfoPanel, BorderLayout.EAST);

        // List of categories
        DefaultComboBoxModel<String> categoryNameList = new DefaultComboBoxModel<>();
        categoryNameList.addElement("-- Sélectionnez une categorie --");
        try {
            CategoryRepository categoryRepository = new CategoryRepository();
            for (Category category :
                    categoryRepository.getCategories()) {
                categoryNameList.addElement(category.getName());
            }
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
        JComboBox<String> categoryList = new JComboBox<>(categoryNameList);
        moreInfoPanel.add(categoryList);

        // Duration
        JTextField activityDuration = new JTextField();
        activityDuration.setToolTipText("Durée de l'activité");
        moreInfoPanel.add(activityDuration);

        // Rating
        SpinnerNumberModel numberModel = new SpinnerNumberModel(0, 0, 5, 1);
        JSpinner activityRating = new JSpinner(numberModel);
        activityRating.setToolTipText("Cotation de l'activité");
        moreInfoPanel.add(activityRating);


        /*
         * The list for the categories
         */
        DefaultTableModel tableActivityModel = new DefaultTableModel();
        tableActivityModel.addColumn("#");
        tableActivityModel.addColumn("Pays");
        tableActivityModel.addColumn("Ville");
        tableActivityModel.addColumn("Nom");
        tableActivityModel.addColumn("Description");
        tableActivityModel.addColumn("Durée");
        tableActivityModel.addColumn("Cotation");
        // Add button
        JButton addActivityBtn = new JButton("Ajouter");
        addActivityBtn.addActionListener(new AddActivityListener(frame, countryList, cityList, activityName, activityDescription, categoryList, activityDuration, activityRating, tableActivityModel));
        moreInfoPanel.add(addActivityBtn);


        // List all the cities
        JTable activitiesTable = new JTable(tableActivityModel);
        activitiesTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        activitiesTable.setColumnSelectionAllowed(false);
        try {
            // Parcours toutes les villes afin de les afficher
            for (Activity activity :
                    new ActivityRepository().getActivities()) {
                Vector<String> vector = new Vector<>();

                vector.add(String.valueOf(activity.getId()));
                vector.add(activity.getCity().getCountry().getName());
                vector.add(activity.getCity().getName());
                vector.add(activity.getName());
                vector.add(activity.getDescription());
                vector.add(String.valueOf(activity.getTime().getTotalMinutes()));
                vector.add(String.valueOf(activity.getRating()));

                tableActivityModel.addRow(vector);
            }
        } catch (ClassNotFoundException | SQLException e1) {
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
        JScrollPane citiesScrollTable = new JScrollPane(activitiesTable);
        citiesScrollTable.setPreferredSize(new Dimension(500, 300));
        activitiesPanel.add(citiesScrollTable);


        /*
         * ACTION PANEL
         */
        JButton delActivityBtn = new JButton("Supprimer");
        delActivityBtn.addActionListener(new DeleteActivityListener(frame, activitiesTable));
        actionPanel.add(delActivityBtn);

        JButton closeBtn = new JButton("Fermer la fenêtre");
        closeBtn.addActionListener(new CloseFrameListener(frame));
        actionPanel.add(closeBtn);

        frame.add(newActivityPanel, BorderLayout.NORTH);
        frame.add(activitiesPanel, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
