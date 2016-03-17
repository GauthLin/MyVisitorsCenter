import Entity.Activity;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Centre de visiteurs");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null); // Centre la fenêtre

        // HEADER
        JLabel labelVille = new JLabel("Nom de la ville");
        JTextField tVille = new JTextField(15);

        JLabel labelNbJours = new JLabel("Nombre de jours");
        SpinnerNumberModel model1 = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner sNbJour = new JSpinner(model1);
        JButton bSearch = new JButton("Chercher");
        JPanel panel = new JPanel();
        panel.add(labelVille);
        panel.add(tVille);
        panel.add(labelNbJours);
        panel.add(sNbJour);
        panel.add(bSearch);

        // Liste des filtres
        DefaultListModel<String> modelList = new DefaultListModel<>();
        JList<String> listFiltres = new JList<>(modelList);
        modelList.addElement("Sport");
        modelList.addElement("Détente");
        modelList.addElement("Aventure");
        listFiltres.setBorder(BorderFactory.createLineBorder(Color.black));

        JList<Activity> listActivities = new JList<>();

        frame.add(panel, BorderLayout.NORTH);
        frame.add(listFiltres, BorderLayout.WEST);
        frame.add(listActivities, BorderLayout.CENTER);

        frame.setVisible(true); // Affiche la fenêtre
    }
}
