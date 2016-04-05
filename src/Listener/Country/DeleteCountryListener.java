package Listener.Country;

import Repository.CountryRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Deletes the selected countries when the deleteBtn button is clicked.
 * Also updates the GUI
 */
public class DeleteCountryListener implements ActionListener {
    private final JFrame frame;
    private final JList countryList;
    private final DefaultListModel<String> countryModelList;

    public DeleteCountryListener(JFrame frame, JList countryList, DefaultListModel<String> countryModelList) {
        super();
        this.frame = frame;
        this.countryList = countryList;
        this.countryModelList = countryModelList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Integer i :
                countryList.getSelectedIndices()) {
            try {
                new CountryRepository().deleteCountryByName(countryModelList.getElementAt(i));

                // Update the GUI
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        countryModelList.remove(i);
                    }
                });
            } catch (SQLException | ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(frame, e1.getMessage());
            }
        }
    }
}
