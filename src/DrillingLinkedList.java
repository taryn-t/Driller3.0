import java.util.LinkedList;


//implementation made with help from https://www.geeksforgeeks.org/how-to-implement-generic-linkedlist-in-java/
public class DrillingLinkedList<T>  {


    //class for generic node
   static class Node<T>{
       T data;
       Node<T> next;
        Node(){
            this.data = null;
            this.next = null;
        }
       Node(T data)
       {
           // This keyword refers to current object itself
           this.data = data;
           this.next = null;;
       }
   }
   //data variables for linked list
    Node<T> head;
    private int length = 0;

    //default constructor
    DrillingLinkedList(){
        this.head=null;
    }

    //method to add DrillingRecord data to linked list and returns a DrillingLinkedList
    //modified code from the link above to suit this project's needs
    public DrillingLinkedList<DrillingRecord> add(DrillingRecord data, DrillingLinkedList<DrillingRecord> list, String headDate ){
       try{
           //create a node holding the Drilling Record data
           Node<DrillingRecord> temp = new Node<>(data);

           //if there is a negative value in the DrillingRecord then it simply returns the list as is
           if(!data.getFloatError()) {
               //checks the date of the addition. if it doesnt match the date on the head then the node is not added
               if (!temp.data.getDate().equals(headDate)) {
                   return list;
               }
               //checks to see if list is empty
               if (list.head == null) {
                   list.head = (Node<DrillingRecord>) temp;
                   return list;
               } else {
                   //create node to traverse the list equal to the head of the list to be added onto
                   Node<DrillingRecord> trvNode = (Node<DrillingRecord>) list.head;
                   //traverse the list
                   while (trvNode != null) {
                       //had to add this piece of logic or else it would attempt to use a null value which broke the method
                       if (trvNode.next != null) {

                           //use the DrillingRecord compare to method to compare the times within the DrillingRecord data in the nodes
                           //this allows for the records to e stored in chronological order within the linked list
                           if (temp.data.getTime().compareTo(trvNode.data.getTime()) > 0 && temp.data.getTime().compareTo(trvNode.next.data.getTime()) < 0) {
                               //if temp time is greater than the trvNode time then insert the temp node containing the DrillingRecord after the trvNode
                               trvNode = temp;
                               trvNode.next = trvNode;
                               break;
                           } else if (temp.data.getTime().compareTo(trvNode.data.getTime()) < 0 && temp.data.getTime().compareTo(trvNode.next.data.getTime()) < 0) {
                               //if it is the opposite of the previous logic statement, insert the temp data before the trvNode
                               trvNode = temp.next;
                               break;
                           }

                       } else {
                           trvNode.next = temp;
                           break;
                       }
                       trvNode = trvNode.next;

                   }

                   //begin checking for duplicate times

                   //pointer node for the previous
                   Node<DrillingRecord> prev = list.head;
                   trvNode = prev.next;
                   //traverse list
                   while (prev != null) {
                       while (trvNode != null) {
                           if (prev.data.getTime().equals(trvNode.data.getTime())) {
                               delete(list.head, prev);
                               trvNode = trvNode.next;
                           } else {
                               trvNode = trvNode.next;
                           }
                       }
                       prev = prev.next;
                   }
                   //increase the length of list
                   length++;
               }
           }
           return list;
       }catch(StackOverflowError soe){
           System.out.println("Java stack out of memory");
       }

        //return the list
        return list;
    }

    //method to delete node used https://stackoverflow.com/questions/22902924/java-how-to-delete-a-node-from-linkedlist as reference
    private Node<DrillingRecord> delete(Node<DrillingRecord> head, Node<DrillingRecord> delete)
    {
        try{
            //in case list is empty then return
            if(head==null) return head;

            Node<DrillingRecord> curr = head;
            while(curr.next!=null)
            {
                if (curr.data.equals(delete.data))
                {
                    //curr.in's referenced Node will be garbage collected (or run some cleanup manually on it)
                    curr.next = curr.next.next;
                    //we are done and root is same
                    length--;
                    return head;

                }
                curr = curr.next;
            }
            //if here then not found and nothing changed
            return head;
        }catch(StackOverflowError soe){
            System.out.println("Java stack out of memory");
        }
       return head;
    }
    //method from the website linked above
    // To add new node at any given position
    public void add(int position, T data)
    {
        try{
            // Checking if position is valid
            if (position > length + 1) {

                // Display message only
                System.out.println(
                        "Position Unavailable in LikedList");
                return;
            }
            // If new position is head then replace head node
            if (position == 1) {
                // Temporary node that stores previous head
                // value
                Node<T> temp = head;
                // New valued node stored in head
                head = new Node<T>(data);
                // New head node pointing to old head node
                head.next = temp;
                return;
            }
            // Temporary node for traversal
            Node<T> temp = head;

            // Dummy node with null value that stores previous
            // node
            Node<T> prev = new Node<T>(null);
            // iterating to the given position
            while (position - 1 > 0) {
                // assigning previous node
                prev = temp;
                // incrementing next node
                temp = temp.next;
                // decreasing position counter
                position--;
            }
            // previous node now points to new value
            prev.next = new Node<T>(data);
            // new value now points to former current node
            prev.next.next = temp;
        }catch(StackOverflowError soe){
            System.out.println("Java stack out of memory");
        }

    }

