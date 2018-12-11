package gui;




import logic.CoverabilityNode;
import logic.CoverabilityNodeInterface;
import logic.TransitionInterface;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.Arrays;

public class CoverabilityTreePanel extends JPanel {
    private JTree tree ;
    private JScrollPane scrollPane;
    DefaultTreeModel model;
    DefaultMutableTreeNode rootNode;

    public CoverabilityTreePanel(){
        tree = new JTree();
        scrollPane = new JScrollPane(tree);
        rootNode = new DefaultMutableTreeNode("");
        model = new DefaultTreeModel(rootNode);
        tree.setModel(model);
        this.setBorder(BorderFactory.createTitledBorder("Coverability Tree"));
        this.setPreferredSize(new Dimension(200,700));
        this.setLayout(new BorderLayout());
        this.add(scrollPane,BorderLayout.CENTER);
    }
    public void loadTree(CoverabilityNodeInterface rootTreeNode){
        TransitionInterface transition =rootTreeNode.usedTransition();
        String petriState = Arrays.toString(rootTreeNode.getPetriState());
       rootNode.add(new DefaultMutableTreeNode("["+petriState+ "]" +(transition!=null?(": "+
               rootTreeNode.usedTransition().getName()):"")));

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
