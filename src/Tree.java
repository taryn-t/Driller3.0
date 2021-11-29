public class Tree {
    //made with help from https://www.javatpoint.com/avl-tree-program-in-java
    static class Node{
        DrillingRecord data;
        int height;
        Node leftChild;
        Node rightChild;

        //default constructor
        public Node(){
            data = null;
            height = 0;
            leftChild = null;
            rightChild = null;
        }

        public Node(DrillingRecord record){
            data = record;
            height = 0;
            leftChild = null;
            rightChild = null;
        }
    }

    public static class AVLTree{

        private Node rootNode;
        private String rootDate;
        public AVLTree(){
            rootNode = null;
            rootDate ="";
        }

        //makes tree empty
        public void removeAll() {
            rootNode = null;
        }

        //checks to see if tree is empty
        public boolean checkEmpty(){
            boolean empty;
            if(rootNode == null){
                empty = true;
            }else{
                empty = false;
            }
            return empty;
        }

        //get the height of the AVL Tree
        private int getHeight(Node node )
        {
            return node.height;
        }

        // get the maximum height from left and right node
        private int getMaxHeight(int leftNodeHeight, int rightNodeHeight)
        {
            return Math.max(leftNodeHeight, rightNodeHeight);
        }

        //  perform rotation of binary tree node with left child
        private Node rotateWithLeftChild(Node baseNode)
        {
            //make new node to store baseNode's left child
            Node parent = baseNode.leftChild;
            //store the right child of the baseNode as the left child of the node created above
            baseNode.leftChild = parent.rightChild;
            //set the right child of the created node to the given node itself
            parent.rightChild = baseNode;
            //set the height of the baseNode to the greatest height between the children plus one
            baseNode.height = getMaxHeight( getHeight( baseNode.leftChild ), getHeight( baseNode.rightChild ) ) + 1;
            //do the same for the new node
            parent.height = getMaxHeight( getHeight( parent.leftChild ), baseNode.height ) + 1;
            //return the new node
            return parent;
        }

        //  perform rotation of binary tree node with right child
        private Node rotateWithRightChild(Node baseNode)
        {
            //make new node to store baseNode's right child
            Node parent = baseNode.rightChild;
            //store the left child of the baseNode as the right child of the node created above
            baseNode.rightChild = parent.leftChild;
            //set the left child of the created node to the given node itself
            parent.leftChild = baseNode;
            //set the height of the baseNode to the greatest height between the children plus one
            baseNode.height = getMaxHeight( getHeight( baseNode.leftChild ), getHeight( baseNode.rightChild ) ) + 1;
            //do the same for the new node
            parent.height = getMaxHeight( getHeight( parent.rightChild ), baseNode.height ) + 1;
            //return the new node
            return parent;
        }

        //preforms double rotation of left child
        private Node doubleWithLeftChild(Node baseNode)
        {
            baseNode.leftChild = rotateWithRightChild( baseNode.leftChild );
            return rotateWithLeftChild( baseNode );
        }

        //preforms double rotation of right child
        private Node doubleWithRightChild(Node baseNode)
        {
            baseNode.rightChild = rotateWithLeftChild( baseNode.rightChild );
            return rotateWithRightChild( baseNode );
        }

        // create insertElement() to insert element to to the AVL Tree
        public void insertElement(DrillingRecord record, int index)
        {
                rootNode = insertElement(record, rootNode, index);

        }

        //create insertElement() method to insert data in the AVL Tree recursively
        private Node insertElement(DrillingRecord record, Node node, int index)
        {
            //check whether the node is null or not
            if(rootNode == null){
                node = new Node(record);
                rootDate = record.getDate();
            }
            if(record.getDate().equals(rootDate) && !record.getFloatError()){
                if (node == null){
                    node = new Node(record);
                }
                //insert a node in case when the given element is lesser than the element of the root node
                else if (record.compareTo(node.data.getTime()) < 0)
                {
                    //insert node as left child
                    node.leftChild = insertElement( record, node.leftChild, index );
                    if(node.leftChild != null && node.rightChild != null){
                        if( getHeight( node.leftChild ) - getHeight( node.rightChild ) == 2 ){
                            if( record.compareTo(node.leftChild.data.getTime()) <0  )
                                //if record is less than the left child then move the left to the right and put the record in the left
                                node = rotateWithLeftChild( node );
                            else{
                                //double rotate
                                node = doubleWithLeftChild( node );
                            }
                        }
                    }
                }
                //insert node as right child
                else if( record.compareTo(node.data.getTime()) > 0 )
                {
                    node.rightChild = insertElement( record, node.rightChild, index );
                    if(node.rightChild != null && node.leftChild  != null){
                        if( getHeight( node.rightChild ) - getHeight( node.leftChild ) == 2 ){
                            if( record.compareTo(node.rightChild.data.getTime()) > 0 )
                                //if record is greater than the left child then move the right to the left and put the record in the right
                                node = rotateWithRightChild( node );
                            else{
                                //double rotate
                                node = doubleWithRightChild( node );
                            }
                        }
                    }
                }
                if(node.leftChild == null && node.rightChild == null  ){
                    node.height = 1;
                }
                else if(node.leftChild == null && node.rightChild != null ){
                    node.height = getMaxHeight( 0, getHeight( node.rightChild ) ) + 1;
                }
                else if(node.leftChild != null && node.rightChild == null){
                    node.height = getMaxHeight( getHeight( node.leftChild ), 0 ) + 1;
                }
                else{
                    node.height = getMaxHeight( getHeight( node.leftChild ), getHeight( node.rightChild ) ) + 1;
                }
            }

            return node;

        }

        //inorder traversal recursive
        public void inorderTraversal()
        {
            inorderTraversal(rootNode);
        }

        private void inorderTraversal(Node head)
        {
            if (head != null)
            {
                inorderTraversal(head.leftChild);
                System.out.print(head.data +"\n");
                inorderTraversal(head.rightChild);
            }
        }

        //preorder traversal
        public void preorderTraversal()
        {
            preorderTraversal(rootNode);
        }
        private void preorderTraversal(Node head)
        {
            if (head != null)
            {
                System.out.print(head.data+"\n");
                preorderTraversal(head.leftChild);
                preorderTraversal(head.rightChild);
            }
        }

        //post order traversal recursive
        public void postorderTraversal()
        {
            postorderTraversal(rootNode);
        }

        private void postorderTraversal(Node head)
        {
            if (head != null)
            {
                postorderTraversal(head.leftChild);
                postorderTraversal(head.rightChild);
                System.out.print(head.data+"\n");
            }
        }
        //create getTotalNumberOfNodes() method to get total number of nodes in the AVL Tree
        public int getTotalNumberOfNodes()
        {
            return getTotalNumberOfNodes(rootNode);
        }
        private int getTotalNumberOfNodes(Node head)
        {
            if (head == null)
                return 0;
            else
            {
                int length = 1;
                length = length + getTotalNumberOfNodes(head.leftChild);
                length = length + getTotalNumberOfNodes(head.rightChild);
                return length;
            }
        }
        public Node getRootNode(){
            return rootNode;
        }

    }


}
