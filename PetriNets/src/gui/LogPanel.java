package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

public class LogPanel extends JPanel {
    private  JPanel logArea;
    private final Color ERRORCOLOR = Color.red;
    private final Color INFOCOLOR = Color.green;
    private final Color DEFAULTCOLOR = Color.black;
    private ArrayList<LogUIModel> logs ;
    private JScrollPane scrollPane ;
    public LogPanel()
    {
        logs = new ArrayList<>();
        logArea = new JPanel();
        logArea.setBackground(Color.WHITE);
        this.setBackground(Color.GRAY);
        logArea.setLayout(new BoxLayout(logArea,BoxLayout.Y_AXIS));
        logArea.setEnabled(false);
        this.setLayout(new BorderLayout());
        scrollPane = new JScrollPane(logArea);
        this.add(scrollPane,BorderLayout.CENTER);
        this.setBorder(BorderFactory.createTitledBorder("Logs"));
        this.setPreferredSize(new Dimension(getWidth(),100));

    }

    public void log(LogUIModel log) {
        //only log data when
        // the panel is enabled
        if (this.isEnabled()) {

           JLabel label = new JLabel(log.toString());
           // paints the label based on the
            // log model
           label.setForeground(log.getType() ==
                   LogType.INFORMATION?
                   INFOCOLOR:log.getType() ==LogType.ERROR?
                   ERRORCOLOR:DEFAULTCOLOR);

           logArea.add(label);
           logs.add(log);
           revalidate();
           int max = scrollPane.getVerticalScrollBar().getMaximum();
            scrollPane.getVerticalScrollBar().setValue(
                   max
           );


        }

    }
    public void clearLog (){
        logArea.removeAll();
        logs.clear();
        revalidate();
    }
}
