package gui;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {
    private  JTextArea logArea;

    public LogPanel()
    {
        logArea = new JTextArea();
        logArea.setEnabled(false);
        this.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(logArea);
        this.add(scrollPane,BorderLayout.CENTER);
        this.setBorder(BorderFactory.createTitledBorder("Logs"));
        this.setPreferredSize(new Dimension(getWidth(),100));
    }
}
