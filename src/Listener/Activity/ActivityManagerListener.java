package Listener.Activity;

import Entity.Country;
import Repository.CountryRepository;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

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
        newActivityPanel.setLayout(new FlowLayout());

        JPanel activitiesPanel = new JPanel();
        activitiesPanel.setLayout(new FlowLayout());

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


        // NEW ACTIVITY PANEL
        // First line of the new activity panel
        JPanel firstLine = new JPanel();
        firstLine.setLayout(new BoxLayout(firstLine, BoxLayout.X_AXIS));
        newActivityPanel.add(firstLine);

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
        cityList.setEnabled(false);
        firstLine.add(cityList);

        // Activity name
        JTextField activityName = new JTextField(20);
        activityName.setToolTipText("Nom de la nouvelle activité");
        firstLine.add(activityName);

        // Second line of the new activity panel
        JPanel secondLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newActivityPanel.add(secondLine);

        // Activity description
        JTextArea activityDescription = new JTextArea(7, 40);
        activityDescription.setToolTipText("Description de l'activité");
        secondLine.add(activityDescription);

        // More info panel over the new activity
        JPanel moreInfoPanel = new JPanel();
        moreInfoPanel.setLayout(new BoxLayout(moreInfoPanel, BoxLayout.Y_AXIS));
        secondLine.add(moreInfoPanel);

        // Duration
        SpinnerDateModel spinnerModel = new SpinnerDateModel();
        spinnerModel.setValue(Calendar.getInstance().getTime());

        JSpinner activityDuration = new JSpinner(spinnerModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(activityDuration, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        activityDuration.setEditor(editor);
        activityDuration.setToolTipText("Durée de l'activité");
        moreInfoPanel.add(activityDuration);

        // Rating
        SpinnerNumberModel numberModel = new SpinnerNumberModel(0, 0, 5, 1);
        JSpinner activityRating = new JSpinner(numberModel);
        activityRating.setToolTipText("Cotation de l'activité");
        moreInfoPanel.add(activityRating);

        // Add button
        JButton addActivityBtn = new JButton("Ajouter");
        addActivityBtn.addActionListener(new AddActivityListener());
        moreInfoPanel.add(addActivityBtn);

        frame.add(newActivityPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}
