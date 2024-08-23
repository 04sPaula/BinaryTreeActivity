// tree.java
// demonstrates binary tree
// to run this program: C>java TreeApp
import java.io.*;
import java.util.*;               // for Stack class
import java.util.Scanner;

////////////////////////////////////////////////////////////////
class TreeApp
   {
   public static void main(String[] args) throws IOException
      {
      Tree theTree = new Tree();
      boolean exit = false;
      int codProduto;
      Scanner sc;

      theTree.insert(50, 0.90, "Batata", 8);
      theTree.insert(25, 50.0, "Nutella 500g", 16);
      theTree.insert(75, 4.99, "escova de dentes", 24);
      theTree.insert(12, 16.29, "mortadela sadia 1kg", 11);

      while(!exit)
      {
         System.out.println("Enter C to create a new product, ");
         System.out.println("R to remove any item from the stock, ");
         System.out.println("F to find any item from the stock, ");
         System.out.println("S to show all the items currently on the stock, ");
         System.out.println("or E to exit: ");
         String choice = getString();
         switch (choice)
         {
            case "C":
               theTree.create();
               break;
            case "R":
               sc = new Scanner(System.in);
               codProduto = Integer.parseInt(sc.nextLine());
               theTree.delete(codProduto);
               break;
            case "F":
               System.out.print("Insert product's key: ");
               sc = new Scanner(System.in);
               codProduto = Integer.parseInt(sc.nextLine());
               theTree.find(codProduto, true);
               if (theTree.find(codProduto, false) == null) {
                  System.out.println("No item found with key " + codProduto);
               }
               break;
            case "S":
               theTree.traverse(2);
               break;
            case "E":
               exit = true;
               break;
            default:
               System.out.println("Invalid entry\n");
               break;
         }
      }

      }  // end main()
// -------------------------------------------------------------
   public static String getString() throws IOException
      {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
      }
// -------------------------------------------------------------
   public static char getChar() throws IOException
      {
      String s = getString();
      return s.charAt(0);
      }
//-------------------------------------------------------------
   public static int getInt() throws IOException
      {
      String s = getString();
      return Integer.parseInt(s);
      }
// -------------------------------------------------------------
   }  // end class TreeApp
////////////////////////////////////////////////////////////////

class Node
   {
   public int iData;          // data item
   public double pPrice;
   public String pName;
   public int pQuantity;
   public Node leftChild;         // this node's left child
   public Node rightChild;        // this node's right child

   public void displayNode()      // display ourself
      {
      System.out.print('{');
      System.out.print(iData);
      System.out.print(", ");
      System.out.print(pPrice);
      System.out.print(", ");
      System.out.print(pName);
      System.out.print(", ");
      System.out.print(pQuantity);
      System.out.print("} ");
      }
   }  // end class Node
