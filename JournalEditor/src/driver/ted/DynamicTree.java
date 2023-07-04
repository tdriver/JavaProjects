package driver.ted;
/*
 * This code is based on an example provided by Richard Stanford, 
 * a tutorial reader.
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

public class DynamicTree extends JPanel {
    protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    
    public final static int UNKNOWN_NODE = 0;
    public final static int ROOT_NODE = 1;
    public final static int CATEGORY_NODE = 2;    
    public final static int LEAF_NODE = 3;
    

    public DynamicTree() {
        rootNode = new DefaultMutableTreeNode("Untitled");
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());

        tree = new JTree(treeModel);
       // tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.putClientProperty("JTree.lineStyle", "Angled");
        tree.setShowsRootHandles(true);
        
        JScrollPane scrollPane = new JScrollPane(tree);
        setLayout(new GridLayout(1,0));
        add(scrollPane);
        
    }

    /**
     * Change the Root Node Name
     */
    public void setRootNodeName(String name){
        TreePath currentSelection = tree.getSelectionPath();
        rootNode.setUserObject(name);
                
        treeModel.reload();  
        tree.expandPath(currentSelection);
        tree.setSelectionPath(currentSelection);        
    }
    
    /** Remove all nodes except the root node. */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /** Remove the currently selected node. */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                tree.expandPath(currentSelection.getParentPath());
                tree.setSelectionPath(currentSelection.getParentPath());    
                return;
            }
        } 

        // Either there was no selection, or the root was selected.
        toolkit.beep();
    }
    /** Check for the last item in a node
     */
    public boolean checkForLast(){
        TreePath currentSelection = tree.getSelectionPath();
        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
       if(currentNode.getChildCount() == 0) return true;
        
        return false;
    }
    
    
    /** Add child to the currently selected node. */
    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)
                         (parentPath.getLastPathComponent());
        }

        return addObject(parentNode, child, true, false);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child) {
        return addObject(parent, child, true, false);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                            Object child, 
                                            boolean shouldBeVisible, boolean shouldSelect) {
        DefaultMutableTreeNode childNode = 
                new DefaultMutableTreeNode(child);

        if (parent == null) {
            parent = rootNode;
        }
        treeModel.insertNodeInto(childNode, parent, 
                                 parent.getChildCount());

        // Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        if(shouldSelect){
            TreePath path = new TreePath(childNode.getPath());
            tree.expandPath(path);
            tree.setSelectionPath(path);
        }
       return childNode;
    }
    
    public String getParentObject(){
        DefaultMutableTreeNode parentNode = null;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                     tree.getLastSelectedPathComponent();
        
        TreePath parentPath = tree.getSelectionPath();
        
        int ns = nodeSelected();

        if (ns == ROOT_NODE) {
            return (String) rootNode.getUserObject();
        } else {
            parentNode = (DefaultMutableTreeNode)
                         (parentPath.getLastPathComponent());
        }
        
        
        if(ns == LEAF_NODE){
            return (String)( (DefaultMutableTreeNode)node.getParent() ).getUserObject();
        }else{
            return (String)rootNode.getUserObject();
        }
    }
    
    public String getObject(){
        DefaultMutableTreeNode parentNode = null;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                     tree.getLastSelectedPathComponent();
        
        TreePath parentPath = tree.getSelectionPath();
        
        int ns = nodeSelected();

        if (ns == ROOT_NODE) {
            parentNode = rootNode;
        } else {
            parentNode = (DefaultMutableTreeNode)
                         (parentPath.getLastPathComponent());
        }
        
        if(ns == LEAF_NODE){
            return (String)node.getUserObject();
        }else{
            return (String)parentNode.getUserObject();
        }
    }
    
    
    public int nodeSelected(){       
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                                     tree.getLastSelectedPathComponent();
        
       if(node == null){
            return UNKNOWN_NODE;
       }

       if (node.isRoot()) {
           return ROOT_NODE;
       }
       if(node.isLeaf()){
           return LEAF_NODE;
       }else{
           return CATEGORY_NODE;
       }
    }
    
    
    public JTree getTree(){
        return tree;
    }
    
    public void reload(){
        treeModel.reload();
    }
    
    public DefaultMutableTreeNode getCategoryNode(String category){
        
      
      TreeNode[] path = rootNode.getPath();
      java.util.Enumeration children = path[0].children();
      DefaultMutableTreeNode node;
      do{
          node = (DefaultMutableTreeNode) children.nextElement();
          String test = (String)node.getUserObject();
          //System.out.println(test);
          if(test.compareTo(category) == 0) return node;
      }while(children.hasMoreElements());
      return rootNode;
    }

    class MyTreeModelListener implements TreeModelListener {
        public void treeNodesChanged(TreeModelEvent e) {
//            DefaultMutableTreeNode node;
//            node = (DefaultMutableTreeNode)
//                     (e.getTreePath().getLastPathComponent());
//
//            /*
//             * If the event lists children, then the changed
//             * node is the child of the node we've already
//             * gotten.  Otherwise, the changed node and the
//             * specified node are the same.
//             */
//            try {
//                int index = e.getChildIndices()[0];
//                node = (DefaultMutableTreeNode)
//                       (node.getChildAt(index));
//            } catch (NullPointerException exc) {}
//            

          //  System.out.println("The user has finished editing the node.");
           // System.out.println("New value: " + node.getUserObject());
        }
        public void treeNodesInserted(TreeModelEvent e) {
        }
        public void treeNodesRemoved(TreeModelEvent e) {
        }
        public void treeStructureChanged(TreeModelEvent e) {
        }        
    }
}
