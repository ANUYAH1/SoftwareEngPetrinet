package gui;




import logic.CoverabilityNode;
import logic.CoverabilityNodeInterface;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class CoverabilityTreePanel extends JPanel {
    private JTree tree ;
    private JScrollPane scrollPane;
    DefaultTreeModel model;
    DefaultMutableTreeNode rootNode;

    public CoverabilityTreePanel(){
        tree = new JTree();
        scrollPane = new JScrollPane(tree);
        rootNode = new DefaultMutableTreeNode();
        model = new DefaultTreeModel(rootNode);
        tree.setModel(model);
        this.setBorder(BorderFactory.createTitledBorder("Coverability Tree"));
        this.setPreferredSize(new Dimension(200,700));
    }
    public void loadTree(CoverabilityNodeInterface rootTreeNode){

       rootNode = createNode(new DefaultMutableTreeNode("CV Tree"),
               rootTreeNode);
        model.setRoot(rootNode);
        model.reload();



    }
    private DefaultMutableTreeNode createNode(DefaultMutableTreeNode parentNode
            ,CoverabilityNodeInterface node
                                              ){
        if(node.isTerminal())
            return  new DefaultMutableTreeNode(node.getPetriState());

        for (CoverabilityNodeInterface n : node.getChildren()){
            parentNode.add(createNode(parentNode,n));
        }
        return parentNode;
    }
}