////////////////////////////////////////////////////////////////
class Tree
   {
   private Node root;             // first node of tree

// -------------------------------------------------------------
   public Tree()                  // constructor
      { root = null; }            // no nodes in tree yet
// -------------------------------------------------------------
public Node find(int key, boolean isSuposedToPrint) {
   if (root == null) {            // Check if the tree is empty
      return null;               // Return null if it is empty
   }
   Node current = root;           // Start at root
   while (current != null) {      // Check if current is null
      if (key == current.iData) { // Found the matching node
         if (isSuposedToPrint) {
            System.out.println("Found it! Here you are: ");
            System.out.println(current.pName);
            System.out.println(current.pPrice);
            System.out.println(current.pQuantity);
         }
         return current;
      } else if (key < current.iData) { // Go left?
         current = current.leftChild;
      } else {                    // Or go right?
         current = current.rightChild;
      }
   }

   return null; // Didn't find the node with the specified key
}
// -------------------------------------------------------------
   public int insert(int id, double price, String name, int quantity)
      {
      Node newNode = new Node();    // make new node
      newNode.iData = id;           // insert data
      newNode.pPrice = price;
      newNode.pName = name;
      newNode.pQuantity = quantity;

      while (this.find(newNode.iData, false) != null)
      {
         Random random = new Random();
         newNode.iData = random.nextInt(1000000);
      }

      if(root == null)                // no node in root
         root = newNode;
      else                          // root occupied
         {
         Node current = root;       // start at root
         Node parent;
         while(true)                // (exits internally)
            {
            parent = current;
            if(id < current.iData)  // go left?
               {
               current = current.leftChild;
               if(current == null)  // if end of the line,
                  {                 // insert on left
                  parent.leftChild = newNode;
                     return id;
                  }
               }  // end if go left
            else                    // or go right?
               {
               current = current.rightChild;
               if(current == null)  // if end of the line
                  {                 // insert on right
                  parent.rightChild = newNode;
                     return id;
                  }
               }  // end else go right
            }  // end while
         }  // end else not root
         return id;
      }  // end insert()
// -------------------------------------------------------------
   public void create()
   {
      System.out.println("Informe o nome do produto: ");
      Scanner sc = new Scanner(System.in);
      String pName = sc.nextLine();
      System.out.println("Informe o preço do produto: ");
      double pPrice = sc.nextFloat();
      System.out.println("Informe a quantidade do produto: ");
      int pQuantity = sc.nextInt();

      Random random = new Random();
      int id = random.nextInt(1000000);

      int finalId = insert(id, pPrice, pName, pQuantity);
      System.out.println("Product registered sucessfully! Its code is: " + finalId);
   }
// -------------------------------------------------------------
   public boolean delete(int key) // delete node with given key
      {                           // (assumes non-empty list)
      Node current = root;
      Node parent = root;
      boolean isLeftChild = true;

      while(current.iData != key)        // search for node
         {
         parent = current;
         if(key < current.iData)         // go left?
            {
            isLeftChild = true;
            current = current.leftChild;
            }
         else                            // or go right?
            {
            isLeftChild = false;
            current = current.rightChild;
            }
         if(current == null)             // end of the line,
            return false;                // didn't find it
         }  // end while
      // found node to delete

      // if no children, simply delete it
      if(current.leftChild==null &&
                                   current.rightChild==null)
         {
         if(current == root)             // if root,
            root = null;                 // tree is empty
         else if(isLeftChild)
            parent.leftChild = null;     // disconnect
         else                            // from parent
            parent.rightChild = null;
         }

      // if no right child, replace with left subtree
      else if(current.rightChild==null)
         if(current == root)
            root = current.leftChild;
         else if(isLeftChild)
            parent.leftChild = current.leftChild;
         else
            parent.rightChild = current.leftChild;

      // if no left child, replace with right subtree
      else if(current.leftChild==null)
         if(current == root)
            root = current.rightChild;
         else if(isLeftChild)
            parent.leftChild = current.rightChild;
         else
            parent.rightChild = current.rightChild;

      else  // two children, so replace with inorder successor
         {
         // get successor of node to delete (current)
         Node successor = getSuccessor(current);

         // connect parent of current to successor instead
         if(current == root)
            root = successor;
         else if(isLeftChild)
            parent.leftChild = successor;
         else
            parent.rightChild = successor;

         // connect successor to current's left child
         successor.leftChild = current.leftChild;
         }  // end else two children
      // (successor cannot have a left child)
      return true;                                // success
      }  // end delete()
// -------------------------------------------------------------
   // returns node with next-highest value after delNode
   // goes to right child, then right child's left descendents
   private Node getSuccessor(Node delNode)
      {
      Node successorParent = delNode;
      Node successor = delNode;
      Node current = delNode.rightChild;   // go to right child
      while(current != null)               // until no more
         {                                 // left children,
         successorParent = successor;
         successor = current;
         current = current.leftChild;      // go to left child
         }
                                           // if successor not
      if(successor != delNode.rightChild)  // right child,
         {                                 // make connections
         successorParent.leftChild = successor.rightChild;
         successor.rightChild = delNode.rightChild;
         }
      return successor;
      }
// -------------------------------------------------------------
   public void traverse(int traverseType)
      {
      switch(traverseType)
         {
         case 1: System.out.print("\nPreorder traversal: ");
                 preOrder(root);
                 break;
         case 2: System.out.print("\nInorder traversal:  ");
                 inOrder(root);
                 break;
         case 3: System.out.print("\nPostorder traversal: ");
                 postOrder(root);
                 break;
         }
      System.out.println();
      }

       // -------------------------------------------------------------
   private void preOrder(Node localRoot)
      {
      if(localRoot != null)
         {
         System.out.print(localRoot.iData + ",  ");
         preOrder(localRoot.leftChild);
         preOrder(localRoot.rightChild);
         }
      }
// -------------------------------------------------------------
   private void inOrder(Node localRoot)
      {
      if(localRoot != null)
         {
         inOrder(localRoot.leftChild);
         System.out.print(" - Id: ");
         System.out.print(localRoot.iData + ",  ");
         System.out.print("Nome: ");
         System.out.print(localRoot.pName + ",  ");
         System.out.print("Preço: ");
         System.out.print(localRoot.pPrice + ",  ");
         System.out.print("Quantidade disponível: ");
         System.out.println(localRoot.pQuantity + ";");
         inOrder(localRoot.rightChild);
         }
      }
// -------------------------------------------------------------
   private void postOrder(Node localRoot)
      {
      if(localRoot != null)
         {
         postOrder(localRoot.leftChild);
         postOrder(localRoot.rightChild);
         System.out.print(localRoot.iData + " ");
         }
      }
// -------------------------------------------------------------
   public void displayTree()
      {
      Stack globalStack = new Stack();
      globalStack.push(root);
      int nBlanks = 32;
      boolean isRowEmpty = false;
      System.out.println(
      "......................................................");
      while(isRowEmpty==false)
         {
         Stack localStack = new Stack();
         isRowEmpty = true;

         for(int j=0; j<nBlanks; j++)
            System.out.print(' ');

         while(globalStack.isEmpty()==false)
            {
            Node temp = (Node)globalStack.pop();
            if(temp != null)
               {
               System.out.print(temp.iData);
               localStack.push(temp.leftChild);
               localStack.push(temp.rightChild);

               if(temp.leftChild != null ||
                                   temp.rightChild != null)
                  isRowEmpty = false;
               }
            else
               {
               System.out.print("--");
               localStack.push(null);
               localStack.push(null);
               }
            for(int j=0; j<nBlanks*2-2; j++)
               System.out.print(' ');
            }  // end while globalStack not empty
         System.out.println();
         nBlanks /= 2;
         while(localStack.isEmpty()==false)
            globalStack.push( localStack.pop() );
         }  // end while isRowEmpty is false
      System.out.println(
      "......................................................");
      }  // end displayTree()
// -------------------------------------------------------------
   }  // end class Tree
////////////////////////////////////////////////////////////////
