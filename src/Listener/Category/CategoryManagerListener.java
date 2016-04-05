package Listener.Category;

import Entity.Category;
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
        addCatBtn.addActionListener(new ActionListener() {
            @Override
            // Insert the new category in the database and update the GUI
            public void actionPerformed(ActionEvent e) {
                try {
                    Category newCat = new Category(catNameInput.getText());

                    Category cat = new CategoryRepository().insertCategory(newCat);

                    // Update the GUI
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            listCatModel.addElement(cat.getName());
                            catNameInput.setText(null);
                            catNameInput.requestFocusInWindow();
                        }
                    });
                } catch (ClassNotFoundException | SQLException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
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
        JList catList = new JList(listCatModel);
        catList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        catList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        catList.setVisibleRowCount(-1);
        // Add a scrollbar to the list
        JScrollPane listScroller = new JScrollPane(catList);
        listScroller.setPreferredSize(new Dimension(300, 200));
        categoriesPanel.add(listScroller);

        // ACTION panel
        JButton delCatBtn = new JButton("Supprimer");
        delCatBtn.addActionListener(new ActionListener() {
            @Override
            // TODO: multiple delete
            public void actionPerformed(ActionEvent e) {
                for (Integer i :
                        catList.getSelectedIndices()) {
                    String catName = (String) listCatModel.getElementAt(i);
                    try {
                        new CategoryRepository().deleteCategoryByName(catName);
                        listCatModel.remove(i);
                    } catch (SQLException | ClassNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                    }
                }
            }
        });
        actionPanel.add(delCatBtn);

        frame.add(newCategoryPanel, BorderLayout.NORTH);
        frame.add(categoriesPanel, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
