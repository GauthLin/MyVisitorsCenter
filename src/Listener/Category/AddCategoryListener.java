package Listener.Category;

import Entity.Category;
import Repository.CategoryRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddCategoryListener implements ActionListener
{
    private final JFrame frame;
    private final JTextField catNameInput;
    private final DefaultListModel<String> listCatModel;

    public AddCategoryListener(JFrame frame, JTextField catNameInput, DefaultListModel<String> listCatModel) {
        super();
        this.frame = frame;
        this.catNameInput = catNameInput;
        this.listCatModel = listCatModel;
    }


    @Override
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
            JOptionPane.showMessageDialog(frame, e1.getMessage());
        }
    }
}
