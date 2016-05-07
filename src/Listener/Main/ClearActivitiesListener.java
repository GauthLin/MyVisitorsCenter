package Listener.Main;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clear the table of activities
 */
public class ClearActivitiesListener implements ActionListener{
    private final DefaultTableModel tableActivityModel;

    public ClearActivitiesListener(DefaultTableModel tableActivityModel) {
        super();
        this.tableActivityModel = tableActivityModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Removes all rows of the table
        int rowCount = tableActivityModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableActivityModel.removeRow(i);
        }
    }
}
