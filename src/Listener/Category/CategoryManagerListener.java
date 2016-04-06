package Listener.Category;

import Entity.Category;
import Listener.CloseFrameListener;
import Repository.CategoryRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 * Open a new window to manage (delete and create) the categories of the activities.
 *
 * All categories are listed
 */
public class CategoryManagerListener implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e) {
        // The list for the categories
        DefaultListModel<String> listCatModel = new DefaultListModel<>();

        // Window creation
        JFrame frame = new JFrame("Administration des catégories");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panels
        JPanel newCategoryPanel = new JPanel();
        newCategoryPanel.setLayout(new FlowLayout());

        JPanel categoriesPanel = new JPanel();

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));


        // HEADER panel
        newCategoryPanel.add(new JLabel("Nom de la catégorie"));
        JTextField catNameInput = new JTextField(15);
        newCategoryPanel.add(catNameInput);

        JButton addCatBtn = new JButton("Ajouter");
        addCatBtn.addActionListener(new AddCategoryListener(frame,catNameInput, listCatModel));
        newCategoryPanel.add(addCatBtn);


        // List all the categories in a JList
        try {
            for (Category category: new CategoryRepository().getCategories()) {
                listCatModel.addElement(category.getName());
            }
        } catch (ClassNotFoundException | SQLException e1) {
            // Show a message and close the window if SQL error
            JOptionPane.showMessageDialog(frame, e1.getMessage());
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
        JList<String> catList = new JList<>(listCatModel);
        catList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        catList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        catList.setVisibleRowCount(-1);
        categoriesPanel.add(catList);


        // ACTION panel
        JButton delCatBtn = new JButton("Supprimer");
        delCatBtn.addActionListener(new DeleteCategoryListener(frame, listCatModel, catList));
        actionPanel.add(delCatBtn);

        JButton closeBt = new JButton("Fermer la fenêtre");
        closeBt.addActionListener(new CloseFrameListener(frame));
        actionPanel.add(closeBt);


        // Layout of the frame
        frame.add(newCategoryPanel, BorderLayout.NORTH);
        frame.add(categoriesPanel, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
