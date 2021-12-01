
public class Tree {
    //made with help from https://www.javatpoint.com/avl-tree-program-in-java
    static class Node{
        DrillingRecord data;
        int height;
        Node leftChild;
        Node rightChild;
        String key;
        //default constructor
        public Node(){
            data = null;
            height = 0;
            leftChild = null;
            rightChild = null;
            key = "";
        }

        public Node(DrillingRecord record){
            data = record;
            height = 0;
            leftChild = null;
            rightChild = null;
            key = record.getTime();
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
                else if (record.getTime().compareTo(node.key) < 0)
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
                else if( record.getTime().compareTo(node.key) > 0 )
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

        public void setRootNode(Node node) {
            this.rootNode = node;
        }

        public Node getRootNode(){
            return rootNode;
        }

        public Tree.AVLTree purgeTrees(Tree.AVLTree base, Tree.AVLTree purge, int rootTreeSize){
            // store the data in ResizableArray
            ResizableArray<DrillingRecord> finalArray = new ResizableArray<>(); //array that will hold data minus the purge
            ResizableArray<DrillingRecord> baseArray= base.storeInorder(base.getRootNode());
            ResizableArray<DrillingRecord> purgeArray = purge.storeInorder(purge.getRootNode());

            //loop through array and store any data that does not match between them
            for(int i = 0; i< baseArray.getCount(); i++){
                for(int k = 0; k< purgeArray.getCount(); k++){
                    System.out.println(baseArray.get(i).getTime() + "  " + purgeArray.get(k).getTime() + " " + !baseArray.get(i).getTime().equals(purgeArray.get(k).getTime()));
                        if(baseArray.get(i).getTime().equals(purgeArray.get(k).getTime()) == false){
                            System.out.println("match");
                            if(finalArray.isEmpty()){
                                finalArray.push(baseArray.get(i));
                            }
                            ///todo
                            else if(finalArray.contains(baseArray.get(i)) == false){
                                finalArray.push(baseArray.get(i));
                            }
                            break;
                        }
                }
            }
            Tree.AVLTree newTree = new AVLTree();
            finalArray.print(finalArray);
            newTree.setRootNode(resizeArrayToTree(finalArray, 0, finalArray.getCount()-1));
            return newTree;
        }
        //following methods made with the help of  https://www.geeksforgeeks.org/merge-two-balanced-binary-search-trees/

        //utility method used in the method after to store data inorder
        public ResizableArray<DrillingRecord> storeInorderUtil(Node node, ResizableArray<DrillingRecord> list)
        {
            if(node == null)
                return list;

            //recur on the left child
            storeInorderUtil(node.leftChild, list);

            // Adds data to the list
            list.push(node.data);

            //recur on the right child
            storeInorderUtil(node.rightChild, list);

            return list;
        }
        // Method that stores inorder traversal of a tree
       public ResizableArray<DrillingRecord> storeInorder(Node node)
        {
            ResizableArray<DrillingRecord> list1 = new ResizableArray<>();
            ResizableArray<DrillingRecord> list2 = storeInorderUtil(node,list1);
            return list2;
        }
        // Method that merges two ResizableArrays into one.
        ResizableArray<DrillingRecord> merge(ResizableArray<DrillingRecord>list1, ResizableArray<DrillingRecord>list2, int m, int n)
        {
            // list3 will contain the merge of list1 and list2
            ResizableArray<DrillingRecord> list3 = new ResizableArray<>();
            int i=0;
            int j=0;

            //Traversing through both ResizableArrays
            while( i<m && j<n)
            {
                // records with earlier times go into list3
                if(list1.get(i).getTime().compareTo(list2.get(j).getTime()) < 0)
                {
                    list3.push(list1.get(i));
                    i++;
                }
                else
                {
                    list3.push(list2.get(j));
                    j++;
                }
            }

            // Adds the remaining elements of list1 into list3
            while(i<m)
            {
                list3.push(list1.get(i));
                i++;
            }

            // Adds the remaining elements of list2 into list3
            while(j<n)
            {
                list3.push(list2.get(j));
                j++;
            }
            return list3;
        }

        // Method that converts an ResizableArray to a BST
        Node resizeArrayToTree(ResizableArray<DrillingRecord>list, int start, int end)
        { Node node = new Node();
            // Base case
            if(start > end){
                return null;
            }

            // Get the middle element and make it root
            int mid = (start+end)/2;
            if(list.get(mid) != null){
                node = new Node(list.get(mid));
                   /* Recursively construct the left subtree and make it
        left child of root */
                node.leftChild = resizeArrayToTree(list, start, mid-1);

        /* Recursively construct the right subtree and make it
        right child of root */
                node.rightChild = resizeArrayToTree(list, mid+1, end);
            }



            return node;
        }
        // Method that merges two trees into a single one.
        public Node mergeTrees(Node node1, Node node2)
        {
            //Stores Inorder of tree1 to list1
            ResizableArray<DrillingRecord>list1 = storeInorder(node1);

            //Stores Inorder of tree2 to list2
            ResizableArray<DrillingRecord>list2 = storeInorder(node2);

            // Merges both list1 and list2 into list3
            ResizableArray<DrillingRecord>list3 = merge(list1, list2, list1.getCount(), list2.getCount());

            //Eventually converts the merged list into resultant BST
            return resizeArrayToTree(list3, 0, list3.getCount()-1);
        }
    }


}
