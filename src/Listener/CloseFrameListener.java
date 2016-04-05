package Listener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Closes the frame
 */
public class CloseFrameListener implements ActionListener
{
    private final JFrame frame;

    public CloseFrameListener(JFrame frame) {
        super();
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
    }
}