    //method from the website linked above
    // To clear the entire LinkedList
   public void clear()
    {
        // Head now points to null
        head = null;
        // length is 0 again
        length = 0;
    }

    //method from the website linked above
    // Returns whether List is empty or not
    public boolean empty()
    {
        // Checking if head node points to null
        if (head == null) {
            return true;
        }
        return false;
    }
    // Returning the length of LinkedList
    public int length() { return this.length; }

    //method gets the last node
    public Node<T> getLast(){
        Node<T> temp = this.head;
        while(temp != null){
            if(temp.next == null){
                return temp;
            }
           temp = temp.next;
        }
        return temp;
    }
    public Node<T> getHead(){
        return this.head;
    }
    //method to set length
    public void setLength(int length ){
        this.length = length;
    }

    //method to merge two linked lists made with help from https://www.geeksforgeeks.org/java-program-to-merge-two-sorted-linked-lists-in-new-list/
    //and modifed to fit this particular purpose. The method returns the hea dnode of the merged list
    public Node<DrillingRecord> merge(DrillingLinkedList<DrillingRecord> list1, DrillingLinkedList<DrillingRecord> list2){
        try{
            //create result node this will keep track of the head
            Node<DrillingRecord> result= new DrillingLinkedList.Node(-1);
            //create a merge node set to result. This will allow you to add nodes from both lists
            Node<DrillingRecord> merge = result;
            //get the head of both lists to merge
            Node<DrillingRecord> l1 = list1.getHead();
            Node<DrillingRecord> l2 = list2.getHead();
            //traverse both lists
            while(l1 != null && l2 != null){
                //use DrillingRecord compareTo() to compare the times of the lists
                //this allows them to be inserted in chronological order
                if(l1.data.getTime().compareTo(l2.data.getTime()) < 0){
                    //if the time of the l1 node is before l2 insert the l1 node into merge.next
                    merge.next = l1;
                    l1 = l1.next;
                }else if(l1.data.getTime().compareTo(l2.data.getTime()) >0){
                    //if l1 node time is after l2 time then add l2
                    merge.next = l2;
                    l2 = l2.next;
                }else if(l1.data.getTime().equals(l2.data.getTime())){
                    //if times are equal just set the merge to the original list node
                    merge.next = l1;
                    l1 = l1.next;
                    l2 = l2.next;

                }
                //set merge to the next
                merge = merge.next;
            }
            //after traversing if there is any left over nodes add them to merge node
            if(l1 == null){
                merge.next = l2;
            }else if(l2 == null){
                merge.next = l1;
            }
            //return the head of the merge linked list
            return result.next;
        }catch(StackOverflowError soe){
            System.out.println("Java stack out of memory");
        }
       return list1.head;
    }

    //method to purge a list of drilling records. list1 is the original linked list and list2 is the list to be purged
    //this is just a modification f th emerge method to get rid of nodes rather than add them.
    public DrillingLinkedList<DrillingRecord> purge(DrillingLinkedList<DrillingRecord> list1, DrillingLinkedList<DrillingRecord> list2){
        try{
            //create new list to return

            //result node will sgtore the head of the purge list
            Node<DrillingRecord> result= new Node(-1);
            //purge node is equal to result and will store records for the purge list
            Node<DrillingRecord> purge = result;

            // the head nodes of both lists
            Node<DrillingRecord> l1 = list1.getHead();
            Node<DrillingRecord> l2 = list2.getHead();

            //traverse the lists until both are null
                //equals method from DrillingRecord compares the DrillingRecord data stored within the node
                while(l1 != null){
                    while(l2!=null){
                        if(l1.data.equals(l2.data)){
                            //if the data in l1 equals l2 then store the l1 node in the next purge position and set the other nodes to their next value
                            purge.next = l1.next;
                            break;
                        }
                            l2 = l2.next;

                    }
                    l1 = l1.next;
                    l2 = list2.getHead();
                }

            list1.head = result.next;
            return list1;
        }catch(StackOverflowError soe){
            System.out.println("Java stack out of memory");
        }

        //return new list
        return list1;
    }
    // To display the LinkedList
    @Override
    public String toString()
    {
        StringBuilder str = new StringBuilder("");
        Node<T> trvNode = head;
        if(head == null){
            return str.toString();
        }
        while(trvNode != null){
            if(trvNode.next ==null){
                str.append(trvNode.data);
            }else{
                str.append(trvNode.data).append("\n");
            }
            trvNode = trvNode.next;
        }

        return str.toString();
    }

    //sets the hea dof the list
    public void setHead(Node<T> head){
        this.head = head;
    }

    public ResizableArray<T> makeArray(){
        ResizableArray<T> array = new ResizableArray<>();
        Node<T> temp = this.head;
        int index = 0;
        while(temp!=null){
            array.addAt(temp.data, index);
            index++;
            temp = temp.next;
        }
        return array;
    }
}

