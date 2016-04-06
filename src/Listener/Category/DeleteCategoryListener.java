package Listener.Category;

import Repository.CategoryRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DeleteCategoryListener implements ActionListener
{
    private final JFrame frame;
    private final DefaultListModel<String> listCatModel;
    private final JList<String> catList;

    public DeleteCategoryListener(JFrame frame, DefaultListModel<String> listCatModel, JList<String> catList) {
        super();
        this.frame = frame;
        this.listCatModel = listCatModel;
        this.catList = catList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Integer i :
                catList.getSelectedIndices()) {
            String catName = listCatModel.getElementAt(i);
            try {
                new CategoryRepository().deleteCategoryByName(catName);
                listCatModel.remove(i);
            } catch (SQLException | ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(frame, e1.getMessage());
            }
        }
    }
}
