package gui;




import javax.swing.*;
import java.awt.*;

public class CoverabilityTreePanel extends JPanel {
    private JTree tree ;
    private JScrollPane scrollPane;

    public CoverabilityTreePanel(){
        tree = new JTree();
        scrollPane = new JScrollPane(tree);
        this.setBorder(BorderFactory.createTitledBorder("Coverability Tree"));
        this.setPreferredSize(new Dimension(200,700));
    }
}
