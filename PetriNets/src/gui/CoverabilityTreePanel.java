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
        rootNode = new DefaultMutableTreeNode("Coverability Tree");
        model = new DefaultTreeModel(rootNode);
        tree.setModel(model);

        this.setPreferredSize(new Dimension(230,700));
        this.setLayout(new BorderLayout());
        this.add(scrollPane,BorderLayout.CENTER);
    }
    public void loadTree(CoverabilityNodeInterface rootTreeNode){
        rootNode.removeAllChildren();
        recurSiveTree(rootTreeNode,rootNode);


        model.setRoot(rootNode);
        model.reload();
        expandAllNodes(tree,0,tree.getRowCount());


    }

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
        for(int i=startingIndex;i<rowCount;++i){
            tree.expandRow(i);
        }

        if(tree.getRowCount()!=rowCount){
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }
    public void recurSiveTree (CoverabilityNodeInterface node,DefaultMutableTreeNode root){
        if(node == null)
        {
            return ;
        }
        DefaultMutableTreeNode currentNode =
                new DefaultMutableTreeNode("["+Arrays.toString(node.getPetriState())+ "]"
                +(node.usedTransition()!=null?(": "+
                node.usedTransition().getName()):""));
        root.add(currentNode);
        for (CoverabilityNodeInterface c : node.getChildren()){

           recurSiveTree(c,currentNode);
        }

    }
    public void clear(){
        rootNode.removeAllChildren();
        model.reload();
    }

}
