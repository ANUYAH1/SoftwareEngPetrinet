package gui;




import logic.CoverabilityNode;
import logic.CoverabilityNodeInterface;
import logic.TransitionInterface;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;

public class SidePanel extends JPanel {
    private JTree tree ;
    private JScrollPane scrollPane;
    DefaultTreeModel model;
    DefaultMutableTreeNode rootNode;
    JTextArea textArea;
    private  JSplitPane splitPane;
    private boolean firstResize = true;
    private JScrollPane textScrollPane;
    private JScrollPane treeScrollPane;
    public SidePanel(){
        tree = new JTree();

        textArea = new JTextArea();
        textArea.setEnabled(false);
        textScrollPane = new JScrollPane(textArea);
        treeScrollPane = new JScrollPane(tree);



        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeScrollPane);
        splitPane.setBottomComponent(textScrollPane);

        splitPane.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                    splitPane.setDividerLocation(0.5);
                    //firstResize = false;

            }
        });
        rootNode = new DefaultMutableTreeNode("Coverability Tree");
        model = new DefaultTreeModel(rootNode);
        tree.setModel(model);

        this.setPreferredSize(new Dimension(230,700));
        this.setLayout(new BorderLayout());
        this.add(splitPane,BorderLayout.CENTER);
    }
    public void loadTree(CoverabilityNodeInterface rootTreeNode){
        rootNode.removeAllChildren();
        recurSiveTree(rootTreeNode,rootNode);


        model.setRoot(rootNode);
        model.reload();
        expandAllNodes(tree,0,tree.getRowCount());


    }

    /**
     * this updates other
     * information
     */
    public void setText(String text){
        textArea.setText(text);

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
        textArea.setText("");
       revalidate();

    }

}
