package tree.binary;
  
  public class BinaryTreeNode {
      
      private String data;
      private BinaryTreeNode left, right;
      
      BinaryTreeNode (String s) {
          data = s;
          left = null;
          right = null;
      }
      
      public void add (String s, String child) {
          if (child.equals("L")) {
              left = new BinaryTreeNode(s);
          } else if (child.equals("R")) {
              right = new BinaryTreeNode(s);
          } else {
              throw new IllegalArgumentException();
          }
      }
      
      public BinaryTreeNode getChild (String child) {
          return (child.equals("L")) ? left : right;
      }
      
      public String getString () {
          return data;
      }
      
      public void doubleTree () {
          doubleTree(this);
      }
      
      private void doubleTree (BinaryTreeNode n) {
    	  BinaryTreeNode oldL;
    	  if (n == null) {return;}
    	  doubleTree(n.left);
    	  doubleTree(n.right);
    	  oldL = n.left;
    	  n.left = new BinaryTreeNode(n.data);
    	  n.left.left = oldL;
      }
      
      public static boolean sameTree (BinaryTreeNode n1, BinaryTreeNode n2) {
          if (n1 == null && n2 == null) {
        	  return true;
          } else if (n1 != null && n2 != null) {
        	  return (n1.data == n2.data && sameTree(n1.left, n2.left) && sameTree(n1.right,n2.right));
          } else {
        	  return false;
          }
      }
  }
